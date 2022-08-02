package entity;

import java.util.Objects;

/**
 *
 * @author vanphatdev
 */
public class LoaiDichVu {
    private String maLoaiDV;
    private String tenLoaiDV;

    public LoaiDichVu() {
    }

    public LoaiDichVu(String maLoaiDV) {
        this.maLoaiDV = maLoaiDV;
    }

    public LoaiDichVu(String maLoaiDV, String tenLoaiDV) {
        this.maLoaiDV = maLoaiDV;
        this.tenLoaiDV = tenLoaiDV;
    }

    public String getMaLoaiDV() {
        return maLoaiDV;
    }

    public void setMaLoaiDV(String maLoaiDV) {
        this.maLoaiDV = maLoaiDV;
    }

    public String getTenLoaiDV() {
        return tenLoaiDV;
    }

    public void setTenLoaiDV(String tenLoaiDV) {
        this.tenLoaiDV = tenLoaiDV;
    }

    @Override
    public String toString() {
        return "LoaiDichVu{" + "maLoaiDV=" + maLoaiDV + ", tenLoaiDV=" + tenLoaiDV + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.maLoaiDV);
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
        final LoaiDichVu other = (LoaiDichVu) obj;
        if (!Objects.equals(this.maLoaiDV, other.maLoaiDV)) {
            return false;
        }
        return true;
    }
}
