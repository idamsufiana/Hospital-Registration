package com.hospital.registration.repository;

import com.hospital.registration.dto.PegawaiHrdComboResponse;
import com.hospital.registration.model.Jabatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JabatanRepository extends JpaRepository<Jabatan, Integer> {

    @Query("""
        select j.namaJabatan
        from Jabatan j
        where j.kdJabatan = :id
    """)
    Optional<String> findNamaById(Integer id);

    @Query("""
    select new com.hospital.registration.dto.PegawaiHrdComboResponse(
        u.namaLengkap,
        u.kdJabatan,
        j.namaJabatan
    )
    from User u
    left join Jabatan j on j.kdJabatan = u.kdJabatan
    where u.kdDepartemen = :kdDepartemen
""")
    List<PegawaiHrdComboResponse> findPegawaiHrdCombo(@Param("kdDepartemen") Integer kdDepartemen);

}