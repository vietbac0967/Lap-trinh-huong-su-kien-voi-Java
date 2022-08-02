package entity;

import java.time.LocalDateTime;

/**
 *
 * @author vanphatdev
 */
public class HoaDon {
    private int maHD;
    private double tienPhong;
    private double tienDichVu;
    private double tongTien;
    private LocalDateTime ngayNhanPhong;
    private LocalDateTime ngayTraPhong;
    private String maNV;
    private int maKH;
    private String maPhong;

    public HoaDon() {
    }

    public HoaDon(int maHD, double tienPhong, double tienDichVu, double tongTien, LocalDateTime ngayNhanPhong, LocalDateTime ngayTraPhong, String maNV, int maKH, String maPhong) {
        this.maHD = maHD;
        this.tienPhong = tienPhong;
        this.tienDichVu = tienDichVu;
        this.tongTien = tongTien;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.maNV = maNV;
        this.maKH = maKH;
        this.maPhong = maPhong;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(double tienPhong) {
        this.tienPhong = tienPhong;
    }

    public double getTienDichVu() {
        return tienDichVu;
    }

    public void setTienDichVu(double tienDichVu) {
        this.tienDichVu = tienDichVu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getNgayNhanPhong() {
        return ngayNhanPhong;
    }

    public void setNgayNhanPhong(LocalDateTime ngayNhanPhong) {
        this.ngayNhanPhong = ngayNhanPhong;
    }

    public LocalDateTime getNgayTraPhong() {
        return ngayTraPhong;
    }

    public void setNgayTraPhong(LocalDateTime ngayTraPhong) {
        this.ngayTraPhong = ngayTraPhong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHD=" + maHD + ", tienPhong=" + tienPhong + ", tienDichVu=" + tienDichVu + ", tongTien=" + tongTien + ", ngayNhanPhong=" + ngayNhanPhong + ", ngayTraPhong=" + ngayTraPhong + ", maNV=" + maNV + ", maKH=" + maKH + ", maPhong=" + maPhong + '}';
    }
    
    public String get10char(String s) {
        return s.substring(0, 10);
    }
    
    public String getNgayNhanString() {
        return get10char(this.ngayNhanPhong.toString());
    }
    
    public String getNgayTraString() {
        return get10char(this.ngayTraPhong.toString());
    }
}
