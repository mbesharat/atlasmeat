package com.mohammadbesharat.atlasmeat.order.service;


import com.mohammadbesharat.atlasmeat.order.dto.UpdateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.exceptions.CutAnimalMismatch;
import com.mohammadbesharat.atlasmeat.order.exceptions.CutNotFound;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderItemNotFound;
import com.mohammadbesharat.atlasmeat.order.exceptions.InvalidPatchRequest;
import com.mohammadbesharat.atlasmeat.common.exception.OrderNotInCheckout;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderItemRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CutRepository cutRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, CutRepository cutRepository, OrderItemRepository orderItemRepository){
        this.orderRepository = orderRepository;
        this.cutRepository = cutRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Order findOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderResponse getOrderById(Long id){
        return toDto(findOrderById(id));
    }

    public Page<OrderResponse> getOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(this::toDto);
    }

    public Cut findCutById(Long cutId){
        return cutRepository.findById(cutId).orElseThrow(() -> new CutNotFound(cutId));
    }

    private OrderResponse toDto(Order order){
        List<OrderItemResponse> itemDtos = order.getItems().stream()
                .sorted(Comparator.comparing(OrderItem::getId))
                .map(this::toItemDto).toList();
        return new OrderResponse(
            order.getId(),
            order.getAnimalType(),
            itemDtos
        );
    }


    public OrderItemResponse toItemDto(OrderItem item){
        return new OrderItemResponse(
                item.getId(),
                item.getCut().getId(),
                item.getCut().getDisplayName(),
                item.getQuantity()
        );
    }

    public Map<Long, Integer> mergeCutQuantities(List<CreateOrderItemRequest> items){
        Map<Long, Integer> result = new LinkedHashMap<>();

        for(CreateOrderItemRequest item : items){
            Long cutId = item.cutId();
            Integer qty = item.quantity();
            result.merge(cutId, qty, Integer::sum);
        }
        return result;
    }

    public void addItemsToOrder(Order order, Map<Long, Integer> cutQty){
        for(Map.Entry<Long, Integer> entry : cutQty.entrySet()){
            Long cutId = entry.getKey();
            Integer quantity = entry.getValue();

            Cut cut = cutRepository.findById(cutId).orElseThrow(() -> new CutNotFound(cutId));

            if (cut.getAnimalType() != order.getAnimalType()){
                throw new CutAnimalMismatch(cutId + " (" + cut.getDisplayName() + ")" + order.getAnimalType());
            }

            OrderItem item = new OrderItem();
            item.setCut(cut);
            item.setQuantity(quantity);

            order.addItem(item);
        }
    }

    public void replaceOrderItems(Order order, Map<Long, Integer> cutQty){
        order.getItems().clear();
        addItemsToOrder(order, cutQty);
    }

    public Order findByIdAndCheckoutId(Long orderId, Long checkoutId){
        return orderRepository.findByIdAndCheckoutId(orderId, checkoutId).orElseThrow(()
                -> new OrderNotInCheckout(orderId, checkoutId));
    }

    public void validatePatchRequest(UpdateOrderRequest request){

        if (request.animal() == null && request.items() == null){
            throw new InvalidPatchRequest("At least one animal or item must be provided");
        }
        if(request.items() != null && request.items().isEmpty()){
            throw new InvalidPatchRequest("Items must contain at least one item");
        }
        if(request.animal() != null && request.items() == null){
            throw new InvalidPatchRequest("Changing animal requires updating items");
        }

    }

    public OrderItem findByIdAndOrderIdAndCheckoutId(Long orderItemId, Long orderId, Long checkoutId){
        return orderItemRepository.findByIdAndOrderIdAndOrderCheckoutId(
                orderItemId, orderId, checkoutId).orElseThrow(() ->
                new OrderItemNotFound(orderItemId, orderId, checkoutId));
    }


    public void removeItemFromOrder(OrderItem item) {
        orderItemRepository.delete(item);
    }
}
