package com.example.patient.dto;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id, PatientResponseDTO patient, LocalDateTime appointmentDate) {}

