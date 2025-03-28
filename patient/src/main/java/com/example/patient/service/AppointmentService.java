package com.example.patient.service;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import com.example.patient.dto.PatientResponseDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.model.Appointment;
import com.example.patient.model.Patient;
import com.example.patient.model.Provider;
import com.example.patient.repo.AppointmentRepository;
import com.example.patient.repo.PatientRepository;
import com.example.patient.repo.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        Appointment appointment = appointmentRepository.save(
                new Appointment(null, patient, provider, request.appointmentDate(), LocalDateTime.now())
        );

        return new AppointmentResponseDTO(
                appointment.getId(),
                new PatientResponseDTO(patient.getId(), patient.getName(), patient.getAge(),
                        new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization())),
                new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization()),
                appointment.getAppointmentDate()
        );
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> new AppointmentResponseDTO(
                        appointment.getId(),
                        new PatientResponseDTO(appointment.getPatient().getId(), appointment.getPatient().getName(),
                                appointment.getPatient().getAge(),
                                new ProviderResponseDTO(appointment.getProvider().getId(), appointment.getProvider().getName(),
                                        appointment.getProvider().getSpecialization())),
                        new ProviderResponseDTO(appointment.getProvider().getId(), appointment.getProvider().getName(),
                                appointment.getProvider().getSpecialization()),
                        appointment.getAppointmentDate()
                )).toList();
    }

    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        return new AppointmentResponseDTO(
                appointment.getId(),
                new PatientResponseDTO(appointment.getPatient().getId(), appointment.getPatient().getName(),
                        appointment.getPatient().getAge(),
                        new ProviderResponseDTO(appointment.getProvider().getId(), appointment.getProvider().getName(),
                                appointment.getProvider().getSpecialization())),
                new ProviderResponseDTO(appointment.getProvider().getId(), appointment.getProvider().getName(),
                        appointment.getProvider().getSpecialization()),
                appointment.getAppointmentDate()
        );
    }

    @Transactional
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        appointment.setPatient(patient);
        appointment.setProvider(provider);
        appointment.setAppointmentDate(request.appointmentDate());

        return new AppointmentResponseDTO(
                appointment.getId(),
                new PatientResponseDTO(patient.getId(), patient.getName(), patient.getAge(),
                        new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization())),
                new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization()),
                appointment.getAppointmentDate()
        );
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }
}

