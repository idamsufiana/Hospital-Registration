package com.hospital.registration.controller;

import com.hospital.registration.dto.AbseniRequest;
import com.hospital.registration.service.PresensiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PresensiController extends BaseController {

    private final PresensiService presensiService;

    public PresensiController(PresensiService presensiService) {
        this.presensiService = presensiService;
    }

    /**
     * Combo status absen / izin
     */
    @GetMapping("/presensi/combo/status-absen")
    public ResponseEntity<?> comboStatusAbsen(
            @RequestParam Integer tglAwal,
            @RequestParam Integer tglAkhir
    ) {
        return ok(presensiService.comboStatusAbsen(tglAwal, tglAkhir));
    }

    /**
     * Daftar presensi & absensi seluruh pegawai (ADMIN / HRD)
     */
    @GetMapping("/presensi/daftar/admin")
    @PreAuthorize("@pegawaiSecurityService.isAdminOrHrd(authentication.principal)")
    public ResponseEntity<?> daftarPresensiAdmin(
            @RequestParam Integer tglAwal,
            @RequestParam Integer tglAkhir
    ) {
        return ok(presensiService.daftarPresensiAdmin(tglAwal, tglAkhir));
    }


    /**
     * Daftar presensi & absensi pegawai sendiri
     */
    @GetMapping("/presensi/daftar/pegawai")
    @PreAuthorize("@pegawaiSecurityService.isPegawai(authentication.principal)")
    public ResponseEntity<?> daftarPresensiPegawai(
            @RequestParam Integer tglAwal,
            @RequestParam Integer tglAkhir
    ) {
        return ok(presensiService.daftarPresensiPegawai(tglAwal, tglAkhir));
    }

    /**
     * Check-in presensi (jam server)
     */
    @GetMapping("/presensi/in")
    @PreAuthorize("@pegawaiSecurityService.isPegawai(authentication.principal)")
    public ResponseEntity<?> checkIn() {
        return ok(presensiService.checkIn());
    }

    /**
     * Check-out presensi (jam server)
     */
    @GetMapping("/presensi/out")
    @PreAuthorize("@pegawaiSecurityService.isPegawai(authentication.principal)")
    public ResponseEntity<?> checkOut() {
        return ok(presensiService.checkOut());
    }

    /**
     * Absensi / izin (tidak masuk)
     */
    @PostMapping("/presensi/abseni")
    @PreAuthorize("@pegawaiSecurityService.isPegawai(authentication.principal)")
    public ResponseEntity<?> abseni(@RequestBody AbseniRequest req) {
        return ok(presensiService.abseni(req));
    }
}
