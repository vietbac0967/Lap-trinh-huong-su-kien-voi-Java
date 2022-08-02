package entity;

import java.util.Objects;

/**
 *
 * @author vanphatdev
 */
public class NhanVien {
    private String maNV;
    private String hoTen;
    private String soDT;
    private String soCMND;
    private String diaChi;
    private boolean gioiTinh;
    private boolean isQuanLi;

    public NhanVien() {
    }

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }

    public NhanVien(String maNV, String hoTen, String soDT, String soCMND, String diaChi, boolean gioiTinh, boolean isQuanLi) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.soCMND = soCMND;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.isQuanLi = isQuanLi;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isIsQuanLi() {
        return isQuanLi;
    }

    public void setIsQuanLi(boolean isQuanLi) {
        this.isQuanLi = isQuanLi;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNV=" + maNV + ", hoTen=" + hoTen + ", soDT=" + soDT + ", soCMND=" + soCMND + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + ", isQuanLi=" + isQuanLi + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.maNV);
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
        final NhanVien other = (NhanVien) obj;
        if (!Objects.equals(this.maNV, other.maNV)) {
            return false;
        }
        return true;
    }
}
