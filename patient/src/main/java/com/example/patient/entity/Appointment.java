package com.example.patient.entity;

import com.example.patient.dto.AppointmentRequestDTO;
import com.example.patient.dto.AppointmentResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static Appointment toEntity(AppointmentRequestDTO request, Patient patient, Provider provider) {
        return Appointment.builder()
                .patient(patient)
                .provider(provider)
                .appointmentDate(request.appointmentDate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppointmentResponseDTO toDto(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                Patient.toDto(appointment.getPatient()),
                appointment.getAppointmentDate()
        );
    }

}
