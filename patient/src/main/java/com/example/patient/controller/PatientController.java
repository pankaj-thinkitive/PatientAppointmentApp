package com.example.patient.controller;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;
import com.example.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<String> createPatient(@RequestBody PatientRequestDTO request) {
        patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable("id") Long id, @RequestBody PatientRequestDTO request) {
        patientService.updatePatient(id, request);
        return ResponseEntity.ok().body("Patient updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().body("Patient Deleted Successfully");
    }
}

