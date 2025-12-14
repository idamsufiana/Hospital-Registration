package com.hospital.registration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="jabatan")
@Data
public class Jabatan {
    @Id
    private Integer kdJabatan;
    private String namaJabatan;
}