package com.hospital.registration.service;

import com.hospital.registration.dto.*;
import com.hospital.registration.exception.ApiBusinessException;
import com.hospital.registration.model.*;
import com.hospital.registration.repository.*;
import com.hospital.registration.utility.UserMapper;
import com.hospital.registration.utils.EpochUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

import static com.hospital.registration.exception.BusinessErrorCodes.*;

@Service
public class PegawaiService {
    public static final String HRD = "HRD";

    @Value("${app.upload.photo-path}")
    private String photoUploadPath;

    private final UserRepository userRepo;
    private final JabatanRepository jabatanRepo;
    private final DepartemenRepository departemenRepo;
    private final UnitKerjaRepository unitKerjaRepo;
    private final PendidikanRepository pendidikanRepo;
    private final JenisKelaminRepository jenisKelaminRepo;

    private final PasswordEncoder passwordEncoder ;

    public PegawaiService(UserRepository userRepo, JabatanRepository jabatanRepo, DepartemenRepository departemenRepo, UnitKerjaRepository unitKerjaRepo, PendidikanRepository pendidikanRepo, JenisKelaminRepository jenisKelaminRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jabatanRepo = jabatanRepo;
        this.departemenRepo = departemenRepo;
        this.unitKerjaRepo = unitKerjaRepo;
        this.pendidikanRepo = pendidikanRepo;
        this.jenisKelaminRepo = jenisKelaminRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /* =========================================================
       COMBO DATA
       ========================================================= */

    public List<ComboResponse> comboJabatan() {
        return toComboList(
                jabatanRepo.findAll(),
                Jabatan::getKdJabatan,
                Jabatan::getNamaJabatan
        );
    }


    public List<ComboResponse> comboDepartemen() {
        return toComboList(
                departemenRepo.findAll(),
                Departemen::getKdDepartemen,
                Departemen::getNamaDepartemen
        );
    }


    public List<ComboResponse> comboUnitKerja() {
        return toComboList(
                unitKerjaRepo.findAll(),
                UnitKerja::getKdUnitKerja,
                UnitKerja::getNamaUnitKerja
        );
    }


    public List<ComboResponse> comboPendidikan() {
        return toComboList(
                pendidikanRepo.findAll(),
                Pendidikan::getKdPendidikan,
                Pendidikan::getNamaPendidikan
        );
    }


    public List<ComboResponse> comboJenisKelamin() {
        return toComboList(
                jenisKelaminRepo.findAll(),
                JenisKelamin::getKdJenisKelamin,
                JenisKelamin::getNamaJenisKelamin
        );
    }

    /* =========================================================
       COMBO PEGAWAI HRD
       ========================================================= */

    public List<PegawaiHrdComboResponse> comboPegawaiHrd() {

        Integer hrdId = departemenRepo.findIdByNamaIgnoreCase(HRD)
                .orElseThrow(() -> new ApiBusinessException(
                        HRD_DEPARTMENT_NOT_FOUND,
                        "Departemen HRD tidak ditemukan"
                ));

        return jabatanRepo.findPegawaiHrdCombo(hrdId);
    }


    /* =========================================================
       DAFTAR PEGAWAI (ADMIN / HRD)
       ========================================================= */

    public List<PegawaiListDto> daftarPegawaiLengkap() {
        return userRepo.findPegawaiLengkap();
    }
    /* =========================================================
       ADMIN TAMBAH PEGAWAI
       ========================================================= */

    @Transactional
    public UserDto adminTambah(AddPegawaiRequest req) {


        if (!req.getPassword().equals(req.getPasswordC())) {
            throw new ApiBusinessException(PASSWORD_CONFIRM_MISMATCH,"Password dan konfirmasi password tidak sama");
        }

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new ApiBusinessException(EMAIL_ALREADY_USED,"Email sudah terdaftar");
        }

        User user = User.builder()
                .idUser(UUID.randomUUID())
                .profile(UserProfile.PEGAWAI.name())
                .namaLengkap(req.getNamaLengkap())
                .email(req.getEmail())
                .tempatLahir(req.getTempatLahir())
                .tanggalLahir(EpochUtil.toLocalDate(req.getTanggalLahir()))
                .kdJenisKelamin(req.getKdJenisKelamin())
                .kdPendidikan(req.getKdPendidikan())
                .kdJabatan(req.getKdJabatan())
                .kdDepartemen(req.getKdDepartemen())
                .kdUnitKerja(req.getKdUnitKerja())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();

        userRepo.save(user);
        return UserMapper.toUserDto(user);
    }

    /* =========================================================
       ADMIN UBAH PEGAWAI
       ========================================================= */

    @Transactional
    public UserDto adminUbah(UpdatePegawaiRequest req) {

        User user = userRepo.findById(req.getIdUser())
                .orElseThrow(() -> new ApiBusinessException(USER_NOT_FOUND,"User tidak ditemukan"));

        if (!req.getPassword().equals(req.getPasswordC())) {
            throw new ApiBusinessException(NEW_PASSWORD_MISMATCH,"Password dan konfirmasi password tidak sama");
        }

        user.setNamaLengkap(req.getNamaLengkap());
        user.setEmail(req.getEmail());
        user.setTempatLahir(req.getTempatLahir());
        user.setTanggalLahir(EpochUtil.toLocalDate(req.getTanggalLahir()));
        user.setKdJenisKelamin(req.getKdJenisKelamin());
        user.setKdPendidikan(req.getKdPendidikan());
        user.setKdJabatan(req.getKdJabatan());
        user.setKdDepartemen(req.getKdDepartemen());
        user.setKdUnitKerja(req.getKdUnitKerja());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        }

        userRepo.save(user);
        return UserMapper.toUserDto(user);
    }

    /* =========================================================
       UBAH PHOTO (ADMIN / SENDIRI)
       ========================================================= */

    @Transactional
    public UserDto ubahPhoto(UUID userId, String namaFile, MultipartFile file) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApiBusinessException(USER_NOT_FOUND,"User tidak ditemukan"));
        try {
            
            Path uploadDir = Paths.get(photoUploadPath);
            Files.createDirectories(uploadDir);

            String fileName = userId + "_" + namaFile;
            Path target = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            user.setPhoto("/uploads/photo/" + fileName);
            userRepo.save(user);

        } catch (Exception e) {
            throw new ApiBusinessException(UPLOAD_PHOTO_FAILED,"Gagal upload photo");
        }

        return UserMapper.toUserDto(user);
    }

    private <T> List<ComboResponse> toComboList(
            List<T> entities,
            Function<T, Integer> codeMapper,
            Function<T, String> nameMapper
    ) {
        return entities.stream()
                .map(e -> new ComboResponse(
                        codeMapper.apply(e),
                        nameMapper.apply(e)
                ))
                .toList();
    }

}
