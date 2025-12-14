package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PegawaiHrdComboResponse {
    private String namaLengkap;
    private Integer kdJabatan;
    private String namaJabatan;
}
