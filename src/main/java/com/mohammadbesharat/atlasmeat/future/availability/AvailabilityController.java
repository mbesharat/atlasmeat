// package com.mohammadbesharat.atlasmeat.future.availability;
// package com.mohammadbesharat.atlasmeat.controller;

// import com.mohammadbesharat.atlasmeat.dto.AvailabilityDay;
// import com.mohammadbesharat.atlasmeat.service.AvailabilityService;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDate;
// import java.util.List;


// @RestController
// public class AvailabilityController {
    
//     private final AvailabilityService availabilityService;

//     public AvailabilityController(AvailabilityService availabilityService){
//         this.availabilityService = availabilityService;
//     }

//     @GetMapping("/availability")
//     public List<AvailabilityDay> availability(
//         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
//         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
//     ) {
//         return availabilityService.getAvailability(from, to);
//     }

// }
