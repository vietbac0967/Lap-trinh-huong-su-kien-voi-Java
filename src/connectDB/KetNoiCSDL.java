package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vanphatdev
 */
public class KetNoiCSDL {
    public static Connection con = null;
    public static KetNoiCSDL instance = new KetNoiCSDL();
    
    /**
     * Tạo hàm static getInstance() để các class khác có thể
     * sử dụng hàm trong class này cho mục đích kết nối cơ sở dữ liệu
     * @return instance
     */
    public static KetNoiCSDL getInstance() {
        return instance;
    }
    
    public void connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename=DPKS";
        String user = "bac";
        String password = "123";
        con = DriverManager.getConnection(url, user, password);
//        System.out.println("Kết nối thành công với cơ sở dữ liệu");
    }
    
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Bắt lỗi ở function disconnect() -> dòng 37, file connectDB/KetNoiCSDL");
                e.printStackTrace();
            }
        }
    }
    
    public static Connection getConnection() {
        System.out.println(con);
        return con;
    }
}
