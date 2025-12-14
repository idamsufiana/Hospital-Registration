package com.hospital.registration.utils;

import com.hospital.registration.model.KodeDepartemen;
import com.hospital.registration.model.User;
import com.hospital.registration.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("pegawaiSecurityService")
public class PegawaiSecurityService {

    private final UserRepository userRepo;

    public PegawaiSecurityService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isAdminOrHrd(Object principal) {

        if (principal == null) return false;

        UUID userId = UUID.fromString(principal.toString());

        User user = userRepo.findById(userId)
                .orElse(null);

        if (user == null) return false;

        // ADMIN by role
        if ("ADMIN".equals(user.getProfile())) {
            return true;
        }

        // HRD by departemen
        return user.getKdDepartemen() != null
                && user.getKdDepartemen().equals(KodeDepartemen.HRD);
    }
}