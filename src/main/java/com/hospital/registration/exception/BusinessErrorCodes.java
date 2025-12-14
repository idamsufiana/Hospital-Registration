package com.hospital.registration.exception;

public final class BusinessErrorCodes {

    private BusinessErrorCodes() {}

    // AUTH / LOGIN
    public static final String EMAIL_ALREADY_USED = "EMAIL_ALREADY_USED";
    public static final String EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND";
    public static final String PROFILE_NOT_MATCH = "PROFILE_NOT_MATCH";
    public static final String PASSWORD_INVALID = "PASSWORD_INVALID";

    // PASSWORD
    public static final String NEW_PASSWORD_MISMATCH = "NEW_PASSWORD_MISMATCH";
    public static final String OLD_PASSWORD_INVALID = "OLD_PASSWORD_INVALID";
    public static final String PASSWORD_CONFIRM_MISMATCH = "PASSWORD_CONFIRM_MISMATCH";

    // USER / PEGAWAI
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";

    // MASTER DATA
    public static final String HRD_DEPARTMENT_NOT_FOUND = "HRD_DEPARTMENT_NOT_FOUND";

    // FILE
    public static final String UPLOAD_PHOTO_FAILED = "UPLOAD_PHOTO_FAILED";
}
