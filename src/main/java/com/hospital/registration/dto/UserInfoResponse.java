package com.hospital.registration.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoResponse {
    private UUID idUser;
    private String profile;
    private String namaLengkap;
    private String email;
    private Long tanggalLahir;
    private Integer kdJabatan;
    private Integer kdDepartemen;
    private Integer kdUnitKerja;
    private Integer kdJenisKelamin;
    private Integer kdPendidikan;
    private String photo;
}
