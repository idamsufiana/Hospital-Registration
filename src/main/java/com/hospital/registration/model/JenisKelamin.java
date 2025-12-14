package com.hospital.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "jenis_kelamin")
@Data
public class JenisKelamin {

    @Id
    private Integer kdJenisKelamin;

    @Column(nullable = false)
    private String namaJenisKelamin;
}