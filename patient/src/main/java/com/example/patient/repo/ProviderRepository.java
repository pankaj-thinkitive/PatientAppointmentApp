package com.example.patient.repo;

import com.example.patient.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}

