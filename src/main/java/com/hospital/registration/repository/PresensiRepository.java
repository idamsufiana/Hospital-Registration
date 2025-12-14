package com.hospital.registration.repository;

import com.hospital.registration.dto.ComboStatusAbsenResponse;
import com.hospital.registration.dto.PresensiAdminResponse;
import com.hospital.registration.dto.PresensiPegawaiResponse;
import com.hospital.registration.model.Presensi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresensiRepository extends JpaRepository<Presensi, Long> {

    @Query("""
        SELECT new com.hospital.registration.dto.ComboStatusAbsenResponse(
            s.kdStatus,
            s.namaStatus
        )
        FROM StatusAbsen s
        ORDER BY s.kdStatus
    """)
    List<ComboStatusAbsenResponse> findStatusAbsen(
            @Param("tglAwal") Integer tglAwal,
            @Param("tglAkhir") Integer tglAkhir
    );

    @Query("""
    SELECT new com.hospital.registration.dto.PresensiAdminResponse(
        u.id,
        u.namaLengkap,
        p.tglAbsensi,
        p.jamMasuk,
        p.jamKeluar,
        s.namaStatus
    )
    FROM Presensi p
    JOIN p.user u
    JOIN p.status s
    WHERE p.tglAbsensi BETWEEN :tglAwal AND :tglAkhir
    ORDER BY p.tglAbsensi DESC
""")
    List<PresensiAdminResponse> findPresensiAdmin(
            @Param("tglAwal") LocalDate tglAwal,
            @Param("tglAkhir") LocalDate tglAkhir
    );

    @Query("""
        select new com.hospital.registration.dto.PresensiPegawaiResponse(
            p.tglAbsensi,
            p.jamMasuk,
            p.jamKeluar,
            s.namaStatus
        )
        from Presensi p
        join p.status s
        where p.user.id = :userId
          and p.tglAbsensi between :tglAwal and :tglAkhir
        order by p.tglAbsensi desc
    """)
    List<PresensiPegawaiResponse> findPresensiPegawai(
            UUID userId,
            Integer tglAwal,
            Integer tglAkhir
    );

    Optional<Presensi> findByUserAndTglAbsensi(UUID userId, Integer tglAbsensi);

    boolean existsByUserAndTglAbsensi(UUID userId, Integer tglAbsensi);
}
