package entity;

/**
 *
 * @author vanphatdev
 */
public class KhachHang {
    private int maKH;
    private String hoTen;
    private String soDT;
    private String soCMND;
    private String diaChi;
    private boolean gioiTinh;

    public KhachHang() {
    }

    public KhachHang(int maKH) {
        this.maKH = maKH;
    }

    public KhachHang(int maKH, String hoTen, String soDT, String soCMND, String diaChi, boolean gioiTinh) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.soCMND = soCMND;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
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

    @Override
    public String toString() {
        return "KhachHang{" + "maKH=" + maKH + ", hoTen=" + hoTen + ", soDT=" + soDT + ", soCMND=" + soCMND + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.maKH;
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
        final KhachHang other = (KhachHang) obj;
        if (this.maKH != other.maKH) {
            return false;
        }
        return true;
    }
}
