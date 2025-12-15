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
    @Column(name = "id_user")
    private UUID idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "profile", nullable = false)
    private String profile; // "ADMIN" / "PEGAWAI"

    @Column(name = "nama_lengkap", nullable = false)
    private String namaLengkap;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private LocalDate tanggalLahir;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "nik_user")
    private String nikUser;

    @Column(name = "kd_jabatan")
    private Integer kdJabatan;

    @Column(name = "kd_departemen")
    private Integer kdDepartemen;

    @Column(name = "kd_unit_kerja")
    private Integer kdUnitKerja;

    @Column(name = "kd_jenis_kelamin")
    private Integer kdJenisKelamin;

    @Column(name = "kd_pendidikan")
    private Integer kdPendidikan;

    @Column(name = "photo")
    private String photo; // URL / path foto
}