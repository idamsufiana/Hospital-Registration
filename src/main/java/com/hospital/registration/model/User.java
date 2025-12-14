package com.hospital.registration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(length = 36)
    private UUID idUser; // UUID string

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String profile; // "ADMIN" / "PEGAWAI"

    @Column(nullable = false)
    private String namaLengkap;

    private String tempatLahir;

    // epoch millis (Integer di spec; lebih aman Long)
    private LocalDate tanggalLahir;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String nikUser;

    private Integer kdJabatan;
    private Integer kdDepartemen;
    private Integer kdUnitKerja;
    private Integer kdJenisKelamin;
    private Integer kdPendidikan;

    private String photo; // simpan URL / path
}