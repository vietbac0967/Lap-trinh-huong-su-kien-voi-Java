package list_entity;

import entity.Phong;
import java.util.ArrayList;

/**
 *
 * @author vanphatdev
 */
public class ListPhong {
    ArrayList<Phong> dsPhong;
    
    public ListPhong() {
        dsPhong = new ArrayList<>();
    }

    public void setDsPhong(ArrayList<Phong> dsPhong) {
        this.dsPhong = dsPhong;
    }
    
    public void themPhong(Phong ph) {
        dsPhong.add(ph);
    }
}
