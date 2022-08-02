package dao;

import connectDB.KetNoiCSDL;
import entity.HoaDon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 *
 * @author vanphatdev
 */
public class HoaDonDAO {
    public ArrayList<HoaDon> getAllHoaDon() throws SQLException {
        ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM HoaDon";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int maHD = rs.getInt(1);
                double tienPhong = rs.getInt(2);
                double tienDichVu = rs.getInt(3);
                double tongTien = rs.getInt(4);
                LocalDateTime ngayNhanPhong = rs.getTimestamp(5).toLocalDateTime();
                LocalDateTime ngayTraPhong = rs.getTimestamp(6).toLocalDateTime();
                String maNV = rs.getString(7);
                int maKH = rs.getInt(8);
                String maPhong = rs.getString(9);
                
                HoaDon hd = new HoaDon(maHD, tienPhong, tienDichVu, tongTien, ngayNhanPhong, ngayTraPhong, maNV, maKH, maPhong);
                dsHoaDon.add(hd);
            }
            
        } catch (Exception e) {
            System.err.println("getAllHoaDon failed - pls check in HoaDonDAO");
            e.printStackTrace();
        }
        
        return dsHoaDon;
    }
    
    public void themHoaDon(HoaDon hd) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            
            prepStmt.setDouble(1, hd.getTienPhong());
            prepStmt.setDouble(2, hd.getTienDichVu());
            prepStmt.setDouble(3, hd.getTongTien());
            prepStmt.setString(4, hd.getNgayNhanString());
            prepStmt.setString(5, hd.getNgayTraString());
            prepStmt.setString(6, hd.getMaNV());
            prepStmt.setInt(7, hd.getMaKH());
            prepStmt.setString(8, hd.getMaPhong());
            
            prepStmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("themHoaDon failed - pls check in HoaDonDAO");
            e.printStackTrace();
        }
    }
}
