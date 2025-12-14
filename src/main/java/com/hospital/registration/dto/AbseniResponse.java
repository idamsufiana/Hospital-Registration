package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbseniResponse {
    private Integer tglAbsensi;
    private String namaStatus;
}