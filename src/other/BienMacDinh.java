package other;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author vanphatdev
 */
public interface BienMacDinh {
    /**
     * Biến mặc định màu sắc cho chương trình
     */
    Color mauTrang = new Color(255, 255, 255);
    Color mauDen = new Color(0, 0, 0);
    Color mauDanhMucDangChon = new Color(30, 122, 141);
    Color mauNenMenu = new Color(194, 228, 255);
    Color mauBorderBottomMenu = new Color(255, 153, 102);
    Color mauNenDangNhap = new Color(4, 135, 217);
    Color mauTieuDeTable = new Color(57, 86, 151);
    Color mauSelectTable = new Color(255, 138, 48);
    Color mauGridTable = new Color(204, 204, 204);
    
    Color mauPhongTrong = new Color(67, 160, 71);
    Color mauPhongTrongNhat = new Color(233, 247, 234);
    
    Color mauPhongDatTruoc = new Color(4, 135, 217);
    Color mauPhongDatTruocNhat = new Color(187, 222, 252);
    
    Color mauPhongCoNguoi = new Color(238, 83, 80);
    Color mauPhongCoNguoiNhat = new Color(255, 235, 236);
    
    Color mauPhongDangSua = new Color(96, 125, 139);
    Color mauPhongDangSuaNhat = new Color(207, 216, 221);
    
    /**
     * Biến mặc định font cho chương trình
     */
    Font fontButtonDangNhap = new Font("Arial", Font.PLAIN, 14);
    Font fontTieuDeTable = new Font("Arial", Font.PLAIN, 16);
    
    Font fontTieuDeTableCan1 = new Font("Arial", Font.PLAIN, 18);
    Font fontTieuDeTableCan2 = new Font("Arial", Font.PLAIN, 20);
    Font fontTieuDeTableCan3 = new Font("Arial", Font.PLAIN, 22);
    Font fontTieuDeTableCan4 = new Font("Arial", Font.PLAIN, 24);
    
    Font fontTable = new Font("Arial", Font.PLAIN, 15);
    
    Font fontTableCan1 = new Font("Arial", Font.PLAIN, 17);
    Font fontTableCan2 = new Font("Arial", Font.PLAIN, 19);
    Font fontTableCan3 = new Font("Arial", Font.PLAIN, 21);
    Font fontTableCan4 = new Font("Arial", Font.PLAIN, 23);
}
