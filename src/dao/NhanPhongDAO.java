package dao;

import connectDB.KetNoiCSDL;
import entity.NhanPhong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author vanphatdev
 */
public class NhanPhongDAO {
    public ArrayList<NhanPhong> getAllNhanPhong() throws SQLException {
        ArrayList<NhanPhong> dsNhanPhong = new ArrayList<NhanPhong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM NhanPhong";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                LocalDateTime ngayNhanPhong = rs.getTimestamp(1).toLocalDateTime();
                String maPhong = rs.getString(2);
                int maKH = rs.getInt(3);
                
                NhanPhong datPhong = new NhanPhong(ngayNhanPhong, maPhong, maPhong);
                dsNhanPhong.add(datPhong);
            }
            
        } catch (Exception e) {
            System.err.println("Kiểm tra getAllNhanPhong - NhanPhongDAO");
            e.printStackTrace();
        }
        
        return dsNhanPhong;
    }
    
    public void themNhanPhong(String ngayNhanPhong, String maPhong, int maKH) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "INSERT INTO NhanPhong VALUES (?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, ngayNhanPhong);
            prepStmt.setString(2, maPhong);
            prepStmt.setInt(3, maKH);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("themNhanPhong - NhanPhongDAO");
            e.printStackTrace();
        }
    }
    
    public int nguoiNhanPhong(String maPhong) throws SQLException {
        int maNguoiNhanPhong = 0;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "SELECT maKH FROM NhanPhong Where maPhong = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            
            prepStmt.setString(1, maPhong);
            
            ResultSet rs = prepStmt.executeQuery(); 
            
            rs.next();
            maNguoiNhanPhong = rs.getInt(1);
            
        } catch (Exception e) {
            System.err.println("Kiểm tra nguoiNhanPhong - DatPhongDAO");
            e.printStackTrace();
        }
        
        return maNguoiNhanPhong;
    }
    
    public void xoaNhanPhong(String maPhongThue) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "DELETE FROM NhanPhong WHERE maPhong = ?;";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maPhongThue);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("xoaNhanPhong - NhanPhongDAO");
            e.printStackTrace();
        }
    }
    
    public String getNgayNhan(String maPhong) throws SQLException {
        String ngayNhanPhong = "";
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "SELECT ngayNhanPhong FROM NhanPhong Where maPhong = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            
            prepStmt.setString(1, maPhong);
            
            ResultSet rs = prepStmt.executeQuery(); 
            
            rs.next();
            ngayNhanPhong = rs.getDate(1).toString();
            
        } catch (Exception e) {
            System.err.println("Kiểm tra nguoiNhanPhong - DatPhongDAO");
            e.printStackTrace();
        }
        
        return ngayNhanPhong;
    }
}
