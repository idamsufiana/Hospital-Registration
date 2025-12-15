-- =========================================
-- EXTENSION
-- =========================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================================
-- COMPANY
-- =========================================
CREATE TABLE IF NOT EXISTS company (
    id UUID PRIMARY KEY,
    nama_perusahaan VARCHAR(255) NOT NULL UNIQUE,
    alamat TEXT,
    email VARCHAR(255),
    phone VARCHAR(50),
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ
);

-- =========================================
-- MASTER TABLES
-- =========================================
CREATE TABLE IF NOT EXISTS departemen (
    kd_departemen INTEGER PRIMARY KEY,
    nama_departemen VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS jabatan (
    kd_jabatan INTEGER PRIMARY KEY,
    nama_jabatan VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS jenis_kelamin (
    kd_jenis_kelamin INTEGER PRIMARY KEY,
    nama_jenis_kelamin VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS pendidikan (
    kd_pendidikan INTEGER PRIMARY KEY,
    nama_pendidikan VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS unit_kerja (
    kd_unit_kerja INTEGER PRIMARY KEY,
    nama_unit_kerja VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS status_absen (
    kd_status INTEGER PRIMARY KEY,
    nama_status VARCHAR(255) NOT NULL
);

-- =========================================
-- USERS
-- =========================================
CREATE TABLE IF NOT EXISTS users (
    id_user UUID PRIMARY KEY,
    company_id UUID,
    profile VARCHAR(20) NOT NULL,
    nama_lengkap VARCHAR(255) NOT NULL,
    tempat_lahir VARCHAR(255),
    tanggal_lahir DATE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    nik_user VARCHAR(50),
    kd_jabatan INTEGER,
    kd_departemen INTEGER,
    kd_unit_kerja INTEGER,
    kd_jenis_kelamin INTEGER,
    kd_pendidikan INTEGER,
    photo TEXT
);

-- FK users -> company

ALTER TABLE users
DROP CONSTRAINT IF EXISTS fk_user_company;

ALTER TABLE users
    ADD CONSTRAINT fk_user_company
    FOREIGN KEY (company_id)
    REFERENCES company(id)
    ON DELETE SET NULL;

-- =========================================
-- PRESENSI
CREATE TABLE IF NOT EXISTS presensi (
    id BIGSERIAL PRIMARY KEY,

    user_id UUID NOT NULL,
    tgl_absensi DATE NOT NULL,

    jam_masuk TIME NULL,
    jam_keluar TIME NULL,

    kd_status INTEGER NOT NULL
);

ALTER TABLE presensi
DROP CONSTRAINT IF EXISTS fk_presensi_user;
-- FK presensi -> users

ALTER TABLE presensi
    ADD CONSTRAINT fk_presensi_user
    FOREIGN KEY (user_id)
    REFERENCES users(id_user)
    ON DELETE CASCADE;


ALTER TABLE presensi
DROP CONSTRAINT IF EXISTS fk_presensi_status;

-- FK presensi -> status_absen
ALTER TABLE presensi
    ADD CONSTRAINT fk_presensi_status
    FOREIGN KEY (kd_status)
    REFERENCES status_absen(kd_status);

ALTER TABLE presensi
    DROP CONSTRAINT IF EXISTS uk_presensi_user_tgl;

ALTER TABLE presensi
    ADD CONSTRAINT uk_presensi_user_tgl
    UNIQUE (user_id, tgl_absensi);


-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX IF NOT EXISTS idx_users_company
    ON users(company_id);

CREATE INDEX IF NOT EXISTS idx_users_profile
    ON users(profile);

CREATE INDEX IF NOT EXISTS idx_presensi_user_tgl
    ON presensi(user_id, tgl_absensi);
