package com.hospital.registration.service;

import com.hospital.registration.config.JwtService;
import com.hospital.registration.dto.*;
import com.hospital.registration.exception.ApiBusinessException;
import com.hospital.registration.model.Company;
import com.hospital.registration.model.User;
import com.hospital.registration.repository.CompanyRepository;
import com.hospital.registration.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.hospital.registration.exception.BusinessErrorCodes.*;
import static com.hospital.registration.UserMapper.toUserInfo;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final CompanyRepository companyRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo, CompanyRepository companyRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // TODO: isi master combo saat init (jabatan/departemen/unit kerja/pendidikan/jenis kelamin/status absen)
    @Transactional
    public InitDataResponse initData(InitDataRequest req) {

        Company company = new Company();

        if (companyRepo.existsByNamaPerusahaanIgnoreCase(req.getPerusahaan())) {
            company= companyRepo.findByNamaPerusahaan(req.getPerusahaan());
        }else{
            company = Company.builder()
                    .id(UUID.randomUUID())
                    .namaPerusahaan(req.getPerusahaan())
                    .build();
            companyRepo.save(company);
        }

        String email = slug(req.getNamaAdmin()) + "@"+ slug(req.getPerusahaan()) + ".com";
        String rawPass = randomPass();
        if (userRepo.existsByEmail(email)) {
            throw new ApiBusinessException(EMAIL_ALREADY_USED,
                    "Email admin sudah digunakan"
            );
        }
        User admin = User.builder()
                .idUser(UUID.randomUUID())
                .company(company)
                .profile(UserProfile.ADMIN.name())
                .namaLengkap(req.getNamaAdmin())
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPass))
                .kdDepartemen(null)
                .build();
        userRepo.save(admin);

        InitDataResponse res = new InitDataResponse();
        res.setEmail(email);
        res.setPassword(rawPass);
        res.setProfile(UserProfile.ADMIN.name());
        return res;
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new ApiBusinessException(EMAIL_NOT_FOUND,"Email tidak ditemukan"));

        if (!user.getProfile().equalsIgnoreCase(req.getProfile()))
            throw new ApiBusinessException(PROFILE_NOT_MATCH,"Profile tidak sesuai");

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new ApiBusinessException(PASSWORD_INVALID,"Password salah");

        String token = jwtService.generateToken(String.valueOf(user.getIdUser()), user.getProfile(), user.getEmail());

        return new LoginResponse(token, toUserInfo(user));
    }

    @Transactional
    public String ubahPasswordSendiri(String userId, UbahPasswordRequest req) {
        if (!Objects.equals(req.getPasswordBaru1(), req.getPasswordBaru2()))
            throw new ApiBusinessException(NEW_PASSWORD_MISMATCH,"Password baru tidak sama");

        User u = userRepo.findById(UUID.fromString(userId)).orElseThrow();
        if (!passwordEncoder.matches(req.getPasswordAsli(), u.getPasswordHash()))
            throw new ApiBusinessException(OLD_PASSWORD_INVALID,"Password asli salah");

        u.setPasswordHash(passwordEncoder.encode(req.getPasswordBaru1()));
        userRepo.save(u);
        return "Ubah password Berhasil";
    }

    private String slug(String s){ return s.toLowerCase().replaceAll("[^a-z0-9]+",""); }
    private String randomPass(){ return UUID.randomUUID().toString().replace("-","").substring(0,10); }


}
