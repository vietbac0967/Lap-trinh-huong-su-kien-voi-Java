package dao;

import connectDB.KetNoiCSDL;
import entity.DatDichVu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 *
 * @author vanphatdev
 */
public class DatDichVuDAO {
    public ArrayList<DatDichVu> getByMaKH(int maKhach) throws SQLException {
        ArrayList<DatDichVu> dsDichVuTheoKH = new ArrayList<DatDichVu>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            String sql = "SELECT * FROM DatDichVu WHERE maKH = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maKhach);
            
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                int maDatDV = rs.getInt(1);
                int maKH = rs.getInt(2);
                int maDV = rs.getInt(3);
                int soLuongDV = rs.getInt(4);

                DatDichVu ddv = new DatDichVu(maDatDV, maKH, maDV, soLuongDV);
                dsDichVuTheoKH.add(ddv);
            }
            
        } catch (Exception e) {
            System.err.println("getByMaKH failed - pls check in DatDichVuDAO");
            e.printStackTrace();
        }
        
        return dsDichVuTheoKH;
    }
    
    public void themDatDichVu(DatDichVu datDV) throws SQLException {
        int maKhachHang = datDV.getMaKH();
        int maDichVu = datDV.getMaDV();
        ArrayList<DatDichVu> dsDatTheoMaKH = getByMaKHvaMaDV(maKhachHang, maDichVu);
        
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        if (dsDatTheoMaKH.size() == 0) {
            try {
                String sql = "INSERT INTO DatDichVu VALUES (?, ?, ?)";
                PreparedStatement prepStmt = con.prepareStatement(sql);
                prepStmt.setInt(1, datDV.getMaKH());
                prepStmt.setInt(2, datDV.getMaDV());
                prepStmt.setInt(3, datDV.getSoLuongDV());
                prepStmt.executeUpdate();
            } catch (Exception e) {
                System.err.println("themDatDichVu - DatDichVuDAO");
                e.printStackTrace();
            }
        }
        else {
            int maDatDichVu = dsDatTheoMaKH.get(0).getMaDatDV();
            String sql = "UPDATE DatDichVu SET soLuongDV = ? WHERE maDatDV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            
            int soLuongHienTai = dsDatTheoMaKH.get(0).getSoLuongDV();
            int soLuongMoi = soLuongHienTai + datDV.getSoLuongDV();
            
            prepStmt.setInt(1, soLuongMoi);
            prepStmt.setInt(2, maDatDichVu);
            prepStmt.executeUpdate();
        }
    }
    
    public ArrayList<DatDichVu> getByMaKHvaMaDV(int maKhach, int maDichVu) throws SQLException {
        ArrayList<DatDichVu> dsDichVuTheoKH = new ArrayList<DatDichVu>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            String sql = "SELECT * FROM DatDichVu WHERE maKH = ? AND maDV = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setInt(1, maKhach);
            prepStmt.setInt(2, maDichVu);
            
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                int maDatDV = rs.getInt(1);
                int maKH = rs.getInt(2);
                int maDV = rs.getInt(3);
                int soLuongDV = rs.getInt(4);

                DatDichVu ddv = new DatDichVu(maDatDV, maKH, maDV, soLuongDV);
                dsDichVuTheoKH.add(ddv);
            }
            
        } catch (Exception e) {
            System.err.println("getByMaKHvaMaDV failed - pls check in DatDichVuDAO");
            e.printStackTrace();
        }
        
        return dsDichVuTheoKH;
    }
    
    
    public void xoaDatDichVu(String maKhach) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();
        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "DELETE FROM DatDichVu WHERE maKH = ?;";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maKhach);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("xoaDatDichVu - DatDichVuDAO");
            e.printStackTrace();
        }
    }
}
