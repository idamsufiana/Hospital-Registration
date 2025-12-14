package com.hospital.registration.dto;

import lombok.Data;

@Data
public class AbseniRequest {
    private Integer tglAbsensi; // epoch day / format yang kamu pakai
    private Integer kdStatus;   // status izin / tidak masuk
}