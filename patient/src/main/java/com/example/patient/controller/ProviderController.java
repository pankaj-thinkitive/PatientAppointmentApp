package com.example.patient.controller;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping("/create")
    public ResponseEntity<String> createProvider(@RequestBody ProviderRequestDTO request) {
        providerService.createProvider(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Provider created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProviderResponseDTO>> getAllProviders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(providerService.getAllProviders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getProviderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(providerService.getProviderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProvider(@PathVariable("id") Long id, @RequestBody ProviderRequestDTO request) {
        providerService.updateProvider(id, request);
        return ResponseEntity.ok().body("Provider updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable("id") Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.ok().body("Provider Deleted Successfully");
    }
}

