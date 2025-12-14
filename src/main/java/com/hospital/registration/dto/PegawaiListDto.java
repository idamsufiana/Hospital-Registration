package com.hospital.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PegawaiListDto {

    private String profile;
    private UUID idUser;
    private String namaLengkap;
    private String tempatLahir;
    private LocalDate tanggalLahir;
    private String email;
    private String nikUser;

    private Integer kdJabatan;
    private String namaJabatan;

    private Integer kdDepartemen;
    private String namaDepartemen;

    private Integer kdUnitKerja;
    private String namaUnitKerja;

    private Integer kdJenisKelamin;
    private String namaJenisKelamin;

    private Integer kdPendidikan;
    private String namaPendidikan;

    private String photo;
}