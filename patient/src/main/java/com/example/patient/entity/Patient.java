package com.example.patient.entity;

import com.example.patient.dto.PatientRequestDTO;
import com.example.patient.dto.PatientResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static PatientResponseDTO toDto(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .age(patient.getAge())
                .provider(Provider.toDto(patient.getProvider()))
                .build();
    }

    public static Patient toEntity(PatientRequestDTO request, Provider provider) {
        return Patient.builder()
                .name(request.name())
                .age(request.age())
                .provider(provider)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
