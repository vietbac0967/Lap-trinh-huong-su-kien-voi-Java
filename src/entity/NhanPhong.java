package entity;

import java.time.LocalDateTime;

/**
 *
 * @author vanphatdev
 */
public class NhanPhong {
    private LocalDateTime ngayNhanPhong;
    private String maPhong;
    private String maKH;

    public NhanPhong() {
    }

    public NhanPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public NhanPhong(LocalDateTime ngayNhanPhong, String maPhong, String maKH) {
        this.ngayNhanPhong = ngayNhanPhong;
        this.maPhong = maPhong;
        this.maKH = maKH;
    }

    public LocalDateTime getNgayNhanPhong() {
        return ngayNhanPhong;
    }

    public void setNgayNhanPhong(LocalDateTime ngayNhanPhong) {
        this.ngayNhanPhong = ngayNhanPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    @Override
    public String toString() {
        return "NhanPhong{" + "ngayNhanPhong=" + ngayNhanPhong + ", maPhong=" + maPhong + ", maKH=" + maKH + '}';
    }
}
