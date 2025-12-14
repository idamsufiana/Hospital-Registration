package com.hospital.registration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @Column(length = 36)
    private UUID id;   // UUID String

    @Column(nullable = false, unique = true)
    private String namaPerusahaan;

    // Optional metadata (future proof)
    private String alamat;
    private String email;
    private String phone;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // 1 Company : N Users (Admin & Pegawai)
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> users;
}