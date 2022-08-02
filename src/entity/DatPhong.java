package entity;

import java.time.LocalDateTime;

/**
 *
 * @author vanphatdev
 */
public class DatPhong {
    private LocalDateTime ngayDatPhong;
    private String maPhong;
    private String maKH;

    public DatPhong() {
    }

    public DatPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public DatPhong(LocalDateTime ngayDatPhong, String maPhong, String maKH) {
        this.ngayDatPhong = ngayDatPhong;
        this.maPhong = maPhong;
        this.maKH = maKH;
    }

    public LocalDateTime getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(LocalDateTime ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
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
        return "DatPhong{" + "ngayDatPhong=" + ngayDatPhong + ", maPhong=" + maPhong + ", maKH=" + maKH + '}';
    }
}
