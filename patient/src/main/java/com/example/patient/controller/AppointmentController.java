package com.example.patient.controller;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import com.example.patient.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequestDTO request) {
        appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Appointment created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointments(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String providerName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<AppointmentResponseDTO> response = appointmentService.getAllAppointments(
                patientName, providerName, appointmentDate, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable("id") Long id, @RequestBody AppointmentRequestDTO request) {
        appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok().body("Appointment Updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().body("Appointment Deleted Successfully");
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsByProvider(
            @PathVariable Long providerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(appointmentService.getAppointmentsByProvider(providerId, pageable));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointmentsByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(appointmentService.getAppointmentsByPatient(patientId, pageable));
    }
}

