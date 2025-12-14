package com.hospital.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "unit_kerja")
@Data
public class UnitKerja {

    @Id
    private Integer kdUnitKerja;

    @Column(nullable = false)
    private String namaUnitKerja;
}