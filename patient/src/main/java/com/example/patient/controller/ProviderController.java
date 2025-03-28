package com.example.patient.controller;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping("/create")
    public ResponseEntity<ProviderResponseDTO> createProvider(@RequestBody ProviderRequestDTO request) {
        return ResponseEntity.ok().body(providerService.createProvider(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProviderResponseDTO>> getAllProviders() {
        return ResponseEntity.ok().body(providerService.getAllProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getProviderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(providerService.getProviderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> updateProvider(@PathVariable("id") Long id, @RequestBody ProviderRequestDTO request) {
        return ResponseEntity.ok().body(providerService.updateProvider(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable("id") Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.ok().body("Provider Deleted Successfully");
    }
}

