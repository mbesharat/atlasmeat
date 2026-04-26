package com.mohammadbesharat.atlasmeat.appointment.repo;

import com.mohammadbesharat.atlasmeat.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


}
