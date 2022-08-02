package entity;

/**
 *
 * @author vanphatdev
 */
public class DatDichVu {
    private int maDatDV;
    private int maKH;
    private int maDV;
    private int soLuongDV;

    public DatDichVu() {
    }

    public DatDichVu(int maDatDV) {
        this.maDatDV = maDatDV;
    }

    public DatDichVu(int maDatDV, int maKH, int maDV, int soLuongDV) {
        this.maDatDV = maDatDV;
        this.maKH = maKH;
        this.maDV = maDV;
        this.soLuongDV = soLuongDV;
    }

    public int getMaDatDV() {
        return maDatDV;
    }

    public void setMaDatDV(int maDatDV) {
        this.maDatDV = maDatDV;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public int getSoLuongDV() {
        return soLuongDV;
    }

    public void setSoLuongDV(int soLuongDV) {
        this.soLuongDV = soLuongDV;
    }

    @Override
    public String toString() {
        return "DatDichVu{" + "maDatDV=" + maDatDV + ", maKH=" + maKH + ", maDV=" + maDV + ", soLuongDV=" + soLuongDV + '}';
    }
}
