package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresensiPegawaiResponse {
    private LocalDate tglAbsensi;
    private LocalTime jamMasuk;
    private LocalTime jamKeluar;
    private String namaStatus;
}