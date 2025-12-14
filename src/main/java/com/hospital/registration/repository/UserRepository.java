package com.hospital.registration.repository;

import com.hospital.registration.dto.PegawaiListDto;
import com.hospital.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByKdDepartemen(Integer kdDepartemen);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("""
        select new com.hospital.registration.dto.PegawaiListDto(
            u.profile,
            u.idUser,
            u.namaLengkap,
            u.tempatLahir,
            u.tanggalLahir,
            u.email,
            u.nikUser,

            u.kdJabatan,
            j.namaJabatan,

            u.kdDepartemen,
            d.namaDepartemen,

            u.kdUnitKerja,
            uk.namaUnitKerja,

            u.kdJenisKelamin,
            jk.namaJenisKelamin,

            u.kdPendidikan,
            p.namaPendidikan,

            u.photo
        )
        from User u
        left join Jabatan j on j.kdJabatan = u.kdJabatan
        left join Departemen d on d.kdDepartemen = u.kdDepartemen
        left join UnitKerja uk on uk.kdUnitKerja = u.kdUnitKerja
        left join JenisKelamin jk on jk.kdJenisKelamin = u.kdJenisKelamin
        left join Pendidikan p on p.kdPendidikan = u.kdPendidikan
    """)
    List<PegawaiListDto> findPegawaiLengkap();
}