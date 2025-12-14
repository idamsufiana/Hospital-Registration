package com.hospital.registration.repository;

import com.hospital.registration.exception.ApiBusinessException;
import com.hospital.registration.model.StatusAbsen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusAbsenRepository extends JpaRepository<StatusAbsen, Integer> {

    /**
     * Ambil status HADIR (untuk check-in)
     */
    @Query("""
        select s
        from StatusAbsen s
        where upper(s.namaStatus) = 'HADIR'
    """)
    Optional<StatusAbsen> findHadirOptional();

    /**
     * Helper langsung return entity
     */
    default StatusAbsen findHadir() {
        return findHadirOptional()
                .orElseThrow(() ->
                        new ApiBusinessException(
                                "STATUS_HADIR_NOT_FOUND",
                                "Status HADIR belum dikonfigurasi"
                        )
                );
    }
}
