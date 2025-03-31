package com.example.patient.dto;

import lombok.Builder;

@Builder
public record PatientResponseDTO(Long id, String name, Integer age, ProviderResponseDTO provider) {
}

