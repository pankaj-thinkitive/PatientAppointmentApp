package com.example.patient.dto;
import lombok.Builder;

@Builder
public record ProviderResponseDTO(Long id, String name, String specialization) {}

