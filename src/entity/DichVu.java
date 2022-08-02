package entity;

/**
 *
 * @author vanphatdev
 */
public class DichVu {
    private int maDV;
    private String tenDV;
    private double donGia;
    private LoaiDichVu loaiDV;

    public DichVu() {
    }

    public DichVu(int maDV) {
        this.maDV = maDV;
    }

    public DichVu(int maDV, String tenDV, double donGia, LoaiDichVu loaiDV) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
        this.loaiDV = loaiDV;
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public LoaiDichVu getLoaiDV() {
        return loaiDV;
    }

    public void setLoaiDV(LoaiDichVu loaiDV) {
        this.loaiDV = loaiDV;
    }

    @Override
    public String toString() {
        return "DichVu{" + "maDV=" + maDV + ", tenDV=" + tenDV + ", donGia=" + donGia + ", loaiDV=" + loaiDV + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.maDV;
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
        final DichVu other = (DichVu) obj;
        if (this.maDV != other.maDV) {
            return false;
        }
        return true;
    }
}
