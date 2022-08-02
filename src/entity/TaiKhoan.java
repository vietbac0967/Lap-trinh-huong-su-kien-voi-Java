package entity;

import java.util.Objects;

/**
 *
 * @author nguye
 */
public class TaiKhoan {
    private String tenDN;
    private String matKhau;
    private NhanVien nhanVien;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDN) {
        this.tenDN = tenDN;
    }

    public TaiKhoan(String tenDN, String matKhau, NhanVien nhanVien) {
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "tenDN=" + tenDN + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.tenDN);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaiKhoan other = (TaiKhoan) obj;
        if (!Objects.equals(this.tenDN, other.tenDN)) {
            return false;
        }
        return true;
    }
}
