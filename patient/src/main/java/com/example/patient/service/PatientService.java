package com.example.patient.service;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {

    public Page<PatientResponseDTO> getAllPatients(Pageable pageable);
    public PatientResponseDTO getPatientById(Long id);
    public void createPatient(PatientRequestDTO request);
    public void deletePatient(Long id);
    public void updatePatient(Long id, PatientRequestDTO request);
}
