package dao;

import connectDB.KetNoiCSDL;
import entity.DatPhong;
import entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class KhachHangDAO {
    public ArrayList<KhachHang> getAllKhachHang() throws SQLException {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM KhachHang";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int maKH = rs.getInt(1);
                String hoTen = rs.getString(2);
                String soDT = rs.getString(3);
                String soCMND = rs.getString(4);
                String diaChi = rs.getString(5);
                boolean gioiTinh = rs.getBoolean(6);
                
                KhachHang kh = new KhachHang(maKH, hoTen, soDT, soCMND, diaChi, gioiTinh);
                dsKhachHang.add(kh);
            }
            
        } catch (Exception e) {
            System.err.println("Kiểm tra getAllKhachHang - KhachHangDAO");
            e.printStackTrace();
        }
        
        return dsKhachHang;
    }
    
    public void themKhachHang(KhachHang kh) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "INSERT INTO KhachHang VALUES (?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, kh.getHoTen());
            prepStmt.setString(2, kh.getSoDT());
            prepStmt.setString(3, kh.getSoCMND());
            prepStmt.setString(4, kh.getDiaChi());
            prepStmt.setBoolean(5, kh.isGioiTinh());
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Kiểm tra themKhachHang - KhachHangDAO");
            e.printStackTrace();
        }
    }
    
    public int getLastMaKH() throws SQLException {
        int maCuoi = 0;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        String sql = "SELECT max(maKH) as LastKH FROM KhachHang";
                
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            maCuoi = rs.getInt(1);
            
        } catch (Exception e) {
            System.err.println("Kiểm tra getLastMaKH - KhachHangDAO");
            e.printStackTrace();
        }
        
        return maCuoi;
    }
    
    public void xoaKhachHang(int maKhach) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "DELETE FROM KhachHang WHERE maKH = ?;";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maKhach);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("xoaKhachHang - KhachHangDAO");
            e.printStackTrace();
        }
    }
    
    public String getHoTenByMaKH(int maKhach) throws SQLException {
        String tenKhach= "";
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "SELECT hoTen FROM KhachHang WHERE maKH = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maKhach);
            
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            tenKhach = rs.getString(1);
            
        } catch (Exception e) {
            System.err.println("getHoTenByMaKH - KhachHangDAO");
            e.printStackTrace();
        }
        
        return tenKhach;
    }
}
