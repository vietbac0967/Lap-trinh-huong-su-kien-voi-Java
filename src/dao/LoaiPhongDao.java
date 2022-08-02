package dao;

import java.sql.Connection;
import connectDB.KetNoiCSDL;
import entity.LoaiPhong;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author vanphatdev
 */
public class LoaiPhongDao {
    public ArrayList<LoaiPhong> getAllLoaiPhong() throws SQLException {
        ArrayList<LoaiPhong> dsLoaiPhong = new ArrayList<>();
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();

        try {
            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM LoaiPhong";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String maLoaiPhong = rs.getString(1);
                String tenLoaiPhong = rs.getString(2);

                LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong);
                dsLoaiPhong.add(loaiPhong);
            }
            
        } catch (Exception e) {
            System.err.println("getAllLoaiPhong failed - pls check in LoaiPhongDAO");
            e.printStackTrace();
        }
        
        return dsLoaiPhong;
    }
    
    public String getTenLoaiPhong(String maLoaiPhong) throws SQLException {
        KetNoiCSDL.getInstance();
        KetNoiCSDL knCSDL = new KetNoiCSDL();
        knCSDL.connect();

        Connection con = knCSDL.getConnection();
        
        try {
            String sql = "SELECT tenLoaiPhong FROM LoaiPhong WHERE maLoaiPhong = ?";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, maLoaiPhong);
            ResultSet rs = prepStmt.executeQuery(sql);
            return rs.getString(1);
            
        } catch (Exception e) {
            System.err.println("getTenLoaiPhong failed - pls check in LoaiPhongDAO");
            e.printStackTrace();
        }
        
        return "Lỗi getTenLoaiPhong";
    }
}
