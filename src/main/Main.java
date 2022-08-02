package main;

import gui.GDChinh;
import gui.GDDangNhap;
import java.sql.SQLException;

/**
 *
 * @author vanphatdev
 */
public class Main {

    public static GDChinh gdChinh;
    public int a;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
//        System.out.println("Cay cú tạo nên khác biệt!");
        new GDDangNhap().setVisible(true);
    }
    
}
