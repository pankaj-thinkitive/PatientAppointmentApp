package com.example.patient.dto;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(Long patientId, Long providerId, LocalDateTime appointmentDate) {}
