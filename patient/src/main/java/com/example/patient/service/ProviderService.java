package com.example.patient.service;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {

    public ProviderResponseDTO getProviderById(Long id);
    public void updateProvider(Long id, ProviderRequestDTO request);
    public void deleteProvider(Long id);
    public void createProvider(ProviderRequestDTO request);
    public Page<ProviderResponseDTO> getAllProviders(Pageable pageable);
}
