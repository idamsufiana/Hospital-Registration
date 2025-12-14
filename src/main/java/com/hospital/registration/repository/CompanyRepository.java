package com.hospital.registration.repository;

import com.hospital.registration.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

    boolean existsByNamaPerusahaanIgnoreCase(String namaPerusahaan);
    Company findByNamaPerusahaan(String namaPerusahaan);
}
