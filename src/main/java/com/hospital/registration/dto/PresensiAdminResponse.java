package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresensiAdminResponse {
    private UUID id;
    private String namaLengkap;
    private LocalDate tglAbsensi; // epoch SECOND
    private String jamMasuk;    // HH:mm
    private String jamKeluar;   // HH:mm
    private String namaStatus;
}
