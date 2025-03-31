package com.example.patient.service;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AppointmentService {

    public AppointmentResponseDTO getAppointmentById(Long id);
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO request);
    public void deleteAppointment(Long id);
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO request);
    public Page<AppointmentResponseDTO> getAppointments(
            String patientName, String providerName, LocalDate appointmentDate,
            int page, int size, String sortBy, String sortDirection);
}
