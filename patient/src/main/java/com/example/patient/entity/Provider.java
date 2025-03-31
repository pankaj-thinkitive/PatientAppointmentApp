package com.example.patient.entity;

import com.example.patient.dto.ProviderRequestDTO;
import com.example.patient.dto.ProviderResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<Patient> patients;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static ProviderResponseDTO toDto(Provider provider) {
        return ProviderResponseDTO.builder()
                .id(provider.id)
                .name(provider.name)
                .specialization(provider.specialization)
                .build();
    }

    public static Provider toEntity(ProviderRequestDTO request) {
        return Provider.builder()
                .name(request.name())
                .specialization(request.specialization())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
