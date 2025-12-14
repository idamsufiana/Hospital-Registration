package com.hospital.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "status_absen")
@Data
public class StatusAbsen {

    @Id
    private Integer kdStatus;

    @Column(nullable = false)
    private String namaStatus;
}
