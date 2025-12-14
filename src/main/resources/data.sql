-- ========================
-- JENIS KELAMIN
-- ========================
INSERT INTO jenis_kelamin (kd_jenis_kelamin, nama_jenis_kelamin) VALUES
(1, 'Laki-laki'),
(2, 'Perempuan')
ON CONFLICT (kd_jenis_kelamin) DO NOTHING;

-- ========================
-- PENDIDIKAN
-- ========================
INSERT INTO pendidikan (kd_pendidikan, nama_pendidikan) VALUES
(1, 'SMA / SMK'),
(2, 'D3'),
(3, 'S1'),
(4, 'S2'),
(5, 'S3')
ON CONFLICT (kd_pendidikan) DO NOTHING;

-- ========================
-- JABATAN
-- ========================
INSERT INTO jabatan (kd_jabatan, nama_jabatan) VALUES
(1, 'Staff'),
(2, 'Supervisor'),
(3, 'Manager'),
(4, 'Direktur')
ON CONFLICT (kd_jabatan) DO NOTHING;

-- ========================
-- DEPARTEMEN (HOSPITAL)
-- ========================
INSERT INTO departemen (kd_departemen, nama_departemen) VALUES
(1, 'Direksi'),
(2, 'Administrasi & Keuangan'),
(3, 'HRD'),
(4, 'Pelayanan Medis'),
(5, 'Keperawatan'),
(6, 'Farmasi'),
(7, 'Laboratorium'),
(8, 'Radiologi'),
(9, 'Rekam Medis'),
(10, 'IT & Sistem Informasi'),
(11, 'Umum & Logistik'),
(12, 'Keamanan'),
(13, 'Kebersihan')
ON CONFLICT (kd_departemen) DO NOTHING;

-- ========================
-- UNIT KERJA
-- ========================
INSERT INTO unit_kerja (kd_unit_kerja, nama_unit_kerja) VALUES
(1, 'Head Office'),
(2, 'Branch Office')
ON CONFLICT (kd_unit_kerja) DO NOTHING;

-- ========================
-- STATUS ABSEN
-- ========================

INSERT INTO status_absen (kd_status, nama_status) VALUES
(1, 'HADIR'),
(2, 'IZIN'),
(3, 'SAKIT'),
(4, 'CUTI'),
(5, 'ALPA')
ON CONFLICT (kd_status) DO NOTHING;