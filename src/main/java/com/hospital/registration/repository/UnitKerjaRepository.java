package com.hospital.registration.repository;

import com.hospital.registration.model.UnitKerja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UnitKerjaRepository extends JpaRepository<UnitKerja, Integer> {

    @Query("""
        select u.namaUnitKerja
        from UnitKerja u
        where u.kdUnitKerja = :id
    """)
    Optional<String> findNamaById(Integer id);
}