package com.hospital.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "presensi")
@Data
public class Presensi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // epoch millis hari tsb (tglAbsensi)
    @Column(nullable = false)
    private LocalDate tglAbsensi;

    private String jamMasuk;  // "HH:mm:ss"
    private String jamKeluar; // "HH:mm:ss"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kd_status")
    private StatusAbsen status;
}