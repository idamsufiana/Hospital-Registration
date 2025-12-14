package com.hospital.registration.dto;

import lombok.Data;

@Data
public class UbahPasswordRequest {
    private String passwordAsli;
    private String passwordBaru1;
    private String passwordBaru2;
}