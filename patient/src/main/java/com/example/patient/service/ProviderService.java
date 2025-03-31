package com.example.patient.service;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;

import java.util.List;

public interface ProviderService {

    public ProviderResponseDTO getProviderById(Long id);
    public ProviderResponseDTO updateProvider(Long id, ProviderRequestDTO request);
    public void deleteProvider(Long id);
    public ProviderResponseDTO createProvider(ProviderRequestDTO request);
    public List<ProviderResponseDTO> getAllProviders();
}
