package com.example.patient.service;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentService {

    public AppointmentResponseDTO getAppointmentById(Long id);
    public void updateAppointment(Long id, AppointmentRequestDTO request);
    public void deleteAppointment(Long id);
    public void createAppointment(AppointmentRequestDTO request);
    public Page<AppointmentResponseDTO> getAllAppointments(
            String patientName, String providerName, LocalDate appointmentDate,
            int page, int size, String sortBy, String sortDirection);
    public Page<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId, Pageable pageable);
    public Page<AppointmentResponseDTO> getAppointmentsByProvider(Long providerId, Pageable pageable);
}
