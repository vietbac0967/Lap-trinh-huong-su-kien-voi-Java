package dao;

import connectDB.KetNoiCSDL;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 *
 * @author vanphatdev
 */
public class NhanVienDAO {
    public ArrayList<NhanVien> getAllNhanVien() throws SQLException {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM NhanVien";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String maNV = rs.getString(1);
                String hoTen = rs.getString(2);
                String soDT = rs.getString(3);
                String soCMND = rs.getString(4);
                String diaChi = rs.getString(5);
                boolean gioiTinh = rs.getBoolean(6);
                boolean isQuanLi = rs.getBoolean(7);
                
                NhanVien nv = new NhanVien(maNV, hoTen, soDT, soCMND, diaChi, gioiTinh, isQuanLi);
                dsNhanVien.add(nv);
            }
            
        } catch (Exception e) {
            System.err.println("getAllNhanVien - NhanVienDAO");
            e.printStackTrace();
        }
        
        return dsNhanVien;
    }
    
    public String getTenNhanVienByMaNV(String maNV) throws SQLException {
        String tenNhanVien = "";
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "Select hoTen from NhanVien WHERE maNV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maNV);
            
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            tenNhanVien = rs.getString(1);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNhanVien;
    }
    
    public boolean getChucVuNhanVienByMaNV(String maNV) throws SQLException {
        boolean chucVuNhanVien = false;
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "Select isQuanLi from NhanVien WHERE maNV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maNV);
            
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            chucVuNhanVien = rs.getBoolean(1);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chucVuNhanVien;
    }
}
