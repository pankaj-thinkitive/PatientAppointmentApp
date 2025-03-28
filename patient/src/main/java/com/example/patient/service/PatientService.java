package com.example.patient.service;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.exception.CustomDeletionException;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.model.Patient;
import com.example.patient.model.Provider;
import com.example.patient.repo.PatientRepository;
import com.example.patient.repo.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    public PatientResponseDTO createPatient(PatientRequestDTO request) {
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        Patient patient = patientRepository.save(
                new Patient(null, request.name(), request.age(), provider, LocalDateTime.now())
        );

        return new PatientResponseDTO(patient.getId(), patient.getName(), patient.getAge(),
                new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization()));
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> new PatientResponseDTO(
                        patient.getId(), patient.getName(), patient.getAge(),
                        new ProviderResponseDTO(patient.getProvider().getId(), patient.getProvider().getName(), patient.getProvider().getSpecialization())
                )).toList();
    }

    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return new PatientResponseDTO(
                patient.getId(), patient.getName(), patient.getAge(),
                new ProviderResponseDTO(patient.getProvider().getId(), patient.getProvider().getName(),
                        patient.getProvider().getSpecialization())
        );
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        patient.setName(request.name());
        patient.setAge(request.age());
        patient.setProvider(provider);

        return new PatientResponseDTO(
                patient.getId(), patient.getName(), patient.getAge(),
                new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization())
        );
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found");
        }
        try {
            patientRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDeletionException("Cannot delete patient. It is assigned to existing appointments.");
        }
    }
}

