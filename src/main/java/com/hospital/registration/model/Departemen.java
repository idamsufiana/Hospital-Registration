package com.hospital.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "departemen")
@Data
public class Departemen {

    @Id
    private Integer kdDepartemen;

    @Column(nullable = false)
    private String namaDepartemen;
}