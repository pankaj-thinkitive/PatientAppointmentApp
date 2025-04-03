package com.example.patient.service.Impl;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.entity.Appointment;
import com.example.patient.entity.Patient;
import com.example.patient.entity.Provider;
import com.example.patient.repo.AppointmentRepository;
import com.example.patient.repo.PatientRepository;
import com.example.patient.repo.ProviderRepository;
import com.example.patient.service.AppointmentService;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    @Override
    public void createAppointment(AppointmentRequestDTO request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        Appointment appointment = Appointment.toEntity(request, patient, provider);
       appointmentRepository.save(appointment);

    }

    @Override
    public Page<AppointmentResponseDTO> getAllAppointments(
            String patientName, String providerName, LocalDate appointmentDate,
            int page, int size, String sortBy, String sortDirection) {

//        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Map<String, String> sortFields = Map.of(
                "date", "appointmentDate",
                "patient", "patient.name",
                "provider", "provider.name",
                "appointmentDate", "appointmentDate"
        );

        Sort sort = Sort.by(sortFields.getOrDefault(sortBy.toLowerCase(), "appointmentDate"));
        sort = "desc".equalsIgnoreCase(sortDirection) ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Appointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (patientName != null && !patientName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("patient").get("name")), "%" + patientName.toLowerCase() + "%"));
            }
            if (providerName != null && !providerName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("provider").get("name")), "%" + providerName.toLowerCase() + "%"));
            }
            if (appointmentDate != null) {
                predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("appointmentDate")), appointmentDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<Appointment> appointments = appointmentRepository.findAll(spec, pageable);
        return appointments.map(Appointment::toDto);
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return Appointment.toDto(appointment);
    }

    @Override
    public void updateAppointment(Long id, AppointmentRequestDTO request) {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

            Patient patient = patientRepository.findById(request.patientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
            Provider provider = providerRepository.findById(request.providerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

            appointment.setPatient(patient);
            appointment.setProvider(provider);
            appointment.setAppointmentDate(request.appointmentDate());
            appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsByProvider(Long providerId, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByProviderId(providerId, pageable);
        return appointments.map(Appointment::toDto);
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId,Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByPatientId(patientId,pageable);
        return appointments.map(Appointment::toDto);
    }
}

