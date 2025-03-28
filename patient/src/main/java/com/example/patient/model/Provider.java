package com.example.patient.model;

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
}
