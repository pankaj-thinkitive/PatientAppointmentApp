package com.example.patient.service.Impl;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;
import com.example.patient.exception.CustomDeletionException;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.entity.Patient;
import com.example.patient.entity.Provider;
import com.example.patient.repo.PatientRepository;
import com.example.patient.repo.ProviderRepository;
import com.example.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    @Override
    public void createPatient(PatientRequestDTO request) {
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        Patient patient=Patient.toEntity(request, provider);
         patientRepository.save(patient);
    }

    @Override
    public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(Patient::toDto);
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return Patient.toDto(patient);
    }

    @Override
    public void updatePatient(Long id, PatientRequestDTO request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        patient.setName(request.name());
        patient.setAge(request.age());
        patient.setProvider(provider);
        patientRepository.save(patient);
    }

    @Override
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
