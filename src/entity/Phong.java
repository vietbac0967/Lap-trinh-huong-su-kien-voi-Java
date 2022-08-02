package entity;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author vanphatdev
 */
public class Phong {
    private String maPhong;
    private double giaPhong;
    private double dienTich;
    private int soGiuong;
    private String trangThaiPhong;
    private LoaiPhong loaiPhong;

    public static String PHONG_TRONG = "pt";
    public static String PHONG_DAT_TRUOC = "pdt";
    public static String PHONG_CO_NGUOI = "pcn";
    public static String PHONG_DANG_SUA = "pds";
    
    public Phong() {
    }

    public Phong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Phong(String maPhong, double giaPhong, double dienTich, int soGiuong, String trangThaiPhong, LoaiPhong loaiPhong) {
        this.maPhong = maPhong;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.soGiuong = soGiuong;
        this.trangThaiPhong = trangThaiPhong;
        this.loaiPhong = loaiPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public int getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(int soGiuong) {
        this.soGiuong = soGiuong;
    }

    public String getTrangThaiPhong() {
        return trangThaiPhong;
    }

    public void setTrangThaiPhong(String trangThaiPhong) {
        this.trangThaiPhong = trangThaiPhong;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    @Override
    public String toString() {
        return "Phong{" + "maPhong=" + maPhong + ", giaPhong=" + giaPhong + ", dienTich=" + dienTich + ", soGiuong=" + soGiuong + ", trangThaiPhong=" + trangThaiPhong + ", loaiPhong=" + loaiPhong + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.maPhong);
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
        final Phong other = (Phong) obj;
        if (!Objects.equals(this.maPhong, other.maPhong)) {
            return false;
        }
        return true;
    }
}
