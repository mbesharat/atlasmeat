package com.mohammadbesharat.atlasmeat.appointment.repo;

import com.mohammadbesharat.atlasmeat.appointment.domain.Appointment;
import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class AppointmentSpecifications {

    private AppointmentSpecifications() {}

    public static Specification<Appointment> hasStatus(AppointmentStatus status) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Appointment> customerNameContains(String customerName) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%");
    }

    public static Specification<Appointment> customerPhoneContains(String customerPhone) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("customerPhone"), "%" + customerPhone + "%");
    }

    public static Specification<Appointment> customerEmailContains(String customerEmail) {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("customerEmail"), "%" + customerEmail + "%");
    }

    public static Specification<Appointment> scheduledDateOn(LocalDate scheduledDate) {
        return (root, criteriaQuery, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("scheduledDate"), scheduledDate);
    }
}
