package com.mohammadbesharat.atlasmeat.workflow.service;


import com.mohammadbesharat.atlasmeat.appointment.domain.Appointment;
import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import com.mohammadbesharat.atlasmeat.appointment.dto.AppointmentResponse;
import com.mohammadbesharat.atlasmeat.appointment.repo.AppointmentRepository;
import com.mohammadbesharat.atlasmeat.appointment.service.AppointmentService;
import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.dto.UpdateCheckoutStatusRequest;
import com.mohammadbesharat.atlasmeat.checkout.dto.UpdateItemRequest;
import com.mohammadbesharat.atlasmeat.order.dto.UpdateOrderRequest;
import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;
import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;

import java.util.*;

import static java.util.Comparator.comparing;

@Service
public class WorkflowService {

    private final CheckoutService checkoutService;
    private final OrderService orderService;
    private final AppointmentService appointmentService;

    public WorkflowService(CheckoutService checkoutService, OrderService orderService, AppointmentService appointmentService){
        this.checkoutService = checkoutService;
        this.orderService = orderService;
        this.appointmentService = appointmentService;
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long appointmentId, AppointmentStatus status){
        appointmentService.updateAppointmentStatus(appointmentId, status);
        AppointmentResponse appointmentResponse = appointmentService.getAppointmentById(appointmentId);
        Long checkoutId = null;

        if(status == AppointmentStatus.CUT_SHEET_OPEN){
            CreateCheckoutRequest checkoutRequest = new CreateCheckoutRequest(
                    appointmentResponse.customerName(),
                    appointmentResponse.customerPhone(),
                    appointmentResponse.customerEmail());

            checkoutId = checkoutService.createCheckout(checkoutRequest).getId();
            appointmentService.linkCheckout(appointmentId, checkoutId);
        }
        return new AppointmentResponse(
                appointmentResponse.id(),
                appointmentResponse.customerName(),
                appointmentResponse.customerPhone(),
                appointmentResponse.customerEmail(),
                appointmentResponse.contactPreference(),
                appointmentResponse.animalType(),
                appointmentResponse.animalCount(),
                appointmentResponse.scheduledDate(),
                appointmentResponse.status(),
                checkoutId == null ? appointmentResponse.checkoutId() : checkoutId,
                appointmentResponse.hangingWeight()
        );
    }

    private CheckoutResponse toCheckoutResponse(Checkout checkout){
        List<OrderResponse> orderDtos = checkout.getOrders().stream().map(order -> {
                List<OrderItemResponse> itemDtos = order.getItems().stream()
                        .sorted(Comparator.comparing(OrderItem::getId)).map(orderService::toItemDto).toList();

                return new OrderResponse(
                        order.getId(),
                        order.getAnimalType(),
                        itemDtos
                );

        })
        .toList();
        return new CheckoutResponse(
                checkout.getId(),
                checkout.getCustomerName(),
                checkout.getCustomerPhone(),
                checkout.getCustomerEmail(),
                checkout.getStatus(),
                checkout.getCreatedAt(),
                orderDtos
        );
    }

    public CheckoutResponse createCheckout(CreateCheckoutRequest request){
        return toCheckoutResponse(checkoutService.createCheckout(request));
    }

    public CheckoutResponse getCheckout(Long checkoutId){

        return toCheckoutResponse(checkoutService.getCheckout(checkoutId));
    }

    @Transactional
    public CheckoutResponse addOrderToCheckout(Long checkoutId, CreateOrderRequest orderRequest){

        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "add orders");

        Order order = new Order();
        order.setAnimal(orderRequest.animal());

        checkout.addOrder(order);

        Map<Long, Integer> cutQty = orderService.mergeCutQuantities(orderRequest.items());

        orderService.addItemsToOrder(order, cutQty);
        return toCheckoutResponse(checkoutService.saveCheckout(checkout));
    }


    public void removeOrderFromCheckout(Long checkoutId, Long orderId){

        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "remove orders");

        Order order = orderService.findByIdAndCheckoutId(orderId, checkoutId);
        checkoutService.removeOrderFromCheckout(checkout, order);
    }

    @Transactional
    public CheckoutResponse patchOrder(Long checkoutId, Long orderId, UpdateOrderRequest request){
        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "patch orders");

        Order order = orderService.findByIdAndCheckoutId(orderId, checkoutId);
        orderService.validatePatchRequest(request);

        AnimalType finalAnimal = (request.animal() != null ? request.animal() : order.getAnimalType());
        order.setAnimal(finalAnimal);

        if(request.items() != null) {

            Map<Long, Integer> cutQty = orderService.mergeCutQuantities(request.items());
            orderService.replaceOrderItems(order, cutQty);
        }
        return toCheckoutResponse(checkout);
    }

    @Transactional
    public CheckoutResponse patchItem(Long checkoutId, Long orderId, Long orderItemId, UpdateItemRequest request){
        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "edit items");

        orderService.findByIdAndCheckoutId(orderId, checkoutId);

        OrderItem item = orderService.findByIdAndOrderIdAndCheckoutId(orderItemId, orderId, checkoutId);
        item.setQuantity(request.quantity());
        return toCheckoutResponse(checkout);
    }

    public void removeItemFromOrder(Long checkoutId, Long orderId, Long orderItemId){
        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "remove items");

        OrderItem item =  orderService.findByIdAndOrderIdAndCheckoutId(orderItemId, orderId, checkoutId);
        orderService.removeItemFromOrder(item);
    }

    public CheckoutResponse updateCheckoutStatus(Long checkoutId, CheckoutStatus status){

        return toCheckoutResponse(checkoutService.updateCheckoutStatus(checkoutId, status));
    }

    public Page<CheckoutResponse> searchCheckouts(
            CheckoutStatus status,
            String customerName,
            String customerPhone,
            String customerEmail,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    ){
        Page<Checkout> page = checkoutService.searchCheckouts(
                status,
                customerName,
                customerPhone,
                customerEmail,
                from,
                to,
                pageable);
        return page.map(this::toCheckoutResponse);
    }


}

