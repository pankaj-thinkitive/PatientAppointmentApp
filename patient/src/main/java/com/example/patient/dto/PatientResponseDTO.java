package com.example.patient.dto;

public record PatientResponseDTO(Long id, String name, Integer age, ProviderResponseDTO provider) {}

