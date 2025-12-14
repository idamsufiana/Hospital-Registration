package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresensiPegawaiResponse {
    private LocalDate tglAbsensi;
    private String jamMasuk;
    private String jamKeluar;
    private String namaStatus;
}