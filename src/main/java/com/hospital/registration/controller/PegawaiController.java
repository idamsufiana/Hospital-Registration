package com.hospital.registration.controller;

import com.hospital.registration.dto.AddPegawaiRequest;
import com.hospital.registration.dto.UpdatePegawaiRequest;
import com.hospital.registration.service.PegawaiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PegawaiController extends BaseController{

    private final PegawaiService pegawaiService;

    public PegawaiController(PegawaiService pegawaiService) {
        this.pegawaiService = pegawaiService;
    }

    @GetMapping("/pegawai/combo/jabatan")
    public ResponseEntity<?> comboJabatan() {
        return ok(pegawaiService.comboJabatan()); }

    @GetMapping("/pegawai/combo/departemen")
    public ResponseEntity<?> comboDepartemen() {
        return ok(pegawaiService.comboDepartemen()); }

    @GetMapping("/pegawai/combo/unit-kerja")
    public ResponseEntity<?> comboUnitKerja() {
        return ok(pegawaiService.comboUnitKerja()); }


    @GetMapping("/pegawai/combo/pendidikan")
    public ResponseEntity<?> comboPendidikan() {
        return ok(pegawaiService.comboPendidikan()); }

    @GetMapping("/pegawai/combo/jenis-kelamin")
    public ResponseEntity<?> comboJenisKelamin() {
        return ok(pegawaiService.comboJenisKelamin()); }

    @GetMapping("/pegawai/combo/departemen-hrd")
    public ResponseEntity<?> comboDepartemenHrd() {
        return ok(pegawaiService.comboPegawaiHrd()); }

    @GetMapping("/pegawai/daftar")
    @PreAuthorize("@pegawaiSecurityService.isAdminOrHrd(authentication.principal)")
    public ResponseEntity<?> daftar() {
        return ok(pegawaiService.daftarPegawaiLengkap()); }

    @PostMapping("/pegawai/admin-tambah-pegawai")
    @PreAuthorize("@pegawaiSecurityService.isAdminOrHrd(authentication.principal)")
    public ResponseEntity<?> adminTambah(@RequestBody AddPegawaiRequest req) {
        return ok(pegawaiService.adminTambah(req)); }

    @PostMapping("/pegawai/admin-ubah-pegawai")
    @PreAuthorize("@pegawaiSecurityService.isAdminOrHrd(authentication.principal)")
    public ResponseEntity<?> adminUbah(@RequestBody UpdatePegawaiRequest req) {
        return ok(pegawaiService.adminUbah(req)); }

    @PostMapping(value="/pegawai/admin-ubah-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@pegawaiSecurityService.isAdminOrHrd(authentication.principal)")
    public ResponseEntity<?> adminUbahPhoto(@RequestParam UUID idUser,
                               @RequestParam String namaFile,
                               @RequestParam(name = "file", required = true) MultipartFile files) {
        return ok(pegawaiService.ubahPhoto(idUser, namaFile, files));
    }

    @PostMapping(value="/pegawai/ubah-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ubahPhotoSendiri(Authentication auth,
                                 @RequestParam String namaFile,
                                 @RequestParam(name = "file", required = true) MultipartFile files) {
        UUID userId = UUID.fromString(auth.getName());
        return ok(pegawaiService.ubahPhoto(userId, namaFile, files));
    }
}
