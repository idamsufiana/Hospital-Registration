package com.hospital.registration.repository;

import com.hospital.registration.model.JenisKelamin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JenisKelaminRepository extends JpaRepository<JenisKelamin, Integer> {

    @Query("""
        select j.namaJenisKelamin
        from JenisKelamin j
        where j.kdJenisKelamin = :id
    """)
    Optional<String> findNamaById(Integer id);
}