package com.hospital.registration;

import com.hospital.registration.dto.UserDto;
import com.hospital.registration.dto.UserInfoResponse;
import com.hospital.registration.model.User;
import com.hospital.registration.utils.EpochUtil;

public class UserMapper {

    public static UserDto toUserDto(User u) {
        return new UserDto(u.getNamaLengkap(), u.getEmail());
    }

    public static UserInfoResponse toUserInfo(User u) {
        UserInfoResponse res = new UserInfoResponse();
        res.setIdUser(u.getIdUser());
        res.setProfile(u.getProfile());
        res.setNamaLengkap(u.getNamaLengkap());
        res.setEmail(u.getEmail());
        res.setTanggalLahir(EpochUtil.fromLocalDate(u.getTanggalLahir()));
        res.setKdJabatan(u.getKdJabatan());
        res.setKdDepartemen(u.getKdDepartemen());
        res.setKdUnitKerja(u.getKdUnitKerja());
        res.setKdJenisKelamin(u.getKdJenisKelamin());
        res.setKdPendidikan(u.getKdPendidikan());
        res.setPhoto(u.getPhoto());
        return res;
    }
}