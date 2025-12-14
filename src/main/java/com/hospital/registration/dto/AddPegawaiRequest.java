package com.hospital.registration.dto;

import lombok.Data;

@Data
public class AddPegawaiRequest {

    private String namaLengkap;
    private String email;
    private String tempatLahir;
    private Long tanggalLahir;

    private Integer kdJenisKelamin;
    private Integer kdPendidikan;
    private Integer kdJabatan;
    private Integer kdDepartemen;
    private Integer kdUnitKerja;

    private String password;
    private String passwordC;
}
