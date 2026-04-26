package com.mohammadbesharat.atlasmeat.appointment.service;


import com.mohammadbesharat.atlasmeat.appointment.domain.Appointment;
import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import com.mohammadbesharat.atlasmeat.appointment.dto.AppointmentResponse;
import com.mohammadbesharat.atlasmeat.appointment.dto.CreateAppointmentRequest;
import com.mohammadbesharat.atlasmeat.appointment.exceptions.AppointmentNotFoundException;
import com.mohammadbesharat.atlasmeat.appointment.exceptions.InvalidAppointmentStatusTransitionException;
import com.mohammadbesharat.atlasmeat.appointment.repo.AppointmentRepository;

import com.mohammadbesharat.atlasmeat.appointment.repo.AppointmentSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    private AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getCustomerName(),
                appointment.getCustomerPhone(),
                appointment.getCustomerEmail(),
                appointment.getContactPreference(),
                appointment.getAnimalType(),
                appointment.getAnimalCount(),
                appointment.getScheduledDate(),
                appointment.getStatus(),
                appointment.getCheckoutId()
        );
    }

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest req) {
        Appointment appointment = new Appointment();
        appointment.setCustomerName(req.customerName());
        appointment.setCustomerEmail(req.customerEmail());
        appointment.setCustomerPhone(req.customerPhone());
        appointment.setContactPreference(req.contactPreference());
        appointment.setAnimalType(req.animalType());
        appointment.setAnimalCount(req.animalCount());
        appointment.setStatus(AppointmentStatus.REQUESTED);
        appointment.setScheduledDate(req.scheduledDate());

        return  toAppointmentResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse getAppointmentById(Long id){
        return toAppointmentResponse(appointmentRepository.findById(id).orElseThrow(() ->
                new AppointmentNotFoundException(id)));
    }

    public Page<Appointment> searchAppointments(
            AppointmentStatus status,
            String customerName,
            String customerPhone,
            String customerEmail,
            LocalDate scheduledDate,
            Pageable pageable
    ){
        Specification<Appointment> spec = (root,  criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.conjunction();
        if(status != null){
            spec = spec.and(AppointmentSpecifications.hasStatus(status));
        }
        if(customerName != null){
            spec = spec.and(AppointmentSpecifications.customerNameContains(customerName));
        }
        if(customerPhone != null){
            spec = spec.and(AppointmentSpecifications.customerPhoneContains(customerPhone));
        }
        if(customerEmail != null){
            spec = spec.and(AppointmentSpecifications.customerEmailContains(customerEmail));
        }
        if(scheduledDate != null){
            spec = spec.and(AppointmentSpecifications.scheduledDateOn(scheduledDate));
        }
        return appointmentRepository.findAll(spec, pageable);
    }

    private boolean isAllowedTransition(AppointmentStatus current, AppointmentStatus next) {
        return switch (current) {
            case REQUESTED -> next == AppointmentStatus.SCHEDULED || next == AppointmentStatus.CANCELLED;
            case SCHEDULED -> next == AppointmentStatus.DROPPED_OFF || next == AppointmentStatus.CANCELLED;
            case DROPPED_OFF -> next == AppointmentStatus.CUT_SHEET_OPEN;
            case CUT_SHEET_OPEN, CANCELLED -> false;
        };
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new AppointmentNotFoundException(appointmentId));
        AppointmentStatus currentStatus = appointment.getStatus();

        if(!isAllowedTransition(currentStatus, status)){
            throw new InvalidAppointmentStatusTransitionException(currentStatus, status);
        }
        appointment.setStatus(status);
        return toAppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse setAppointmentDate(Long appointmentId, LocalDate date) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new AppointmentNotFoundException(appointmentId));

        appointment.setScheduledDate(date);
        return toAppointmentResponse(appointmentRepository.save(appointment));
    }

}
