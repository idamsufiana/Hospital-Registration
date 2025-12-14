package com.hospital.registration.service;

import com.hospital.registration.config.CustomUserPrincipal;
import com.hospital.registration.dto.*;
import com.hospital.registration.exception.ApiBusinessException;
import com.hospital.registration.model.Presensi;
import com.hospital.registration.model.StatusAbsen;
import com.hospital.registration.repository.PresensiRepository;
import com.hospital.registration.repository.StatusAbsenRepository;
import com.hospital.registration.repository.UserRepository;
import com.hospital.registration.utils.EpochUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class PresensiService {


    private final PresensiRepository presensiRepo;
    private final StatusAbsenRepository statusRepo;
    private final UserRepository userRepo;

    public PresensiService(
            PresensiRepository presensiRepo,
            StatusAbsenRepository statusRepo,
            UserRepository userRepo
    ) {
        this.presensiRepo = presensiRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
    }

    /**
     * Combo-box status absen / izin
     */
    public List<ComboStatusAbsenResponse> comboStatusAbsen(
            Integer tglAwal,
            Integer tglAkhir
    ) {
        return presensiRepo.findStatusAbsen(tglAwal, tglAkhir);
    }

    /**
     * Daftar presensi seluruh pegawai (ADMIN / HRD)
     */
    public List<PresensiAdminResponse> daftarPresensiAdmin(
            Integer tglAwal,
            Integer tglAkhir
    ) {
        return presensiRepo.findPresensiAdmin(EpochUtil.toLocalDate(Long.valueOf(tglAwal)), EpochUtil.toLocalDate(Long.valueOf(tglAkhir)));
    }

    public List<PresensiPegawaiResponse> daftarPresensiPegawai(
            Integer tglAwal,
            Integer tglAkhir
    ) {
        UUID userId = UUID.fromString(getCurrentUserId());
        return presensiRepo.findPresensiPegawai(userId, tglAwal, tglAkhir);
    }

    /* ==============================
     * CHECK-IN
     * ============================== */
    public CheckInResponse checkIn() {
        UUID userId = UUID.fromString(getCurrentUserId());
        Integer today = getTodayEpoch();
        String now = LocalTime.now().toString();

        presensiRepo.findByUserAndTglAbsensi(userId, today)
                .ifPresent(p -> {
                    if (p.getJamMasuk() != null) {
                        throw new ApiBusinessException(
                                "CHECKIN_DUPLICATE",
                                "Sudah check-in hari ini"
                        );
                    }
                });

        Presensi presensi = presensiRepo
                .findByUserAndTglAbsensi(userId, today)
                .orElse(new Presensi());

        presensi.setUser(userRepo.getReferenceById(userId));
        presensi.setTglAbsensi(EpochUtil.toLocalDate(Long.valueOf(today)));
        presensi.setJamMasuk(now);
        presensi.setStatus(statusRepo.findHadir());

        presensiRepo.save(presensi);

        return new CheckInResponse(now);
    }

    /* ==============================
     * CHECK-OUT
     * ============================== */
    public CheckOutResponse checkOut() {
        UUID userId = UUID.fromString(getCurrentUserId());
        Integer today = getTodayEpoch();
        String now = LocalTime.now().toString();

        Presensi presensi = presensiRepo.findByUserAndTglAbsensi(userId, today)
                .orElseThrow(() ->
                        new ApiBusinessException(
                                "CHECKOUT_INVALID",
                                "Belum check-in hari ini"
                        )
                );

        if (presensi.getJamKeluar() != null) {
            throw new ApiBusinessException(
                    "CHECKOUT_DUPLICATE",
                    "Sudah check-out hari ini"
            );
        }

        presensi.setJamKeluar(now);
        presensiRepo.save(presensi);

        return new CheckOutResponse(now);
    }

    /* ==============================
     * ABSENI / IZIN
     * ============================== */
    public AbseniResponse abseni(AbseniRequest req) {
        UUID userId = UUID.fromString(getCurrentUserId());

        if (presensiRepo.existsByUserAndTglAbsensi(userId, req.getTglAbsensi())) {
            throw new ApiBusinessException(
                    "ABSENI_DUPLICATE",
                    "Presensi sudah tercatat"
            );
        }

        StatusAbsen status = statusRepo.findById(req.getKdStatus())
                .orElseThrow(() ->
                        new ApiBusinessException(
                                "STATUS_NOT_FOUND",
                                "Status tidak ditemukan"
                        )
                );

        Presensi presensi = new Presensi();
        presensi.setUser(userRepo.getReferenceById(userId));
        presensi.setTglAbsensi(EpochUtil.toLocalDate(Long.valueOf(req.getTglAbsensi())));
        presensi.setStatus(status);

        presensiRepo.save(presensi);

        return new AbseniResponse(
                req.getTglAbsensi(),
                status.getNamaStatus()
        );
    }

    /* ==============================
     * HELPER
     * ============================== */
    private String getCurrentUserId() {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        return principal.getUserId();
    }

    private Integer getTodayEpoch() {
        return (int) LocalDate.now().toEpochDay();
    }


}
