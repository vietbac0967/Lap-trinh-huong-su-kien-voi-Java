package dao;

import connectDB.KetNoiCSDL;
import entity.DatDichVu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vanphatdev
 */
public class DichVuDAO {
    public double getDonGiaByMaDV(int maDichVu) throws SQLException {
        double giaCa = 0;
        
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            String sql = "SELECT donGia FROM DichVu WHERE maDV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maDichVu);
            
            ResultSet rs = prepStmt.executeQuery(sql);
            
            rs.next();
            giaCa = rs.getDouble(1);
            
        } catch (Exception e) {
            System.err.println("getDonGiaByMaDV - pls check in DichVuDAO");
            e.printStackTrace();
        }
        
        return giaCa;
    }
    
    public String getTenDVByMaDV(int maDichVu) throws SQLException {
        String tenDichVu = "";
        
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            String sql = "SELECT tenDV FROM DichVu WHERE maDV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maDichVu);
            
            ResultSet rs = prepStmt.executeQuery(sql);
            
            rs.next();
            tenDichVu = rs.getString(1);
            
        } catch (Exception e) {
            System.err.println("getTenDVByMaDV failed - pls check in DatDichVuDAO");
            e.printStackTrace();
        }
        
        return tenDichVu;
    }
}
