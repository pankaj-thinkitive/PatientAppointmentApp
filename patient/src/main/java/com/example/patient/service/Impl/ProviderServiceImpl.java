package com.example.patient.service.Impl;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.exception.CustomDeletionException;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.model.Provider;
import com.example.patient.repo.ProviderRepository;
import com.example.patient.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public ProviderResponseDTO createProvider(ProviderRequestDTO request) {
        Provider provider = providerRepository.save(
                new Provider(null, request.name(), request.specialization(), null, LocalDateTime.now())
        );
        return new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization());
    }

    @Override
    public List<ProviderResponseDTO> getAllProviders() {
        return providerRepository.findAll().parallelStream()
                .map(provider -> new ProviderResponseDTO(
                        provider.getId(),
                        provider.getName(),
                        provider.getSpecialization()
                )).toList();
    }

    @Override
    public ProviderResponseDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        return new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization());
    }

    @Override
    @Transactional
    public ProviderResponseDTO updateProvider(Long id, ProviderRequestDTO request) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        provider.setName(request.name());
        provider.setSpecialization(request.specialization());

        return new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getSpecialization());
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Provider not found with ID: " + id);
        }
        try {
            providerRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDeletionException("Cannot delete provider. It is assigned to a patient or has existing appointments.");
        }
    }

}

