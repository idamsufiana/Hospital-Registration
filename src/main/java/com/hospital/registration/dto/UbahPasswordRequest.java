package com.hospital.registration.dto;

import lombok.Data;

@Data
public class UbahPasswordRequest {
    private String passwordLama;
    private String passwordBaru;
    private String konfirmasiPassword;
}