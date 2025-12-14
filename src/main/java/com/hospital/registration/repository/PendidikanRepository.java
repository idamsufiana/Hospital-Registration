package com.hospital.registration.repository;

import com.hospital.registration.model.Pendidikan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer> {

    @Query("""
        select p.namaPendidikan
        from Pendidikan p
        where p.kdPendidikan = :id
    """)
    Optional<String> findNamaById(Integer id);
}