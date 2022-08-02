package dao;

import connectDB.KetNoiCSDL;
import entity.DatPhong;
import entity.Phong;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author vanphatdev
 */
public class DatPhongDAO {
    public ArrayList<DatPhong> getAllDatPhong() throws SQLException {
        ArrayList<DatPhong> dsDatPhong = new ArrayList<DatPhong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM DatPhong";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                LocalDateTime ngayDatPhong = rs.getTimestamp(1).toLocalDateTime();
                String maPhong = rs.getString(2);
                int maKH = rs.getInt(3);
                
                DatPhong datPhong = new DatPhong(ngayDatPhong, maPhong, maPhong);
                dsDatPhong.add(datPhong);
            }
            
        } catch (Exception e) {
            System.err.println("Kiểm tra getAllDatPhong - DatPhongDAO");
            e.printStackTrace();
        }
        
        return dsDatPhong;
    }
    
    public void themDatPhong(String ngayDatPhong, String maPhong, int maKH) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "INSERT INTO DatPhong VALUES (?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, ngayDatPhong);
            prepStmt.setString(2, maPhong);
            prepStmt.setInt(3, maKH);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("themDatPhong - DatPhongDAO");
            e.printStackTrace();
        }
    }
    
    public int nguoiDatPhong(String maPhong) throws SQLException {
        int maNguoiDatPhong = 0;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "SELECT maKH FROM DatPhong Where maPhong = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            
            prepStmt.setString(1, maPhong);
            
            ResultSet rs = prepStmt.executeQuery(); 
            
            rs.next();
            maNguoiDatPhong = rs.getInt(1);
            
        } catch (Exception e) {
            System.err.println("Kiểm tra nguoiDatPhong - DatPhongDAO");
            e.printStackTrace();
        }
        
        return maNguoiDatPhong;
    }
    
    public void xoaDatPhong(String maPhongDat) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "DELETE FROM DatPhong WHERE maPhong = ?;";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maPhongDat);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("xoaDatPhong - DatPhongDAO");
            e.printStackTrace();
        }
    }
}
