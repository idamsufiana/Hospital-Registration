package com.hospital.registration.repository;

import com.hospital.registration.model.Departemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartemenRepository extends JpaRepository<Departemen, Integer> {

    @Query("""
        select d.namaDepartemen
        from Departemen d
        where d.kdDepartemen = :id
    """)
    Optional<String> findNamaById(Integer id);

    @Query("""
        select d.kdDepartemen
        from Departemen d
        where lower(d.namaDepartemen) = lower(:nama)
    """)
    Optional<Integer> findIdByNamaIgnoreCase(String nama);
}