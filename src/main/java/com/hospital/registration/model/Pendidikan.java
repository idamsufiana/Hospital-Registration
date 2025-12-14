package com.hospital.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pendidikan")
@Data
public class Pendidikan {

    @Id
    private Integer kdPendidikan;

    @Column(nullable = false)
    private String namaPendidikan;
}