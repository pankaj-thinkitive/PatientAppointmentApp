package com.example.patient.service.Impl;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import com.example.patient.exception.CustomDeletionException;
import com.example.patient.exception.ResourceNotFoundException;
import com.example.patient.entity.Provider;
import com.example.patient.repo.ProviderRepository;
import com.example.patient.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public void createProvider(ProviderRequestDTO request) {
        Provider provider = Provider.toEntity(request);
         providerRepository.save(provider);
    }

    @Override
    public Page<ProviderResponseDTO> getAllProviders(Pageable pageable) {
        return providerRepository.findAll(pageable)
                .map(Provider::toDto);
    }

    @Override
    public ProviderResponseDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        return Provider.toDto(provider);
    }

    @Override
    public void updateProvider(Long id, ProviderRequestDTO request) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        provider.setName(request.name());
        provider.setSpecialization(request.specialization());
       providerRepository.save(provider);
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
