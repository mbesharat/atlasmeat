package com.mohammadbesharat.atlasmeat.appointment.api;


import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import com.mohammadbesharat.atlasmeat.appointment.dto.*;
import com.mohammadbesharat.atlasmeat.appointment.service.AppointmentService;
import com.mohammadbesharat.atlasmeat.workflow.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final WorkflowService workflowService;
    public AppointmentController(AppointmentService service, WorkflowService workflowService) {
        this.appointmentService = service;
        this.workflowService = workflowService;
    }

    //create appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest req){
        AppointmentResponse response = appointmentService.createAppointment(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get appointment using id
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long appointmentId){
        AppointmentResponse response = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(response);
    }

    //get appointment using specific filters
    @GetMapping
    public Page<AppointmentResponse> getAppointments(
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerPhone,
            @RequestParam(required = false) String customerEmail,
            @RequestParam(required = false) LocalDate scheduledDate,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return appointmentService.searchAppointments(
                status,
                customerName,
                customerPhone,
                customerEmail,
                scheduledDate,
                pageable);
    }

    //set appointment date
    @PatchMapping("/{appointmentId}/scheduled-date")
    public ResponseEntity<AppointmentResponse> setScheduledDate(@PathVariable Long appointmentId, @Valid @RequestBody SetScheduledDateRequest req){
        AppointmentResponse response = appointmentService.setAppointmentDate(appointmentId, req.scheduledDate());
        return ResponseEntity.ok(response);
    }

    //update appointment status
    @PatchMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(@PathVariable Long appointmentId, @Valid @RequestBody UpdateAppointmentStatusRequest req){
        AppointmentResponse response = workflowService.updateAppointmentStatus(appointmentId, req.status());
        return ResponseEntity.ok(response);
    }

    //set hanging weight
    @PatchMapping("/{appointmentId}/hanging-weight")
    public ResponseEntity<AppointmentResponse> setHangingWeight(@PathVariable Long appointmentId, @Valid @RequestBody SetHangingWeightRequest req){
        return ResponseEntity.ok(appointmentService.setHangingWeight(appointmentId, req.hangingWeight()));
    }

}
