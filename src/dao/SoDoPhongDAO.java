package dao;

import java.sql.Connection;
import connectDB.KetNoiCSDL;
import entity.LoaiPhong;
import entity.Phong;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author vanphatdev
 */
public class SoDoPhongDAO {
    public ArrayList<Phong> getAllSoDoPhong() throws SQLException {
        ArrayList<Phong> soDoPhong = new ArrayList<Phong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Phong";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String maPhong = rs.getString(1);
                double giaPhong = rs.getDouble(2);
                double dienTich = rs.getDouble(3);
                int soGiuong = rs.getInt(4);
                String trangThaiPhong = rs.getString(5);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(6));
                Phong phong = new Phong(maPhong, giaPhong, dienTich, soGiuong, trangThaiPhong, loaiPhong);
                soDoPhong.add(phong);
            }
            
        } catch (Exception e) {
            System.err.println("getAllSoDoPhong failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return soDoPhong;
    }
    
    public ArrayList<Phong> getSoDoPhongLoai(String maLoaiPhong) throws SQLException {
        ArrayList<Phong> soDoPhong = new ArrayList<Phong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "SELECT * FROM Phong WHERE maLoaiPhong = ?";
        
        try {
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maLoaiPhong);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                String maPhong = rs.getString(1);
                double giaPhong = rs.getDouble(2);
                double dienTich = rs.getDouble(3);
                int soGiuong = rs.getInt(4);
                String trangThaiPhong = rs.getString(5);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(6));
                Phong phong = new Phong(maPhong, giaPhong, dienTich, soGiuong, trangThaiPhong, loaiPhong);
                soDoPhong.add(phong);
            }
            
        } catch (Exception e) {
            System.err.println("getSoDoPhongFilter failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return soDoPhong;
    }
    
    public ArrayList<Phong> getSoDoPhongTrangThai(String ttPhong) throws SQLException {
        ArrayList<Phong> soDoPhong = new ArrayList<Phong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "SELECT * FROM Phong WHERE trangThaiPhong = ?";
        
        try {
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, ttPhong);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                String maPhong = rs.getString(1);
                double giaPhong = rs.getDouble(2);
                double dienTich = rs.getDouble(3);
                int soGiuong = rs.getInt(4);
                String trangThaiPhong = rs.getString(5);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(6));
                Phong phong = new Phong(maPhong, giaPhong, dienTich, soGiuong, trangThaiPhong, loaiPhong);
                soDoPhong.add(phong);
            }
            
        } catch (Exception e) {
            System.err.println("getSoDoPhongTrangThai failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return soDoPhong;
    }
    
    public ArrayList<Phong> getSoDoPhongFilter(String maLoaiPhong, String ttPhong) throws SQLException {
        ArrayList<Phong> soDoPhong = new ArrayList<Phong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "SELECT * FROM Phong WHERE maLoaiPhong = ? AND trangThaiPhong = ?";
        
        try {
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maLoaiPhong);
            prepStmt.setString(2, ttPhong);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                String maPhong = rs.getString(1);
                double giaPhong = rs.getDouble(2);
                double dienTich = rs.getDouble(3);
                int soGiuong = rs.getInt(4);
                String trangThaiPhong = rs.getString(5);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(6));
                Phong phong = new Phong(maPhong, giaPhong, dienTich, soGiuong, trangThaiPhong, loaiPhong);
                soDoPhong.add(phong);
            }
            
        } catch (Exception e) {
            System.err.println("getSoDoPhongFilter failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return soDoPhong;
    }
    
    public void updateTrangThaiPhong(String ttPhong, String maPhong) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "Update Phong SET trangThaiPhong = ? WHERE maPhong = ?";
        
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, ttPhong);
            statement.setString(2, maPhong);
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("updateTrangThaiPhong failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
    }
    
    public ArrayList<Phong> getSoDoPhongTimKiem(String maPhongTimKiem) throws SQLException {
        ArrayList<Phong> soDoPhong = new ArrayList<Phong>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "SELECT * FROM Phong WHERE maPhong LIKE ?";
        
        try {
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maPhongTimKiem);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                String maPhong = rs.getString(1);
                double giaPhong = rs.getDouble(2);
                double dienTich = rs.getDouble(3);
                int soGiuong = rs.getInt(4);
                String trangThaiPhong = rs.getString(5);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(6));
                Phong phong = new Phong(maPhong, giaPhong, dienTich, soGiuong, trangThaiPhong, loaiPhong);
                soDoPhong.add(phong);
            }
            
        } catch (Exception e) {
            System.err.println("getSoDoPhongTimKiem failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return soDoPhong;
    }
    
    public int getCountPhongTheoTT(String ttPhong) throws SQLException {
        int count = 0;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        PreparedStatement prepStmt = null;
        String sql = "SELECT COUNT(*) AS Total FROM Phong WHERE trangThaiPhong = ?";
        
        try {
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, ttPhong);
            ResultSet rs = prepStmt.executeQuery();
            
            rs.next();
            count = rs.getInt("Total");
            
        } catch (Exception e) {
            System.err.println("getCountPhongTheoTT failed - pls check in SoDoPhongDAO");
            e.printStackTrace();
        }
        
        return count;
    }
    
    public double getTienGiaPhong(String maPhong) throws SQLException {
        double giaPhong = 0;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            
            String sql = "SELECT giaPhong FROM Phong WHERE maPhong = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maPhong);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            giaPhong = rs.getDouble(1);
            
        } catch (Exception e) {
            
        }
        
        return giaPhong;
    }
}
