package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresensiAdminResponse {
    private String namaLengkap;
    private LocalDate tglAbsensi; // epoch SECOND
    private LocalTime jamMasuk;    // HH:mm
    private LocalTime jamKeluar;   // HH:mm
    private String namaStatus;
}
