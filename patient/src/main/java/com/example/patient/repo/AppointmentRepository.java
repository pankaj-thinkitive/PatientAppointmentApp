package com.example.patient.repo;

import com.example.patient.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    Page<Appointment> findByProviderId(Long providerId, Pageable pageable);
}
