package com.example.patient.service;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {

    public List<PatientResponseDTO> getAllPatients();
    public PatientResponseDTO getPatientById(Long id);
    public PatientResponseDTO createPatient(PatientRequestDTO request);
    public void deletePatient(Long id);
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO request);
}
