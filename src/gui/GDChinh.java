package gui;

import component.CustomConfirmDialog;
import component.CustomMessageDialog;
import component.PanelItemPhong;
import custom.MyScrollBar;
import dao.DatDichVuDAO;
import dao.DatPhongDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.LoaiPhongDao;
import dao.NhanPhongDAO;
import dao.NhanVienDAO;
import dao.SoDoPhongDAO;
import entity.DatDichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import main.Main;
import other.BienMacDinh;

/**
 *
 * @author vanphatdev
 */
public class GDChinh extends javax.swing.JFrame implements MouseListener{

    private boolean isThuGonMenu = false;
    private int manHinhDangChon = 1;
    public String maNhanVien;
    public HoaDon hd;
    
    //{Sơ đồ phòng, Đặt phòng, Trả phòng, Dịch vụ, Nhân viên, Khách hàng, Hoá đơn}
    private LinkedList<JPanel> lstPanel;
    private LinkedList<JPanel> lstPanelMenu;
    private LinkedList<JLabel> lstLabel;
    private LinkedList<JButton> lstButton;
    private ArrayList<String> lstMaLoaiPhong;
    private ArrayList<String> lstTTPhong;
    private ArrayList<Phong> soDoPhong;
    private ArrayList<LoaiPhong> dsLoaiPhong;
    private ArrayList<Integer> lstDemLoaiPhong;
    private DefaultTableModel modelKhachHang;
    private ArrayList<KhachHang> dsKhachHang;
    private DefaultTableModel modelNhanVien;
    private ArrayList<NhanVien> dsNhanVien;
    private DefaultTableModel modelHoaDon;
    private ArrayList<HoaDon> dsHoaDon;
    
    public GDChinh() throws SQLException {
        icon();
        this.setTitle("Quản lí khách sạn - VinaHotel");
        initComponents();
        
        customJPanelVaJLableKhiDuocChon(pSoDoPhong, lblSoDoPhong);
        
        // Tạo list panel nội dung
        lstPanel = new LinkedList<>();
        lstPanel.add(pNDSoDoPhong);
        lstPanel.add(pNDQLDatPhong);
        lstPanel.add(pNDQLTraPhong);
        lstPanel.add(pNDQLDichVu);
        lstPanel.add(pNDQLNhanVien);
        lstPanel.add(pNDQLKhachHang);
        lstPanel.add(pNDQLHoaDon);
        
        // Tạo list label menu
        lstLabel = new LinkedList<>();
        lstLabel.add(lblSoDoPhong);
        lstLabel.add(lblQLDatPhong);
        lstLabel.add(lblQLTraPhong);
        lstLabel.add(lblQLDichVu);
        lstLabel.add(lblQLNhanVien);
        lstLabel.add(lblQLKhachHang);
        lstLabel.add(lblQLHoaDon);
        
        // Tạo list panel menu
        lstPanelMenu = new LinkedList<>();
        lstPanelMenu.add(pSoDoPhong);
        lstPanelMenu.add(pQLDatPhong);
        lstPanelMenu.add(pQLTraPhong);
        lstPanelMenu.add(pQLDichVu);
        lstPanelMenu.add(pQLNhanVien);
        lstPanelMenu.add(pQLKhachHang);
        lstPanelMenu.add(pQLHoaDon);
        
        // Tạo list mã loại phòng
        lstMaLoaiPhong = new ArrayList<>();
        lstMaLoaiPhong.add("all");
        lstMaLoaiPhong.add("standard");
        lstMaLoaiPhong.add("superior");
        lstMaLoaiPhong.add("deluxe");
        lstMaLoaiPhong.add("suite");
        
        // Tạo list tình trạng phòng
        lstTTPhong = new ArrayList<>();
        lstTTPhong.add("all");
        lstTTPhong.add("pt");
        lstTTPhong.add("pcn");
        lstTTPhong.add("pdt");
        lstTTPhong.add("pds");
        
        // Chọn panel sơ đồ phòng khi vừa khởi tạo
        setVisibleLstPanel(lstPanel, new int[]{1,0,0,0,0,0,0 });
        
        // Đăng kí lắng nghe
        lblSoDoPhong.addMouseListener(this);
        lblQLDatPhong.addMouseListener(this);
        lblQLTraPhong.addMouseListener(this);
        lblQLDichVu.addMouseListener(this);
        lblQLNhanVien.addMouseListener(this);
        lblQLKhachHang.addMouseListener(this);
        lblQLHoaDon.addMouseListener(this);
        
        // Custom JScrollpane
        jScrollPane1.setVerticalScrollBar(new MyScrollBar());
        scrKhachHang.setVerticalScrollBar(new MyScrollBar());
        
        // Chỉnh tốc độ lăn thanh cuộn
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        scrKhachHang.getVerticalScrollBar().setUnitIncrement(16);
        
        // Button group cho radio button
        btnGroupLoaiPhong.add(rbtnLoaiPhong1);
        btnGroupLoaiPhong.add(rbtnLoaiPhong2);
        btnGroupLoaiPhong.add(rbtnLoaiPhong3);
        btnGroupLoaiPhong.add(rbtnLoaiPhong4);
        btnGroupLoaiPhong.add(rbtnLoaiPhong5);
        rbtnLoaiPhong1.setSelected(true);
        
        btnGroupTrangThai.add(rbtnTrangThaiPhong1);
        btnGroupTrangThai.add(rbtnTrangThaiPhong2);
        btnGroupTrangThai.add(rbtnTrangThaiPhong3);
        btnGroupTrangThai.add(rbtnTrangThaiPhong4);
        btnGroupTrangThai.add(rbtnTrangThaiPhong5);
        rbtnTrangThaiPhong1.setSelected(true);
        
        // Button group cho giới tính khách hàng
        btnGroupGioiTinhDatPhong.add(rbtnKHNam);
        btnGroupGioiTinhDatPhong.add(rbtnKHNu);
        rbtnKHNam.setSelected(true);
        
        // Button group cho Muc Do Can
        btnGroupCan.add(rbtnCan1);
        btnGroupCan.add(rbtnCan2);
        btnGroupCan.add(rbtnCan3);
        btnGroupCan.add(rbtnCan4);
        
        // Button group giới tính khách hàng
        btnGroupGioiTinhKH.add(rbtnGTKHTatCa);
        btnGroupGioiTinhKH.add(rbtnGTKHNam);
        btnGroupGioiTinhKH.add(rbtnGTKHNu);
        rbtnGTKHTatCa.setSelected(true);
        
        // Button group gioiTinh Nhan vien
        btnGroupGioiTinhNhanVien.add(rbtnGTNhanVienTC);
        btnGroupGioiTinhNhanVien.add(rbtnGTNhanVienNam);
        btnGroupGioiTinhNhanVien.add(rbtnGTNhanVienNu);
        rbtnGTNhanVienTC.setSelected(true);
        
        btnGroupChucVuNhanVien.add(rbtnCVNhanVienTC);
        btnGroupChucVuNhanVien.add(rbtnCVNhanVienLeTan);
        btnGroupChucVuNhanVien.add(rbtnCVNhanVienQL);
        rbtnCVNhanVienTC.setSelected(true);
        
        // Chỉnh tfTimKiemPhong
        tfTimKiemPhong.setHint("Mã phòng");
        tfTimKiemPhong.setPrefixIcon(new ImageIcon(getClass().getResource("/img/search_16px.png")));
        
        // Tom tat phong
        lstDemLoaiPhong = new ArrayList<>(4);
        demTTPhong();
        loadDuLieuTomTatSoDoPhong();
        
        // So do phong
        soDoPhong = new SoDoPhongDAO().getAllSoDoPhong();

        // Loai Phong
        dsLoaiPhong = new LoaiPhongDao().getAllLoaiPhong();

        loadSoDoPhong(soDoPhong);
        
        // Table Khach Hang
        String[] headerKH = {"Mã", "Họ tên", "Số điện thoại", "Số CMND", "Giới tính"};
        modelKhachHang = new DefaultTableModel(headerKH, 0);
        tableKH.setModel(modelKhachHang);
        
        dsKhachHang = new KhachHangDAO().getAllKhachHang();
        loadDSKhachHangLenUI(dsKhachHang);
        CustomTableBeautiful(tableKH, 5);
        
        // Table Nhan Vien
        String[] headerNV = {"Mã", "Họ tên", "Số điện thoại", "Số CMND", "Giới tính", "Chức vụ"};
        modelNhanVien = new DefaultTableModel(headerNV, 0);
        tableNV.setModel(modelNhanVien);
        
        dsNhanVien = new NhanVienDAO().getAllNhanVien();
        loadDSNhanVienLenUI(dsNhanVien);
        CustomTableBeautiful(tableNV, 6);
        
        // Table Hoa don 
        String[] headerHD = {"Mã hoá đơn", "Tổng tiền", "Ngày nhận", "Ngày trả", "Mã nhân viên", "Mã khách hàng", "Mã phòng"};
        modelHoaDon = new DefaultTableModel(headerHD, 0);
        tableHoaDon.setModel(modelHoaDon);
        
        dsHoaDon = new HoaDonDAO().getAllHoaDon();
        loadDSHoaDonLenUI(dsHoaDon);
        CustomTableBeautiful(tableHoaDon, 7);
    }
    
    public void icon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Logo_big.jpg")));
    }

    private void setVisibleLstPanel(LinkedList<JPanel> lstPanel, int[] isOn)
    {
       int n = lstPanel.size();
       int i = 0;
       for (i=0; i<n; i++) {
           if (isOn[i] == 1) {
               lstPanel.get(i).setVisible(true);
           } else {
               lstPanel.get(i).setVisible(false);
           }
       }
    }
    
    public void thuGonMenu() {
        if (isThuGonMenu == false) {
            pMenu.setPreferredSize(new Dimension(76,740));
            pMenu.revalidate();
            pMenu.repaint();
            isThuGonMenu = true;
        } else {
            pMenu.setPreferredSize(new Dimension(230,740));
            pMenu.revalidate();
            pMenu.repaint();
            isThuGonMenu = false;
        }
    }
    
    public void loadSoDoPhong(ArrayList<Phong> dsPhong) throws SQLException {
        System.out.println("Xin chao loadSoDoPhong");
        panelMoHinhPhong1.removeAll();
        panelMoHinhPhong1.revalidate();
        panelMoHinhPhong1.repaint();
        String maPhong;
        String maLoaiPhong;
        String loaiPhong;
        String trangThaiPhong;
        Phong phong;
        for (int i=0; i<dsPhong.size(); i++) {
            phong = dsPhong.get(i);
            maPhong = phong.getMaPhong();
            maLoaiPhong = phong.getLoaiPhong().getMaLoaiPhong();
            loaiPhong = getTenLoaiPhong(maLoaiPhong, dsLoaiPhong);
            
            trangThaiPhong = phong.getTrangThaiPhong();
            PanelItemPhong pItem = new PanelItemPhong(maPhong, loaiPhong, trangThaiPhong);
            panelMoHinhPhong1.add(pItem);
        }
    }
    
    public String getTenLoaiPhong(String maLoaiPhong, ArrayList<LoaiPhong> dsLoaiPhong) {
        for(int i=0; i<dsLoaiPhong.size(); i++) {
            if (maLoaiPhong.equals(dsLoaiPhong.get(i).getMaLoaiPhong())) {
                return dsLoaiPhong.get(i).getTenLoaiPhong();
            }
        }
        return "rỗng || không có mã này";
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupLoaiPhong = new javax.swing.ButtonGroup();
        btnGroupTrangThai = new javax.swing.ButtonGroup();
        btnGroupGioiTinhDatPhong = new javax.swing.ButtonGroup();
        btnGroupCan = new javax.swing.ButtonGroup();
        btnGroupGioiTinhKH = new javax.swing.ButtonGroup();
        btnGroupGioiTinhNhanVien = new javax.swing.ButtonGroup();
        btnGroupChucVuNhanVien = new javax.swing.ButtonGroup();
        pBackgroundMain = new javax.swing.JPanel();
        pMenu = new javax.swing.JPanel();
        pLogo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pSoDoPhong = new javax.swing.JPanel();
        lblSoDoPhong = new javax.swing.JLabel();
        pQLDatPhong = new javax.swing.JPanel();
        lblQLDatPhong = new javax.swing.JLabel();
        pQLTraPhong = new javax.swing.JPanel();
        lblQLTraPhong = new javax.swing.JLabel();
        pQLDichVu = new javax.swing.JPanel();
        lblQLDichVu = new javax.swing.JLabel();
        pQLNhanVien = new javax.swing.JPanel();
        lblQLNhanVien = new javax.swing.JLabel();
        pQLKhachHang = new javax.swing.JPanel();
        lblQLKhachHang = new javax.swing.JLabel();
        pQLHoaDon = new javax.swing.JPanel();
        lblQLHoaDon = new javax.swing.JLabel();
        pDangXuat = new javax.swing.JPanel();
        lblDangXuat = new javax.swing.JLabel();
        pNoiDung = new javax.swing.JPanel();
        pNDSoDoPhong = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnMenuSoDoPhong = new custom.Button();
        button2 = new custom.Button();
        lblTenNVSoDoPhong = new javax.swing.JLabel();
        lblChucVuNhanVien1 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        rbtnLoaiPhong1 = new custom.RadioButtonCustom();
        rbtnLoaiPhong2 = new custom.RadioButtonCustom();
        rbtnLoaiPhong3 = new custom.RadioButtonCustom();
        rbtnLoaiPhong4 = new custom.RadioButtonCustom();
        rbtnLoaiPhong5 = new custom.RadioButtonCustom();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        rbtnTrangThaiPhong1 = new custom.RadioButtonCustom();
        rbtnTrangThaiPhong2 = new custom.RadioButtonCustom();
        rbtnTrangThaiPhong3 = new custom.RadioButtonCustom();
        rbtnTrangThaiPhong4 = new custom.RadioButtonCustom();
        rbtnTrangThaiPhong5 = new custom.RadioButtonCustom();
        jLabel36 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        lblChuThichPT = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        lblChuThichPDT = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        lblChuThichPCN = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        lblChuThichPDS = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        buttonCustom1 = new custom.ButtonCustom();
        tfTimKiemPhong = new custom.MyTextField();
        buttonCustom2 = new custom.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelMoHinhPhong1 = new component.PanelMoHinhPhong();
        pNDQLDatPhong = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        btnMenuQLDatPhong = new custom.Button();
        button3 = new custom.Button();
        lblTenNVQLDP = new javax.swing.JLabel();
        lblChucVuNhanVien2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        tfHoTenKH = new javax.swing.JTextField();
        tfSDTKH = new javax.swing.JTextField();
        tfSoCMNDKH = new javax.swing.JTextField();
        tfDiaChiKH = new javax.swing.JTextField();
        rbtnKHNam = new custom.RadioButtonCustom();
        rbtnKHNu = new custom.RadioButtonCustom();
        jPanel27 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        btnNDXoaTrang = new custom.ButtonCustom();
        btnNDDatPhong = new custom.ButtonCustom();
        btnNDDatNgay = new custom.ButtonCustom();
        tfMaPhongDat = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        tfLoaiPhongDat = new javax.swing.JTextField();
        pNDQLTraPhong = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        btnMenuQLTraPhong = new custom.Button();
        button4 = new custom.Button();
        lblTenNVQLTP = new javax.swing.JLabel();
        lblChucVuNhanVien3 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        tfTraPhongMaKH = new javax.swing.JTextField();
        tfTraPhongHoTen = new javax.swing.JTextField();
        tfTraPhongMaPhong = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        tfTraPhongNgayNhanPhong = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        tfTraPhongBuffetAn = new javax.swing.JTextField();
        tfTraPhongBuffetNuoc = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnThanhToanTraPhong = new custom.ButtonCustom();
        jLabel58 = new javax.swing.JLabel();
        tfTraPhongTongTien = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        tfTraPhongNVLapHD = new javax.swing.JTextField();
        pNDQLDichVu = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnMenuQLDichVu = new custom.Button();
        button5 = new custom.Button();
        lblTenNVQLDV = new javax.swing.JLabel();
        lblChucVuNhanVien4 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        tfDDVMaKH = new javax.swing.JTextField();
        tfDDVHoTen = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        tfSLBuffetAn = new javax.swing.JTextField();
        tfSLBuffetNuoc = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        tfChotDonDV = new custom.ButtonCustom();
        jLabel48 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        tfDDVMaPhong = new javax.swing.JTextField();
        pNDQLNhanVien = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        btnMenuQLNhanVien = new custom.Button();
        button11 = new custom.Button();
        lblTenNVQLNhanVien = new javax.swing.JLabel();
        lblChucVuNhanVien5 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        rbtnGTNhanVienTC = new custom.RadioButtonCustom();
        rbtnGTNhanVienNam = new custom.RadioButtonCustom();
        rbtnGTNhanVienNu = new custom.RadioButtonCustom();
        jPanel25 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        rbtnCVNhanVienTC = new custom.RadioButtonCustom();
        rbtnCVNhanVienLeTan = new custom.RadioButtonCustom();
        rbtnCVNhanVienQL = new custom.RadioButtonCustom();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        pNDQLKhachHang = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        btnMenuQLKhachHang = new custom.Button();
        button15 = new custom.Button();
        lblTenNVQLKhachHang = new javax.swing.JLabel();
        lblChucVuNhanVien6 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        rbtnCan1 = new custom.RadioButtonCustom();
        rbtnCan2 = new custom.RadioButtonCustom();
        rbtnCan3 = new custom.RadioButtonCustom();
        rbtnCan4 = new custom.RadioButtonCustom();
        jPanel33 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        rbtnGTKHTatCa = new custom.RadioButtonCustom();
        rbtnGTKHNam = new custom.RadioButtonCustom();
        rbtnGTKHNu = new custom.RadioButtonCustom();
        jPanel30 = new javax.swing.JPanel();
        scrKhachHang = new javax.swing.JScrollPane();
        tableKH = new javax.swing.JTable();
        pNDQLHoaDon = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        btnMenuQLHoaDon = new custom.Button();
        button16 = new custom.Button();
        lblTenNhanVienQLHoaDon = new javax.swing.JLabel();
        lblChucVuNhanVien7 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        radioButtonCustom7 = new custom.RadioButtonCustom();
        radioButtonCustom3 = new custom.RadioButtonCustom();
        jPanel42 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        radioButtonCustom8 = new custom.RadioButtonCustom();
        radioButtonCustom1 = new custom.RadioButtonCustom();
        radioButtonCustom2 = new custom.RadioButtonCustom();
        jPanel38 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pMenu.setBackground(new java.awt.Color(194, 228, 255));
        pMenu.setPreferredSize(new java.awt.Dimension(230, 588));

        pLogo.setBackground(new java.awt.Color(194, 228, 255));

        lblLogo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(204, 0, 51));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logo_small.jpg"))); // NOI18N
        lblLogo.setText("VinaHotel");
        lblLogo.setIconTextGap(16);

        javax.swing.GroupLayout pLogoLayout = new javax.swing.GroupLayout(pLogo);
        pLogo.setLayout(pLogoLayout);
        pLogoLayout.setHorizontalGroup(
            pLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pLogoLayout.setVerticalGroup(
            pLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pSoDoPhong.setBackground(new java.awt.Color(194, 228, 255));

        lblSoDoPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblSoDoPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/houses_32px.png"))); // NOI18N
        lblSoDoPhong.setText("Sơ đồ phòng");
        lblSoDoPhong.setToolTipText("Sơ đồ phòng");
        lblSoDoPhong.setIconTextGap(38);

        javax.swing.GroupLayout pSoDoPhongLayout = new javax.swing.GroupLayout(pSoDoPhong);
        pSoDoPhong.setLayout(pSoDoPhongLayout);
        pSoDoPhongLayout.setHorizontalGroup(
            pSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSoDoPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoDoPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pSoDoPhongLayout.setVerticalGroup(
            pSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSoDoPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLDatPhong.setBackground(new java.awt.Color(194, 228, 255));

        lblQLDatPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLDatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/room_service_32px.png"))); // NOI18N
        lblQLDatPhong.setText("Quản lí đặt phòng");
        lblQLDatPhong.setToolTipText("Quản lí đặt phòng");
        lblQLDatPhong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLDatPhong.setIconTextGap(38);

        javax.swing.GroupLayout pQLDatPhongLayout = new javax.swing.GroupLayout(pQLDatPhong);
        pQLDatPhong.setLayout(pQLDatPhongLayout);
        pQLDatPhongLayout.setHorizontalGroup(
            pQLDatPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLDatPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLDatPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLDatPhongLayout.setVerticalGroup(
            pQLDatPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLDatPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLTraPhong.setBackground(new java.awt.Color(194, 228, 255));

        lblQLTraPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLTraPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/info_32px.png"))); // NOI18N
        lblQLTraPhong.setText("Quản lí trả phòng");
        lblQLTraPhong.setToolTipText("Quản lí trả phòng");
        lblQLTraPhong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLTraPhong.setIconTextGap(38);

        javax.swing.GroupLayout pQLTraPhongLayout = new javax.swing.GroupLayout(pQLTraPhong);
        pQLTraPhong.setLayout(pQLTraPhongLayout);
        pQLTraPhongLayout.setHorizontalGroup(
            pQLTraPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLTraPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLTraPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLTraPhongLayout.setVerticalGroup(
            pQLTraPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLTraPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLDichVu.setBackground(new java.awt.Color(194, 228, 255));

        lblQLDichVu.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/water_service_32px.png"))); // NOI18N
        lblQLDichVu.setText("Quản lí dịch vụ");
        lblQLDichVu.setToolTipText("Quản lí dịch vụ");
        lblQLDichVu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLDichVu.setIconTextGap(38);

        javax.swing.GroupLayout pQLDichVuLayout = new javax.swing.GroupLayout(pQLDichVu);
        pQLDichVu.setLayout(pQLDichVuLayout);
        pQLDichVuLayout.setHorizontalGroup(
            pQLDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLDichVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLDichVuLayout.setVerticalGroup(
            pQLDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLNhanVien.setBackground(new java.awt.Color(194, 228, 255));

        lblQLNhanVien.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/employee_32px.png"))); // NOI18N
        lblQLNhanVien.setText("Quản lí nhân viên");
        lblQLNhanVien.setToolTipText("Quản lí nhân viên");
        lblQLNhanVien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLNhanVien.setIconTextGap(38);

        javax.swing.GroupLayout pQLNhanVienLayout = new javax.swing.GroupLayout(pQLNhanVien);
        pQLNhanVien.setLayout(pQLNhanVienLayout);
        pQLNhanVienLayout.setHorizontalGroup(
            pQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLNhanVienLayout.setVerticalGroup(
            pQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLKhachHang.setBackground(new java.awt.Color(194, 228, 255));

        lblQLKhachHang.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/customer_32px.png"))); // NOI18N
        lblQLKhachHang.setText("Quản lí khách hàng");
        lblQLKhachHang.setToolTipText("Quản lí khách hàng");
        lblQLKhachHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLKhachHang.setIconTextGap(38);

        javax.swing.GroupLayout pQLKhachHangLayout = new javax.swing.GroupLayout(pQLKhachHang);
        pQLKhachHang.setLayout(pQLKhachHangLayout);
        pQLKhachHangLayout.setHorizontalGroup(
            pQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLKhachHangLayout.setVerticalGroup(
            pQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pQLHoaDon.setBackground(new java.awt.Color(194, 228, 255));

        lblQLHoaDon.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblQLHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/payment_32px.png"))); // NOI18N
        lblQLHoaDon.setText("Quản lí hoá hơn");
        lblQLHoaDon.setToolTipText("Quản lí hoá hơn");
        lblQLHoaDon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 102)));
        lblQLHoaDon.setIconTextGap(38);

        javax.swing.GroupLayout pQLHoaDonLayout = new javax.swing.GroupLayout(pQLHoaDon);
        pQLHoaDon.setLayout(pQLHoaDonLayout);
        pQLHoaDonLayout.setHorizontalGroup(
            pQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQLHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pQLHoaDonLayout.setVerticalGroup(
            pQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pDangXuat.setBackground(new java.awt.Color(194, 228, 255));

        lblDangXuat.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        lblDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout_32px.png"))); // NOI18N
        lblDangXuat.setText("Đăng xuất");
        lblDangXuat.setToolTipText("ALT + F4");
        lblDangXuat.setIconTextGap(38);
        lblDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pDangXuatLayout = new javax.swing.GroupLayout(pDangXuat);
        pDangXuat.setLayout(pDangXuatLayout);
        pDangXuatLayout.setHorizontalGroup(
            pDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDangXuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pDangXuatLayout.setVerticalGroup(
            pDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pMenuLayout = new javax.swing.GroupLayout(pMenu);
        pMenu.setLayout(pMenuLayout);
        pMenuLayout.setHorizontalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pSoDoPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLDatPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLTraPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pMenuLayout.setVerticalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMenuLayout.createSequentialGroup()
                .addComponent(pLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pSoDoPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLTraPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pQLHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pNoiDung.setLayout(new javax.swing.OverlayLayout(pNoiDung));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuSoDoPhong.setBorder(null);
        btnMenuSoDoPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuSoDoPhong.setToolTipText("Thu gọn menu");
        btnMenuSoDoPhong.setFocusable(false);
        btnMenuSoDoPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuSoDoPhongMouseClicked(evt);
            }
        });

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N
        button2.setFocusable(false);

        lblTenNVSoDoPhong.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVSoDoPhong.setText("Tên nhân viên");

        lblChucVuNhanVien1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien1.setText("Chức vụ");

        jLabel37.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(204, 0, 51));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Sơ đồ phòng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuSoDoPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVSoDoPhong, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTenNVSoDoPhong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien1))
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnMenuSoDoPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(4, 135, 217));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Loại Phòng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        rbtnLoaiPhong1.setBackground(new java.awt.Color(4, 135, 217));
        rbtnLoaiPhong1.setText("Tất cả");
        rbtnLoaiPhong1.setFocusable(false);
        rbtnLoaiPhong1.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnLoaiPhong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLoaiPhong1ActionPerformed(evt);
            }
        });

        rbtnLoaiPhong2.setBackground(new java.awt.Color(4, 135, 217));
        rbtnLoaiPhong2.setText("Phòng Standard");
        rbtnLoaiPhong2.setFocusable(false);
        rbtnLoaiPhong2.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnLoaiPhong2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLoaiPhong2ActionPerformed(evt);
            }
        });

        rbtnLoaiPhong3.setBackground(new java.awt.Color(4, 135, 217));
        rbtnLoaiPhong3.setText("Phòng Superior");
        rbtnLoaiPhong3.setFocusable(false);
        rbtnLoaiPhong3.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnLoaiPhong3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLoaiPhong3ActionPerformed(evt);
            }
        });

        rbtnLoaiPhong4.setBackground(new java.awt.Color(4, 135, 217));
        rbtnLoaiPhong4.setText("Phòng Deluxe");
        rbtnLoaiPhong4.setFocusable(false);
        rbtnLoaiPhong4.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnLoaiPhong4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLoaiPhong4ActionPerformed(evt);
            }
        });

        rbtnLoaiPhong5.setBackground(new java.awt.Color(4, 135, 217));
        rbtnLoaiPhong5.setText("Phòng Suite");
        rbtnLoaiPhong5.setFocusable(false);
        rbtnLoaiPhong5.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnLoaiPhong5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLoaiPhong5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnLoaiPhong1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnLoaiPhong2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnLoaiPhong3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnLoaiPhong4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnLoaiPhong5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(rbtnLoaiPhong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnLoaiPhong2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnLoaiPhong3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnLoaiPhong4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnLoaiPhong5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(4, 135, 217));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Trạng thái phòng");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        rbtnTrangThaiPhong1.setBackground(new java.awt.Color(4, 135, 217));
        rbtnTrangThaiPhong1.setText("Tất cả");
        rbtnTrangThaiPhong1.setToolTipText("");
        rbtnTrangThaiPhong1.setFocusable(false);
        rbtnTrangThaiPhong1.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnTrangThaiPhong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTrangThaiPhong1ActionPerformed(evt);
            }
        });

        rbtnTrangThaiPhong2.setBackground(new java.awt.Color(4, 135, 217));
        rbtnTrangThaiPhong2.setText("Phòng trống");
        rbtnTrangThaiPhong2.setFocusable(false);
        rbtnTrangThaiPhong2.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnTrangThaiPhong2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTrangThaiPhong2ActionPerformed(evt);
            }
        });

        rbtnTrangThaiPhong3.setBackground(new java.awt.Color(4, 135, 217));
        rbtnTrangThaiPhong3.setText("Phòng có người");
        rbtnTrangThaiPhong3.setFocusable(false);
        rbtnTrangThaiPhong3.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnTrangThaiPhong3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTrangThaiPhong3ActionPerformed(evt);
            }
        });

        rbtnTrangThaiPhong4.setBackground(new java.awt.Color(4, 135, 217));
        rbtnTrangThaiPhong4.setText("Phòng đặt trước");
        rbtnTrangThaiPhong4.setFocusable(false);
        rbtnTrangThaiPhong4.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnTrangThaiPhong4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTrangThaiPhong4ActionPerformed(evt);
            }
        });

        rbtnTrangThaiPhong5.setBackground(new java.awt.Color(4, 135, 217));
        rbtnTrangThaiPhong5.setText("Phòng đang sửa");
        rbtnTrangThaiPhong5.setFocusable(false);
        rbtnTrangThaiPhong5.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnTrangThaiPhong5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTrangThaiPhong5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnTrangThaiPhong5, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(rbtnTrangThaiPhong1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnTrangThaiPhong2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnTrangThaiPhong3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnTrangThaiPhong4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(rbtnTrangThaiPhong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnTrangThaiPhong2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnTrangThaiPhong3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnTrangThaiPhong4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnTrangThaiPhong5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jLabel36.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(204, 0, 51));
        jLabel36.setText("*Chú thích và tóm tắt sơ đồ");

        jPanel45.setBackground(new java.awt.Color(67, 160, 71));

        lblChuThichPT.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblChuThichPT.setForeground(new java.awt.Color(255, 255, 255));
        lblChuThichPT.setText("Trống");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChuThichPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblChuThichPT, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel46.setBackground(new java.awt.Color(4, 135, 217));

        lblChuThichPDT.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblChuThichPDT.setForeground(new java.awt.Color(255, 255, 255));
        lblChuThichPDT.setText("Đặt trước");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChuThichPDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblChuThichPDT, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel47.setBackground(new java.awt.Color(238, 83, 80));

        lblChuThichPCN.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblChuThichPCN.setForeground(new java.awt.Color(255, 255, 255));
        lblChuThichPCN.setText("Có người");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChuThichPCN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblChuThichPCN, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel48.setBackground(new java.awt.Color(96, 125, 139));

        lblChuThichPDS.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblChuThichPDS.setForeground(new java.awt.Color(255, 255, 255));
        lblChuThichPDS.setText("Đang sửa");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChuThichPDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblChuThichPDS, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus_24px.png"))); // NOI18N
        buttonCustom1.setText("Thêm phòng");
        buttonCustom1.setFocusable(false);
        buttonCustom1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        buttonCustom1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCustom1MouseClicked(evt);
            }
        });

        buttonCustom2.setText("Tìm");
        buttonCustom2.setFocusable(false);
        buttonCustom2.setStyle(custom.ButtonCustom.ButtonStyle.DESTRUCTIVE);
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfTimKiemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 412, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addComponent(tfTimKiemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(panelMoHinhPhong1);

        javax.swing.GroupLayout pNDSoDoPhongLayout = new javax.swing.GroupLayout(pNDSoDoPhong);
        pNDSoDoPhong.setLayout(pNDSoDoPhongLayout);
        pNDSoDoPhongLayout.setHorizontalGroup(
            pNDSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pNDSoDoPhongLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)))
        );
        pNDSoDoPhongLayout.setVerticalGroup(
            pNDSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDSoDoPhongLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDSoDoPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pNDSoDoPhongLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))))
        );

        pNoiDung.add(pNDSoDoPhong);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLDatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLDatPhong.setToolTipText("Thu gọn menu");
        btnMenuQLDatPhong.setFocusable(false);
        btnMenuQLDatPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLDatPhongMouseClicked(evt);
            }
        });

        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N
        button3.setFocusable(false);

        lblTenNVQLDP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVQLDP.setText("Tên nhân viên");

        lblChucVuNhanVien2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien2.setText("Chức vụ");

        jLabel38.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(204, 0, 51));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Quản lí đặt phòng");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVQLDP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien2))
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenNVQLDP)
                            .addComponent(btnMenuQLDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Thông tin khách hàng");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel18.setText("Họ tên:");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel19.setText("Số điện thoại:");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel28.setText("Số CMND:");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel29.setText("Địa chỉ:");

        jLabel30.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel30.setText("Giới tính:");

        tfHoTenKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfSDTKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        tfSDTKH.setText("0938649707");

        tfSoCMNDKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        tfSoCMNDKH.setText("012345678911");

        tfDiaChiKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        tfDiaChiKH.setText("1/53 Đặng Thuỳ Trâm, p13, quận BT, tpHCM");

        rbtnKHNam.setBackground(new java.awt.Color(4, 135, 217));
        rbtnKHNam.setText("Nam");
        rbtnKHNam.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnKHNu.setBackground(new java.awt.Color(4, 135, 217));
        rbtnKHNu.setText("Nữ");
        rbtnKHNu.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfSDTKH, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfSoCMNDKH, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfDiaChiKH, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rbtnKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(rbtnKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSoCMNDKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Thông tin đặt phòng");

        jLabel32.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel32.setText("Mã phòng:");

        jLabel34.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 0, 51));
        jLabel34.setText("*Đặt ngay là nhận phòng luôn");

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 64, 64));

        btnNDXoaTrang.setText("Xoá trắng");
        btnNDXoaTrang.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnNDXoaTrang.setStyle(custom.ButtonCustom.ButtonStyle.SECONDARY);
        btnNDXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNDXoaTrangActionPerformed(evt);
            }
        });
        jPanel44.add(btnNDXoaTrang);

        btnNDDatPhong.setText("Đặt phòng");
        btnNDDatPhong.setFocusable(false);
        btnNDDatPhong.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnNDDatPhong.setStyle(custom.ButtonCustom.ButtonStyle.DESTRUCTIVE);
        btnNDDatPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNDDatPhongActionPerformed(evt);
            }
        });
        jPanel44.add(btnNDDatPhong);

        btnNDDatNgay.setText("Đặt ngay");
        btnNDDatNgay.setFocusable(false);
        btnNDDatNgay.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnNDDatNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNDDatNgayActionPerformed(evt);
            }
        });
        jPanel44.add(btnNDDatNgay);

        tfMaPhongDat.setEditable(false);
        tfMaPhongDat.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel35.setText("Loại phòng:");

        tfLoaiPhongDat.setEditable(false);
        tfLoaiPhongDat.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfMaPhongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel34)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfLoaiPhongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMaPhongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLoaiPhongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pNDQLDatPhongLayout = new javax.swing.GroupLayout(pNDQLDatPhong);
        pNDQLDatPhong.setLayout(pNDQLDatPhongLayout);
        pNDQLDatPhongLayout.setHorizontalGroup(
            pNDQLDatPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pNDQLDatPhongLayout.setVerticalGroup(
            pNDQLDatPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLDatPhongLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pNoiDung.add(pNDQLDatPhong);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLTraPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLTraPhong.setToolTipText("Thu gọn menu");
        btnMenuQLTraPhong.setFocusable(false);
        btnMenuQLTraPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLTraPhongMouseClicked(evt);
            }
        });

        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N
        button4.setFocusable(false);

        lblTenNVQLTP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVQLTP.setText("Tên nhân viên");

        lblChucVuNhanVien3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien3.setText("Chức vụ");

        jLabel39.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(204, 0, 51));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Quản lí trả phòng");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLTraPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVQLTP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien3))
                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenNVQLTP)
                            .addComponent(btnMenuQLTraPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Thanh toán và trả phòng");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel45.setText("Mã khách hàng:");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel51.setText("Họ tên:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel52.setText("Mã phòng:");

        tfTraPhongMaKH.setEditable(false);
        tfTraPhongMaKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfTraPhongHoTen.setEditable(false);
        tfTraPhongHoTen.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfTraPhongMaPhong.setEditable(false);
        tfTraPhongMaPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel53.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel53.setText("Ngày nhận phòng:");

        jLabel54.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(204, 0, 51));
        jLabel54.setText("Dịch vụ:");

        tfTraPhongNgayNhanPhong.setEditable(false);
        tfTraPhongNgayNhanPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel55.setText("Buffet đồ ăn (phần):");

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel56.setText("Buffet nước (phần):");

        tfTraPhongBuffetAn.setEditable(false);
        tfTraPhongBuffetAn.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfTraPhongBuffetNuoc.setEditable(false);
        tfTraPhongBuffetNuoc.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(204, 0, 51));
        jLabel57.setText("<html>\n<body/>\n*Tổng tiền hiện tại =<br>\nTiền phòng * (ngày hiện tại - ngày nhận phòng) +<br>\nTiền buffet ăn * số lượng phần ăn +<br>\nTiền Buffet nước * số lượng phần nước<br>\n*Nếu ngày thuê chưa qua 1 ngày, thì tiền phòng là 500.000 vnd\n</body>\n</html>");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 64, 16));

        btnThanhToanTraPhong.setText("Thanh toán");
        btnThanhToanTraPhong.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnThanhToanTraPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanTraPhongActionPerformed(evt);
            }
        });
        jPanel12.add(btnThanhToanTraPhong);

        jLabel58.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel58.setText("Tổng tiền hiện tại:");

        tfTraPhongTongTien.setEditable(false);
        tfTraPhongTongTien.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel59.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel59.setText("Nhân viên lập HĐ:");

        tfTraPhongNVLapHD.setEditable(false);
        tfTraPhongNVLapHD.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel49Layout.createSequentialGroup()
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfTraPhongMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel49Layout.createSequentialGroup()
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfTraPhongHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel49Layout.createSequentialGroup()
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfTraPhongMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel49Layout.createSequentialGroup()
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfTraPhongNgayNhanPhong))
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTraPhongBuffetAn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTraPhongBuffetNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfTraPhongTongTien)
                            .addComponent(tfTraPhongNVLapHD, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongNgayNhanPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongBuffetAn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongBuffetNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTraPhongNVLapHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pNDQLTraPhongLayout = new javax.swing.GroupLayout(pNDQLTraPhong);
        pNDQLTraPhong.setLayout(pNDQLTraPhongLayout);
        pNDQLTraPhongLayout.setHorizontalGroup(
            pNDQLTraPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pNDQLTraPhongLayout.setVerticalGroup(
            pNDQLTraPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLTraPhongLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pNoiDung.add(pNDQLTraPhong);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLDichVu.setToolTipText("Thu gọn menu");
        btnMenuQLDichVu.setFocusable(false);
        btnMenuQLDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLDichVuMouseClicked(evt);
            }
        });

        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N

        lblTenNVQLDV.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVQLDV.setText("Tên nhân viên");

        lblChucVuNhanVien4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien4.setText("Chức vụ");

        jLabel40.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(204, 0, 51));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Quản lí dịch vụ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVQLDV, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(lblTenNVQLDV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien4))
                    .addComponent(button5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuQLDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Thông tin đặt dịch vụ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel6.setText("Mã khách hàng:");

        jLabel46.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel46.setText("Họ tên:");

        jLabel47.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(204, 0, 51));
        jLabel47.setText("Loại dịch vụ:");

        tfDDVMaKH.setEditable(false);
        tfDDVMaKH.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfDDVHoTen.setEditable(false);
        tfDDVHoTen.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel49.setText("Buffet đồ ăn (SL)");

        jLabel50.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel50.setText("Buffet nước (SL)");

        tfSLBuffetAn.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        tfSLBuffetNuoc.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 64, 64));

        tfChotDonDV.setText("Chốt đơn");
        tfChotDonDV.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        tfChotDonDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfChotDonDVActionPerformed(evt);
            }
        });
        jPanel11.add(tfChotDonDV);

        jLabel48.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(204, 0, 51));
        jLabel48.setText("*Không chỉnh số lượng sẽ mặc định là: Số lượng = 0");

        jLabel44.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        jLabel44.setText("Mã phòng:");

        tfDDVMaPhong.setEditable(false);
        tfDDVMaPhong.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDDVMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDDVHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDDVMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSLBuffetAn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSLBuffetNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDDVMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDDVHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDDVMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSLBuffetAn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSLBuffetNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pNDQLDichVuLayout = new javax.swing.GroupLayout(pNDQLDichVu);
        pNDQLDichVu.setLayout(pNDQLDichVuLayout);
        pNDQLDichVuLayout.setHorizontalGroup(
            pNDQLDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pNDQLDichVuLayout.setVerticalGroup(
            pNDQLDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLDichVuLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pNoiDung.add(pNDQLDichVu);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLNhanVien.setToolTipText("Thu gọn menu");
        btnMenuQLNhanVien.setFocusable(false);
        btnMenuQLNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLNhanVienMouseClicked(evt);
            }
        });

        button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N

        lblTenNVQLNhanVien.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVQLNhanVien.setText("Tên nhân viên");

        lblChucVuNhanVien5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien5.setText("Chức vụ");

        jLabel41.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 0, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Quản lí nhân viên");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVQLNhanVien, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(lblTenNVQLNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien5))
                    .addComponent(button11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jPanel23.setBackground(new java.awt.Color(4, 135, 217));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Giới tính");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        rbtnGTNhanVienTC.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTNhanVienTC.setText("Tất cả");
        rbtnGTNhanVienTC.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnGTNhanVienNam.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTNhanVienNam.setText("Nam");
        rbtnGTNhanVienNam.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnGTNhanVienNu.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTNhanVienNu.setText("Nữ");
        rbtnGTNhanVienNu.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnGTNhanVienTC, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(rbtnGTNhanVienNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnGTNhanVienNu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(rbtnGTNhanVienTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnGTNhanVienNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnGTNhanVienNu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(4, 135, 217));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Chức vụ");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        rbtnCVNhanVienTC.setBackground(new java.awt.Color(4, 135, 217));
        rbtnCVNhanVienTC.setText("Tất cả");
        rbtnCVNhanVienTC.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnCVNhanVienLeTan.setText("Lễ tân");
        rbtnCVNhanVienLeTan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        rbtnCVNhanVienQL.setText("Quản lí");
        rbtnCVNhanVienQL.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnCVNhanVienTC, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(rbtnCVNhanVienLeTan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnCVNhanVienQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(rbtnCVNhanVienTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnCVNhanVienLeTan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnCVNhanVienQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 224, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableNV);

        javax.swing.GroupLayout pNDQLNhanVienLayout = new javax.swing.GroupLayout(pNDQLNhanVien);
        pNDQLNhanVien.setLayout(pNDQLNhanVienLayout);
        pNDQLNhanVienLayout.setHorizontalGroup(
            pNDQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLNhanVienLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)))
            .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pNDQLNhanVienLayout.setVerticalGroup(
            pNDQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLNhanVienLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(pNDQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pNDQLNhanVienLayout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))))
        );

        pNoiDung.add(pNDQLNhanVien);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLKhachHang.setToolTipText("Thu gọn menu");
        btnMenuQLKhachHang.setFocusable(false);
        btnMenuQLKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLKhachHangMouseClicked(evt);
            }
        });

        button15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N

        lblTenNVQLKhachHang.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNVQLKhachHang.setText("Tên nhân viên");

        lblChucVuNhanVien6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien6.setText("Chức vụ");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 0, 51));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Quản lí khách hàng");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNVQLKhachHang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(lblTenNVQLKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien6))
                    .addComponent(button15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuQLKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jPanel31.setBackground(new java.awt.Color(4, 135, 217));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Tiện ích (Cận)");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        rbtnCan1.setBackground(new java.awt.Color(4, 135, 217));
        rbtnCan1.setText("Mức độ 1");
        rbtnCan1.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnCan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCan1ActionPerformed(evt);
            }
        });

        rbtnCan2.setBackground(new java.awt.Color(4, 135, 217));
        rbtnCan2.setText("Mức độ 2");
        rbtnCan2.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnCan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCan2ActionPerformed(evt);
            }
        });

        rbtnCan3.setBackground(new java.awt.Color(4, 135, 217));
        rbtnCan3.setText("Mức độ 3");
        rbtnCan3.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnCan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCan3ActionPerformed(evt);
            }
        });

        rbtnCan4.setBackground(new java.awt.Color(4, 135, 217));
        rbtnCan4.setText("Mức độ 4");
        rbtnCan4.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        rbtnCan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCan4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnCan1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(rbtnCan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnCan3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnCan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(rbtnCan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnCan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnCan3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnCan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 55, Short.MAX_VALUE))
        );

        jPanel33.setBackground(new java.awt.Color(4, 135, 217));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Giới tính");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        rbtnGTKHTatCa.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTKHTatCa.setText("Tất cả");
        rbtnGTKHTatCa.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnGTKHNam.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTKHNam.setText("Nam");
        rbtnGTKHNam.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        rbtnGTKHNu.setBackground(new java.awt.Color(4, 135, 217));
        rbtnGTKHNu.setText("Nữ");
        rbtnGTKHNu.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnGTKHTatCa, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(rbtnGTKHNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnGTKHNu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(rbtnGTKHTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnGTKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnGTKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        scrKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        scrKhachHang.setBorder(null);
        scrKhachHang.setFocusable(false);

        tableKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tableKH.setFocusable(false);
        tableKH.setShowGrid(true);
        tableKH.getTableHeader().setReorderingAllowed(false);
        scrKhachHang.setViewportView(tableKH);
        if (tableKH.getColumnModel().getColumnCount() > 0) {
            tableKH.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout pNDQLKhachHangLayout = new javax.swing.GroupLayout(pNDQLKhachHang);
        pNDQLKhachHang.setLayout(pNDQLKhachHangLayout);
        pNDQLKhachHangLayout.setHorizontalGroup(
            pNDQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pNDQLKhachHangLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)))
        );
        pNDQLKhachHangLayout.setVerticalGroup(
            pNDQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLKhachHangLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDQLKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pNDQLKhachHangLayout.createSequentialGroup()
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrKhachHang))))
        );

        pNoiDung.add(pNDQLKhachHang);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        btnMenuQLHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        btnMenuQLHoaDon.setToolTipText("Thu gọn menu");
        btnMenuQLHoaDon.setFocusable(false);
        btnMenuQLHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuQLHoaDonMouseClicked(evt);
            }
        });

        button16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile_32px.png"))); // NOI18N

        lblTenNhanVienQLHoaDon.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTenNhanVienQLHoaDon.setText("Tên nhân viên");

        lblChucVuNhanVien7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblChucVuNhanVien7.setText("Chức vụ");

        jLabel43.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(204, 0, 51));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Quản lí hoá đơn");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuQLHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNhanVienQLHoaDon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblChucVuNhanVien7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(lblTenNhanVienQLHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblChucVuNhanVien7))
                    .addComponent(button16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jPanel40.setBackground(new java.awt.Color(4, 135, 217));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Ngày trả phòng");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        radioButtonCustom7.setBackground(new java.awt.Color(4, 135, 217));
        radioButtonCustom7.setSelected(true);
        radioButtonCustom7.setText("Mặc định (tăng dần)");
        radioButtonCustom7.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        radioButtonCustom3.setBackground(new java.awt.Color(4, 135, 217));
        radioButtonCustom3.setText("Giảm dần");
        radioButtonCustom3.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioButtonCustom7, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(radioButtonCustom3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(radioButtonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioButtonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 119, Short.MAX_VALUE))
        );

        jPanel42.setBackground(new java.awt.Color(4, 135, 217));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Tổng tiền");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));

        radioButtonCustom8.setBackground(new java.awt.Color(4, 135, 217));
        radioButtonCustom8.setSelected(true);
        radioButtonCustom8.setText("Mặc định");
        radioButtonCustom8.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        radioButtonCustom1.setBackground(new java.awt.Color(4, 135, 217));
        radioButtonCustom1.setText("Tăng dần");
        radioButtonCustom1.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        radioButtonCustom2.setBackground(new java.awt.Color(4, 135, 217));
        radioButtonCustom2.setText("Giảm dần");
        radioButtonCustom2.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioButtonCustom8, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(radioButtonCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButtonCustom2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(radioButtonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioButtonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioButtonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableHoaDon.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tableHoaDon);

        javax.swing.GroupLayout pNDQLHoaDonLayout = new javax.swing.GroupLayout(pNDQLHoaDon);
        pNDQLHoaDon.setLayout(pNDQLHoaDonLayout);
        pNDQLHoaDonLayout.setHorizontalGroup(
            pNDQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pNDQLHoaDonLayout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)))
        );
        pNDQLHoaDonLayout.setVerticalGroup(
            pNDQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNDQLHoaDonLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pNDQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pNDQLHoaDonLayout.createSequentialGroup()
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5))))
        );

        pNoiDung.add(pNDQLHoaDon);

        javax.swing.GroupLayout pBackgroundMainLayout = new javax.swing.GroupLayout(pBackgroundMain);
        pBackgroundMain.setLayout(pBackgroundMainLayout);
        pBackgroundMainLayout.setHorizontalGroup(
            pBackgroundMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBackgroundMainLayout.createSequentialGroup()
                .addComponent(pMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBackgroundMainLayout.setVerticalGroup(
            pBackgroundMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
            .addComponent(pNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pBackgroundMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pBackgroundMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseExited
        pDangXuat.setBackground(BienMacDinh.mauNenMenu);
        lblDangXuat.setForeground(BienMacDinh.mauDen);
    }//GEN-LAST:event_lblDangXuatMouseExited

    private void lblDangXuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseEntered
        pDangXuat.setBackground(BienMacDinh.mauDanhMucDangChon);
        lblDangXuat.setForeground(BienMacDinh.mauTrang);
    }//GEN-LAST:event_lblDangXuatMouseEntered

    // Nút thu gọn menu ở panel Sơ đồ phòng
    private void btnMenuSoDoPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuSoDoPhongMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuSoDoPhongMouseClicked

    // Nút thu gọn menu ở panel Dịch vụ
    private void btnMenuQLDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLDichVuMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLDichVuMouseClicked
    
    // Nút thu gọn menu ở panel Nhân viên
    private void btnMenuQLNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLNhanVienMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLNhanVienMouseClicked

    // Nút demo thêm phòng ở panel Sơ đồ phòng
    private void buttonCustom1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCustom1MouseClicked
        panelMoHinhPhong1.add(new PanelItemPhong("P000","Mặc định","pt"));
        panelMoHinhPhong1.revalidate();
        panelMoHinhPhong1.repaint();
    }//GEN-LAST:event_buttonCustom1MouseClicked
    
    // Nút thu gọn menu ở panel Hoá đơn
    private void btnMenuQLHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLHoaDonMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLHoaDonMouseClicked

    // Nút thu gọn menu ở panel Khách hàng
    private void btnMenuQLKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLKhachHangMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLKhachHangMouseClicked

    // Nút thu gọn menu ở panel Đặt phòng
    private void btnMenuQLDatPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLDatPhongMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLDatPhongMouseClicked

    // Nút thu gọn menu ở panel Trả phòng
    private void btnMenuQLTraPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuQLTraPhongMouseClicked
        thuGonMenu();
    }//GEN-LAST:event_btnMenuQLTraPhongMouseClicked

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        CustomConfirmDialog ccdDangXuat = new CustomConfirmDialog(this);
        ccdDangXuat.showMessage("Cảnh báo đăng xuất?", "Đăng xuất sẽ trở về giao diện đăng nhập\nKiểm tra cần thận trước khi đăng xuất.");
        
        if (ccdDangXuat.getMessageType() == CustomConfirmDialog.MessageType.OK) {
            GDDangNhap gdDangNhap = new GDDangNhap();
            gdDangNhap.setVisible(true);
            Main.gdChinh.setVisible(false);
        } else {
            return;
        }
    }//GEN-LAST:event_lblDangXuatMouseClicked

    public void rePanel(JPanel p) {
        p.revalidate();
        p.repaint();
    }
    
    public void tryCatchAll() {
        try {
            soDoPhong = new SoDoPhongDAO().getAllSoDoPhong();
            loadSoDoPhong(soDoPhong);
            rePanel(panelMoHinhPhong1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tryCatchPhong1Loai(int i) {
        try {
            soDoPhong = new SoDoPhongDAO().getSoDoPhongLoai(lstMaLoaiPhong.get(i-1));
            loadSoDoPhong(soDoPhong);
            rePanel(panelMoHinhPhong1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tryCatchPhong1TT(int i) {
        try {
            soDoPhong = new SoDoPhongDAO().getSoDoPhongTrangThai(lstTTPhong.get(i-1));
            loadSoDoPhong(soDoPhong);
            rePanel(panelMoHinhPhong1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tryCatch2Bien(int i, int j) {
        try {
            soDoPhong = new SoDoPhongDAO().getSoDoPhongFilter(lstMaLoaiPhong.get(i-1), lstTTPhong.get(j-1));
            loadSoDoPhong(soDoPhong);
            rePanel(panelMoHinhPhong1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tryCatch1MaPhong(String maPhongTimKiem) {
        try {
            soDoPhong = new SoDoPhongDAO().getSoDoPhongTimKiem("%"+maPhongTimKiem+"%");
            loadSoDoPhong(soDoPhong);
            rePanel(panelMoHinhPhong1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void filterPhong(JPanel pMoHinh) {
        pMoHinh.removeAll();
        if (rbtnLoaiPhong1.isSelected() && rbtnTrangThaiPhong1.isSelected()) {
            tryCatchAll();
        }
        if (rbtnLoaiPhong1.isSelected() && rbtnTrangThaiPhong2.isSelected()) {
            tryCatchPhong1TT(2);
        }
        if (rbtnLoaiPhong1.isSelected() && rbtnTrangThaiPhong3.isSelected()) {
            tryCatchPhong1TT(3);
        }
        if (rbtnLoaiPhong1.isSelected() && rbtnTrangThaiPhong4.isSelected()) {
            tryCatchPhong1TT(4);
        }
        if (rbtnLoaiPhong1.isSelected() && rbtnTrangThaiPhong5.isSelected()) {
            tryCatchPhong1TT(5);
        }
        //////////////////////////////////////////////////////////////////////
        if (rbtnLoaiPhong2.isSelected() && rbtnTrangThaiPhong1.isSelected()) {
            tryCatchPhong1Loai(2);
        }
        if (rbtnLoaiPhong2.isSelected() && rbtnTrangThaiPhong2.isSelected()) {
            tryCatch2Bien(2, 2);
        }
        if (rbtnLoaiPhong2.isSelected() && rbtnTrangThaiPhong3.isSelected()) {
            tryCatch2Bien(2, 3);
        }
        if (rbtnLoaiPhong2.isSelected() && rbtnTrangThaiPhong4.isSelected()) {
            tryCatch2Bien(2, 4);
        }
        if (rbtnLoaiPhong2.isSelected() && rbtnTrangThaiPhong5.isSelected()) {
            tryCatch2Bien(2, 5);
        }
        //////////////////////////////////////////////////////////////////////
        if (rbtnLoaiPhong3.isSelected() && rbtnTrangThaiPhong1.isSelected()) {
            tryCatchPhong1Loai(3);
        }
        if (rbtnLoaiPhong3.isSelected() && rbtnTrangThaiPhong2.isSelected()) {
            tryCatch2Bien(3, 2);
        }
        if (rbtnLoaiPhong3.isSelected() && rbtnTrangThaiPhong3.isSelected()) {
            tryCatch2Bien(3, 3);
        }
        if (rbtnLoaiPhong3.isSelected() && rbtnTrangThaiPhong4.isSelected()) {
            tryCatch2Bien(3, 4);
        }
        if (rbtnLoaiPhong3.isSelected() && rbtnTrangThaiPhong5.isSelected()) {
            tryCatch2Bien(3, 5);
        }
        //////////////////////////////////////////////////////////////////////
        if (rbtnLoaiPhong4.isSelected() && rbtnTrangThaiPhong1.isSelected()) {
            tryCatchPhong1Loai(4);
        }
        if (rbtnLoaiPhong4.isSelected() && rbtnTrangThaiPhong2.isSelected()) {
            tryCatch2Bien(4, 2);
        }
        if (rbtnLoaiPhong4.isSelected() && rbtnTrangThaiPhong3.isSelected()) {
            tryCatch2Bien(4, 3);
        }
        if (rbtnLoaiPhong4.isSelected() && rbtnTrangThaiPhong4.isSelected()) {
            tryCatch2Bien(4, 4);
        }
        if (rbtnLoaiPhong4.isSelected() && rbtnTrangThaiPhong5.isSelected()) {
            tryCatch2Bien(4, 5);
        }
        //////////////////////////////////////////////////////////////////////
        if (rbtnLoaiPhong5.isSelected() && rbtnTrangThaiPhong1.isSelected()) {
            tryCatchPhong1Loai(5);
        }
        if (rbtnLoaiPhong5.isSelected() && rbtnTrangThaiPhong2.isSelected()) {
            tryCatch2Bien(5, 2);
        }
        if (rbtnLoaiPhong5.isSelected() && rbtnTrangThaiPhong3.isSelected()) {
            tryCatch2Bien(5, 3);
        }
        if (rbtnLoaiPhong5.isSelected() && rbtnTrangThaiPhong4.isSelected()) {
            tryCatch2Bien(5, 4);
        }
        if (rbtnLoaiPhong5.isSelected() && rbtnTrangThaiPhong5.isSelected()) {
            tryCatch2Bien(5, 5);
        }
    }
    
    private void rbtnLoaiPhong2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLoaiPhong2ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnLoaiPhong2ActionPerformed

    private void rbtnLoaiPhong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLoaiPhong1ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnLoaiPhong1ActionPerformed

    private void rbtnLoaiPhong3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLoaiPhong3ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnLoaiPhong3ActionPerformed

    private void rbtnLoaiPhong5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLoaiPhong5ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnLoaiPhong5ActionPerformed

    private void rbtnLoaiPhong4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLoaiPhong4ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnLoaiPhong4ActionPerformed

    private void rbtnTrangThaiPhong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTrangThaiPhong1ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnTrangThaiPhong1ActionPerformed

    private void rbtnTrangThaiPhong2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTrangThaiPhong2ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnTrangThaiPhong2ActionPerformed

    private void rbtnTrangThaiPhong3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTrangThaiPhong3ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnTrangThaiPhong3ActionPerformed

    private void rbtnTrangThaiPhong4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTrangThaiPhong4ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnTrangThaiPhong4ActionPerformed

    private void rbtnTrangThaiPhong5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTrangThaiPhong5ActionPerformed
        filterPhong(panelMoHinhPhong1);
    }//GEN-LAST:event_rbtnTrangThaiPhong5ActionPerformed

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        tryCatch1MaPhong(tfTimKiemPhong.getText());
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    public String getDate(String lcDate) {
        return lcDate.substring(0,10);
    }
    
    private void btnNDDatPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNDDatPhongActionPerformed
        
        if (tfMaPhongDat.getText().equals("")) {
            CustomMessageDialog cmdPhongChotDon = new CustomMessageDialog(this);
            cmdPhongChotDon.showMessage("Cảnh báo chưa chọn phòng!", "Bạn chưa lấy dữ liệu từ phòng thuê qua!\nVui lòng qua sơ đồ phòng để chọn ^^");
            return;
        }
        
        boolean dungSaiTrong = kiemTraTTKhachTrong(tfHoTenKH.getText().trim(), tfSDTKH.getText().trim(), tfSoCMNDKH.getText().trim(), tfDiaChiKH.getText().trim());
        if (dungSaiTrong == false) {
            return;
        }
        
        boolean dungSaiRegex = kiemTraTTKhachRegex(tfHoTenKH.getText().trim(), tfSDTKH.getText().trim(), tfSoCMNDKH.getText().trim(), tfDiaChiKH.getText().trim());
        if (dungSaiRegex == false) {
            return;
        }
        
        CustomConfirmDialog ccdDatPhong = new CustomConfirmDialog(this);
        ccdDatPhong.showMessage("Xác nhận đặt phòng?", "Hãy kiểm tra kĩ thông tin khách hàng trước khi đặt!\nChúc bạn làm việc tốt ^^");
        
        if (ccdDatPhong.getMessageType() == CustomConfirmDialog.MessageType.OK) {
            String tenKH = tfHoTenKH.getText().trim();
            String soDT = tfSDTKH.getText().trim();
            String soCMND = tfSoCMNDKH.getText().trim();
            String diaChi = tfDiaChiKH.getText().trim();
            boolean gioiTinh = rbtnKHNu.isSelected();
    //        String date = LocalDateTime.now().toString();
    //        System.out.println(getDate(date));
            KhachHang kh = new KhachHang(0, tenKH, soDT, soCMND, diaChi, gioiTinh);
            try {
                new KhachHangDAO().themKhachHang(kh);

                int maCuoi = new KhachHangDAO().getLastMaKH();
                String maPhong = tfMaPhongDat.getText().trim();
                LocalDateTime ngayDatPhong = LocalDateTime.now();
                String ngayDatFormat = getDate(ngayDatPhong.toString());
                new DatPhongDAO().themDatPhong(ngayDatFormat, maPhong, maCuoi);

                new SoDoPhongDAO().updateTrangThaiPhong("pdt", maPhong);

                refreshSoDoPhong();
                loadSoDoPhong(getSoDoPhong());

                dsKhachHang = new KhachHangDAO().getAllKhachHang();
                loadDSKhachHangLenUI(dsKhachHang);

                CustomMessageDialog cmdDatPhong = new CustomMessageDialog(this);
                cmdDatPhong.showMessage("Đặt phòng thành công!", "Có thể qua sơ đồ phòng để kiểm tra!\nChúc bạn làm việc tốt ^^");

                if (cmdDatPhong.getMessageType() == CustomMessageDialog.MessageType.OK) {  
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnNDDatPhongActionPerformed

    public void setFontAndRowHeight(Font fTieuDe, Font fTable, int height) {
        tableKH.setFont(fTable);
        tableKH.getTableHeader().setFont(fTieuDe);
        tableKH.setRowHeight(height);
    }
    
    private void rbtnCan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCan1ActionPerformed
        setFontAndRowHeight(BienMacDinh.fontTieuDeTableCan1, BienMacDinh.fontTableCan1, 27);
    }//GEN-LAST:event_rbtnCan1ActionPerformed

    private void rbtnCan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCan2ActionPerformed
        setFontAndRowHeight(BienMacDinh.fontTieuDeTableCan2, BienMacDinh.fontTableCan2, 29);
    }//GEN-LAST:event_rbtnCan2ActionPerformed

    private void rbtnCan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCan3ActionPerformed
        setFontAndRowHeight(BienMacDinh.fontTieuDeTableCan3, BienMacDinh.fontTableCan3, 31);
    }//GEN-LAST:event_rbtnCan3ActionPerformed

    private void rbtnCan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCan4ActionPerformed
        setFontAndRowHeight(BienMacDinh.fontTieuDeTableCan4, BienMacDinh.fontTableCan4, 33);
    }//GEN-LAST:event_rbtnCan4ActionPerformed

    private void btnNDXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNDXoaTrangActionPerformed
        tfHoTenKH.setText("");
        tfSDTKH.setText("");
        tfSoCMNDKH.setText("");
        tfDiaChiKH.setText("");
        rbtnKHNam.setSelected(true);
        tfHoTenKH.requestFocus();
    }//GEN-LAST:event_btnNDXoaTrangActionPerformed

    private void btnNDDatNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNDDatNgayActionPerformed
        
        if (tfMaPhongDat.getText().equals("")) {
            CustomMessageDialog cmdKiemTraPhongDatNgay = new CustomMessageDialog(this);
            cmdKiemTraPhongDatNgay.showMessage("Cảnh báo chưa chọn phòng!", "Bạn chưa lấy dữ liệu từ phòng qua!\nVui lòng qua sơ đồ phòng để chọn ^^");
            return;
        }
        
        boolean dungSaiTrong = kiemTraTTKhachTrong(tfHoTenKH.getText().trim(), tfSDTKH.getText().trim(), tfSoCMNDKH.getText().trim(), tfDiaChiKH.getText().trim());
        if (dungSaiTrong == false) {
            return;
        }
        
        boolean dungSai = kiemTraTTKhachRegex(tfHoTenKH.getText().trim(), tfSDTKH.getText().trim(), tfSoCMNDKH.getText().trim(), tfDiaChiKH.getText().trim());
        if (dungSai == false) {
            return;
        }
        
        CustomConfirmDialog ccdDatNgay = new CustomConfirmDialog(this);
        ccdDatNgay.showMessage("Xác nhận đặt phòng ngay?", "Hãy kiểm tra kĩ thông tin khách hàng trước khi đặt!\nChúc bạn làm việc tốt ^^");
        
        if (ccdDatNgay.getMessageType() == CustomConfirmDialog.MessageType.OK) {
            String tenKH = tfHoTenKH.getText().trim();
            String soDT = tfSDTKH.getText().trim();
            String soCMND = tfSoCMNDKH.getText().trim();
            String diaChi = tfDiaChiKH.getText().trim();
            boolean gioiTinh = rbtnKHNu.isSelected();
    //        String date = LocalDateTime.now().toString();
    //        System.out.println(getDate(date));
            KhachHang kh = new KhachHang(0, tenKH, soDT, soCMND, diaChi, gioiTinh);
            try {
                new KhachHangDAO().themKhachHang(kh);

                int maCuoi = new KhachHangDAO().getLastMaKH();
                String maPhong = tfMaPhongDat.getText().trim();
                LocalDateTime ngayDatPhong = LocalDateTime.now();
                String ngayDatFormat = getDate(ngayDatPhong.toString());

                new NhanPhongDAO().themNhanPhong(ngayDatFormat, maPhong, maCuoi);
                new SoDoPhongDAO().updateTrangThaiPhong(Phong.PHONG_CO_NGUOI, maPhong);

                refreshSoDoPhong();
                loadSoDoPhong(getSoDoPhong());

                dsKhachHang = new KhachHangDAO().getAllKhachHang();
                loadDSKhachHangLenUI(dsKhachHang);

                CustomMessageDialog cmdDatPhongThanhCong = new CustomMessageDialog(this);
                cmdDatPhongThanhCong.showMessage("Đặt và nhận phòng thành công!", "Có thể qua sơ đồ phòng để kiểm tra!\nChúc bạn làm việc tốt ^^");

                if (cmdDatPhongThanhCong.getMessageType() == CustomMessageDialog.MessageType.OK) {  
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnNDDatNgayActionPerformed

    private void tfChotDonDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChotDonDVActionPerformed
        
        if (tfDDVMaKH.getText().equals("")) {
            CustomMessageDialog cmdPhongChotDon = new CustomMessageDialog(this);
            cmdPhongChotDon.showMessage("Cảnh báo chưa chọn phòng!", "Bạn chưa lấy dữ liệu từ phòng thuê qua!\nVui lòng qua sơ đồ phòng để chọn ^^");
            return;
        }
        
        if (tfSLBuffetAn.getText().matches("[0-9]") == false || tfSLBuffetNuoc.getText().matches("[0-9]") == false) {
            CustomMessageDialog cmdSLBuffetRegex = new CustomMessageDialog(this);
            cmdSLBuffetRegex.showMessage("Cảnh báo dữ liệu SL buffet!", "Số lượng buffet có dữ liệu số!\nVui lòng nhập lại đúng cú pháp ^^");
            return;
        }
        
        int maKhachHang = Integer.parseInt(tfDDVMaKH.getText().trim());
        int SLPhanAn = 0;
        int SLPhanUong = 0;
        if (!tfSLBuffetAn.getText().equals("")) {
            SLPhanAn = Integer.parseInt(tfSLBuffetAn.getText().trim());
        }
        if (!tfSLBuffetNuoc.getText().equals("")) {
            SLPhanUong = Integer.parseInt(tfSLBuffetNuoc.getText().trim());
        }
        if (SLPhanAn == 0 && SLPhanUong == 0) {
            CustomMessageDialog cmdChuaNhapSLBuffet = new CustomMessageDialog(this);
            cmdChuaNhapSLBuffet.showMessage("Chưa nhập số lượng buffet!", "Hãy kiểm tra lại số lượng ở trên!\nChúc bạn làm việc tốt ^^");
            if (cmdChuaNhapSLBuffet.getMessageType() == CustomMessageDialog.MessageType.OK) {  
                return;
            }
        }

        if (SLPhanAn < 0 || SLPhanUong < 0) {
            CustomMessageDialog cmdSLBuffetAm = new CustomMessageDialog(this);
            cmdSLBuffetAm.showMessage("Số lượng buffet lỗi!", "Số lượng buffet không được âm!\nVui lòng nhập lại ^^");
            if (cmdSLBuffetAm.getMessageType() == CustomMessageDialog.MessageType.OK) {  
                return;
            }
        }
        
        CustomConfirmDialog ccdChotDon = new CustomConfirmDialog(this);
        ccdChotDon.showMessage("Xác nhận đặt dịch vụ?", "Kiểm tra kĩ số lượng nhập vào\nThao tác không thể hoàn tác!");
        
        if (ccdChotDon.getMessageType() == CustomConfirmDialog.MessageType.OK) {

            DatDichVu ddvAn = new DatDichVu(0, maKhachHang, 1, SLPhanAn);
            DatDichVu ddvUong = new DatDichVu(0, maKhachHang, 2, SLPhanUong);

            try {
                new DatDichVuDAO().themDatDichVu(ddvAn);
                new DatDichVuDAO().themDatDichVu(ddvUong);
            } catch (SQLException ex) {
                Logger.getLogger(GDChinh.class.getName()).log(Level.SEVERE, null, ex);
            }

            CustomMessageDialog cmdThanhToanDichVu = new CustomMessageDialog(this);
            cmdThanhToanDichVu.showMessage("Thanh toán thành công!", "Tiền sẽ được thanh toán chung khi trả phòng!\nChúc bạn làm việc tốt ^^");
        } else {
            return;
        }
    }//GEN-LAST:event_tfChotDonDVActionPerformed

    private void btnThanhToanTraPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanTraPhongActionPerformed

        if (tfTraPhongMaPhong.getText().equals("")) {
            CustomMessageDialog cmdPhongChotDon = new CustomMessageDialog(this);
            cmdPhongChotDon.showMessage("Cảnh báo chưa chọn phòng!", "Bạn chưa lấy dữ liệu từ phòng thuê qua!\nVui lòng qua sơ đồ phòng để chọn ^^");
            return;
        }
        
        CustomConfirmDialog ccdThanhToanPhong = new CustomConfirmDialog(this);
        ccdThanhToanPhong.showMessage("Xác nhận thanh toán phòng?", "Xác nhận kĩ khách hàng khi muốn thanh toán!\nThao tác không thể hoàn tác.");
        
        if (ccdThanhToanPhong.getMessageType() == CustomConfirmDialog.MessageType.OK) {
            try {
                new HoaDonDAO().themHoaDon(hd);
                new SoDoPhongDAO().updateTrangThaiPhong(Phong.PHONG_TRONG, hd.getMaPhong());

                CustomMessageDialog cmdThanhToanTraPhong = new CustomMessageDialog(this);
                cmdThanhToanTraPhong.showMessage("Thanh toán thành công!", "Hãy kiểm tra tiền trước khi khách đi!\nChúc bạn làm việc tốt ^^");

                new DatDichVuDAO().xoaDatDichVu(tfTraPhongMaKH.getText());
                
                dsHoaDon = new HoaDonDAO().getAllHoaDon();
                loadDSHoaDonLenUI(dsHoaDon);
                
                refreshSoDoPhong();
                loadSoDoPhong(soDoPhong);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnThanhToanTraPhongActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupCan;
    private javax.swing.ButtonGroup btnGroupChucVuNhanVien;
    private javax.swing.ButtonGroup btnGroupGioiTinhDatPhong;
    private javax.swing.ButtonGroup btnGroupGioiTinhKH;
    private javax.swing.ButtonGroup btnGroupGioiTinhNhanVien;
    private javax.swing.ButtonGroup btnGroupLoaiPhong;
    private javax.swing.ButtonGroup btnGroupTrangThai;
    private custom.Button btnMenuQLDatPhong;
    private custom.Button btnMenuQLDichVu;
    private custom.Button btnMenuQLHoaDon;
    private custom.Button btnMenuQLKhachHang;
    private custom.Button btnMenuQLNhanVien;
    private custom.Button btnMenuQLTraPhong;
    private custom.Button btnMenuSoDoPhong;
    private custom.ButtonCustom btnNDDatNgay;
    private custom.ButtonCustom btnNDDatPhong;
    private custom.ButtonCustom btnNDXoaTrang;
    private custom.ButtonCustom btnThanhToanTraPhong;
    private custom.Button button11;
    private custom.Button button15;
    private custom.Button button16;
    private custom.Button button2;
    private custom.Button button3;
    private custom.Button button4;
    private custom.Button button5;
    private custom.ButtonCustom buttonCustom1;
    private custom.ButtonCustom buttonCustom2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblChuThichPCN;
    private javax.swing.JLabel lblChuThichPDS;
    private javax.swing.JLabel lblChuThichPDT;
    private javax.swing.JLabel lblChuThichPT;
    private javax.swing.JLabel lblChucVuNhanVien1;
    private javax.swing.JLabel lblChucVuNhanVien2;
    private javax.swing.JLabel lblChucVuNhanVien3;
    private javax.swing.JLabel lblChucVuNhanVien4;
    private javax.swing.JLabel lblChucVuNhanVien5;
    private javax.swing.JLabel lblChucVuNhanVien6;
    private javax.swing.JLabel lblChucVuNhanVien7;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblQLDatPhong;
    private javax.swing.JLabel lblQLDichVu;
    private javax.swing.JLabel lblQLHoaDon;
    private javax.swing.JLabel lblQLKhachHang;
    private javax.swing.JLabel lblQLNhanVien;
    private javax.swing.JLabel lblQLTraPhong;
    private javax.swing.JLabel lblSoDoPhong;
    private javax.swing.JLabel lblTenNVQLDP;
    private javax.swing.JLabel lblTenNVQLDV;
    private javax.swing.JLabel lblTenNVQLKhachHang;
    private javax.swing.JLabel lblTenNVQLNhanVien;
    private javax.swing.JLabel lblTenNVQLTP;
    private javax.swing.JLabel lblTenNVSoDoPhong;
    private javax.swing.JLabel lblTenNhanVienQLHoaDon;
    private javax.swing.JPanel pBackgroundMain;
    private javax.swing.JPanel pDangXuat;
    private javax.swing.JPanel pLogo;
    private javax.swing.JPanel pMenu;
    private javax.swing.JPanel pNDQLDatPhong;
    private javax.swing.JPanel pNDQLDichVu;
    private javax.swing.JPanel pNDQLHoaDon;
    private javax.swing.JPanel pNDQLKhachHang;
    private javax.swing.JPanel pNDQLNhanVien;
    private javax.swing.JPanel pNDQLTraPhong;
    private javax.swing.JPanel pNDSoDoPhong;
    private javax.swing.JPanel pNoiDung;
    private javax.swing.JPanel pQLDatPhong;
    private javax.swing.JPanel pQLDichVu;
    private javax.swing.JPanel pQLHoaDon;
    private javax.swing.JPanel pQLKhachHang;
    private javax.swing.JPanel pQLNhanVien;
    private javax.swing.JPanel pQLTraPhong;
    private javax.swing.JPanel pSoDoPhong;
    private component.PanelMoHinhPhong panelMoHinhPhong1;
    private custom.RadioButtonCustom radioButtonCustom1;
    private custom.RadioButtonCustom radioButtonCustom2;
    private custom.RadioButtonCustom radioButtonCustom3;
    private custom.RadioButtonCustom radioButtonCustom7;
    private custom.RadioButtonCustom radioButtonCustom8;
    private custom.RadioButtonCustom rbtnCVNhanVienLeTan;
    private custom.RadioButtonCustom rbtnCVNhanVienQL;
    private custom.RadioButtonCustom rbtnCVNhanVienTC;
    private custom.RadioButtonCustom rbtnCan1;
    private custom.RadioButtonCustom rbtnCan2;
    private custom.RadioButtonCustom rbtnCan3;
    private custom.RadioButtonCustom rbtnCan4;
    private custom.RadioButtonCustom rbtnGTKHNam;
    private custom.RadioButtonCustom rbtnGTKHNu;
    private custom.RadioButtonCustom rbtnGTKHTatCa;
    private custom.RadioButtonCustom rbtnGTNhanVienNam;
    private custom.RadioButtonCustom rbtnGTNhanVienNu;
    private custom.RadioButtonCustom rbtnGTNhanVienTC;
    private custom.RadioButtonCustom rbtnKHNam;
    private custom.RadioButtonCustom rbtnKHNu;
    private custom.RadioButtonCustom rbtnLoaiPhong1;
    private custom.RadioButtonCustom rbtnLoaiPhong2;
    private custom.RadioButtonCustom rbtnLoaiPhong3;
    private custom.RadioButtonCustom rbtnLoaiPhong4;
    private custom.RadioButtonCustom rbtnLoaiPhong5;
    private custom.RadioButtonCustom rbtnTrangThaiPhong1;
    private custom.RadioButtonCustom rbtnTrangThaiPhong2;
    private custom.RadioButtonCustom rbtnTrangThaiPhong3;
    private custom.RadioButtonCustom rbtnTrangThaiPhong4;
    private custom.RadioButtonCustom rbtnTrangThaiPhong5;
    private javax.swing.JScrollPane scrKhachHang;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableKH;
    private javax.swing.JTable tableNV;
    private custom.ButtonCustom tfChotDonDV;
    private javax.swing.JTextField tfDDVHoTen;
    private javax.swing.JTextField tfDDVMaKH;
    private javax.swing.JTextField tfDDVMaPhong;
    private javax.swing.JTextField tfDiaChiKH;
    private javax.swing.JTextField tfHoTenKH;
    private javax.swing.JTextField tfLoaiPhongDat;
    private javax.swing.JTextField tfMaPhongDat;
    private javax.swing.JTextField tfSDTKH;
    private javax.swing.JTextField tfSLBuffetAn;
    private javax.swing.JTextField tfSLBuffetNuoc;
    private javax.swing.JTextField tfSoCMNDKH;
    private custom.MyTextField tfTimKiemPhong;
    private javax.swing.JTextField tfTraPhongBuffetAn;
    private javax.swing.JTextField tfTraPhongBuffetNuoc;
    private javax.swing.JTextField tfTraPhongHoTen;
    private javax.swing.JTextField tfTraPhongMaKH;
    private javax.swing.JTextField tfTraPhongMaPhong;
    private javax.swing.JTextField tfTraPhongNVLapHD;
    private javax.swing.JTextField tfTraPhongNgayNhanPhong;
    private javax.swing.JTextField tfTraPhongTongTien;
    // End of variables declaration//GEN-END:variables

    public void customJPanelVaJLableKhiDuocChon(JPanel p, JLabel lbl) {
        p.setBackground(BienMacDinh.mauDanhMucDangChon);
        lbl.setForeground(BienMacDinh.mauTrang);
        lbl.setBorder(null);
    }
    
    public void customJPanelVaJLableKhiKhongDuocChon(JPanel p, JLabel lbl) {
        p.setBackground(BienMacDinh.mauNenMenu);
        lbl.setForeground(BienMacDinh.mauDen);
        lbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, BienMacDinh.mauBorderBottomMenu));
    }
    
    public void chuyenManHinh(int maManHinh) {
        if (maManHinh == 1) {
            // Sơ đồ phòng
            setVisibleLstPanel(lstPanel, new int[]{1,0,0,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {1,0,0,0,0,0,0});
        }
        if (maManHinh == 2) {
            // Đặt phòng
            setVisibleLstPanel(lstPanel, new int[]{0,1,0,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,1,0,0,0,0,0});
        }
        if (maManHinh == 3) {
            // Trả phòng
            setVisibleLstPanel(lstPanel, new int[]{0,0,1,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,1,0,0,0,0});
        }
        if (maManHinh == 4) {
            // Dịch vụ
            setVisibleLstPanel(lstPanel, new int[]{0,0,0,1,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,1,0,0,0});
        }
        if (maManHinh == 5) {
            // Nhân viên
            setVisibleLstPanel(lstPanel, new int[]{0,0,0,0,1,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,1,0,0});
        }
        if (maManHinh == 6) {
            // Khách hàng
            setVisibleLstPanel(lstPanel, new int[]{ 0,0,0,0,0,1,0 }); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,0,1,0});
        }
        if (maManHinh == 7) {
            // Hoá đơn
            setVisibleLstPanel(lstPanel, new int[]{ 0,0,0,0,0,0,1 }); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,0,0,1});
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        
        if (o.equals(lblSoDoPhong)) {
            // Sơ đồ phòng
            setVisibleLstPanel(lstPanel, new int[]{1,0,0,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {1,0,0,0,0,0,0});
            
        } else if (o.equals(lblQLDatPhong)) {
            // Đặt phòng
            setVisibleLstPanel(lstPanel, new int[]{0,1,0,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,1,0,0,0,0,0});
            
        } else if (o.equals(lblQLTraPhong)) {
            // Trả phòng
            setVisibleLstPanel(lstPanel, new int[]{0,0,1,0,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,1,0,0,0,0});
            
        } else if (o.equals(lblQLDichVu)) {
            // Dịch vụ
            setVisibleLstPanel(lstPanel, new int[]{0,0,0,1,0,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,1,0,0,0});
            
        } else if (o.equals(lblQLNhanVien)) {
            // Nhân viên
            setVisibleLstPanel(lstPanel, new int[]{0,0,0,0,1,0,0}); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,1,0,0});
            
        } else if (o.equals(lblQLKhachHang)) {
            // Khách hàng
            setVisibleLstPanel(lstPanel, new int[]{ 0,0,0,0,0,1,0 }); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,0,1,0});
            
        } else if (o.equals(lblQLHoaDon)) {
            // Hoá đơn
            setVisibleLstPanel(lstPanel, new int[]{ 0,0,0,0,0,0,1 }); 
            customMenu(lstPanelMenu, lstLabel, new int[] {0,0,0,0,0,0,1});
        }
    }
    
    private void customMenu(LinkedList<JPanel> lstPanel, LinkedList<JLabel> lstLabel, int[] tat)
    {
        int n = lstPanel.size();
        int i = 0;
        for (i = 0; i < n; i++) {
            if (tat[i] == 0) {
                customJPanelVaJLableKhiKhongDuocChon(lstPanel.get(i), lstLabel.get(i));
            } else {
                customJPanelVaJLableKhiDuocChon(lstPanel.get(i), lstLabel.get(i));
                this.manHinhDangChon = i+1;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(lblSoDoPhong)) {
            customJPanelVaJLableKhiDuocChon(pSoDoPhong, lblSoDoPhong);
        }
        if (o.equals(lblQLDatPhong)) {
            customJPanelVaJLableKhiDuocChon(pQLDatPhong, lblQLDatPhong);
        }
        if (o.equals(lblQLTraPhong)) {
            customJPanelVaJLableKhiDuocChon(pQLTraPhong, lblQLTraPhong);
        }
        if (o.equals(lblQLDichVu)) {
            customJPanelVaJLableKhiDuocChon(pQLDichVu, lblQLDichVu);
        }
        if (o.equals(lblQLNhanVien)) {
            customJPanelVaJLableKhiDuocChon(pQLNhanVien, lblQLNhanVien);
        }
        if (o.equals(lblQLKhachHang)) {
            customJPanelVaJLableKhiDuocChon(pQLKhachHang, lblQLKhachHang);
        }
        if (o.equals(lblQLHoaDon)) {
            customJPanelVaJLableKhiDuocChon(pQLHoaDon, lblQLHoaDon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(lblSoDoPhong) && manHinhDangChon != 1) {
            customJPanelVaJLableKhiKhongDuocChon(pSoDoPhong, lblSoDoPhong);
        }
        if (o.equals(lblQLDatPhong) && manHinhDangChon != 2) {
            customJPanelVaJLableKhiKhongDuocChon(pQLDatPhong, lblQLDatPhong);
        }
        if (o.equals(lblQLTraPhong) && manHinhDangChon != 3) {
            customJPanelVaJLableKhiKhongDuocChon(pQLTraPhong, lblQLTraPhong);
        }
        if (o.equals(lblQLDichVu) && manHinhDangChon != 4) {
            customJPanelVaJLableKhiKhongDuocChon(pQLDichVu, lblQLDichVu);
        }
        if (o.equals(lblQLNhanVien) && manHinhDangChon != 5) {
            customJPanelVaJLableKhiKhongDuocChon(pQLNhanVien, lblQLNhanVien);
        }
        if (o.equals(lblQLKhachHang) && manHinhDangChon != 6) {
            customJPanelVaJLableKhiKhongDuocChon(pQLKhachHang, lblQLKhachHang);
        }
        if (o.equals(lblQLHoaDon) && manHinhDangChon != 7) {
            customJPanelVaJLableKhiKhongDuocChon(pQLHoaDon, lblQLHoaDon);
        }
    }

    public ArrayList<Phong> getSoDoPhong() {
        filterPhong(panelMoHinhPhong1);
        return soDoPhong;
    }
    
    public void refreshSoDoPhong() throws SQLException {
        soDoPhong = new SoDoPhongDAO().getAllSoDoPhong();
    }
    
    public void setTextTTDatPhong(String maPhongDat, String loaiPhong) {
        tfMaPhongDat.setText(maPhongDat);
        tfLoaiPhongDat.setText(loaiPhong);
    }
    
    public boolean validateDatPhong() {
        
        return true;
    } 
    
    public void demTTPhong() throws SQLException {
        lstDemLoaiPhong.add(new SoDoPhongDAO().getCountPhongTheoTT("pt"));
        lstDemLoaiPhong.add(new SoDoPhongDAO().getCountPhongTheoTT("pdt"));
        lstDemLoaiPhong.add(new SoDoPhongDAO().getCountPhongTheoTT("pcn"));
        lstDemLoaiPhong.add(new SoDoPhongDAO().getCountPhongTheoTT("pds"));
    }
    
    public void loadDSKhachHangLenUI(ArrayList<KhachHang> dsKH) {
        modelKhachHang.setRowCount(0);
        for (KhachHang item : dsKH) {
            String gioiTinh = "Nam";
            if (item.isGioiTinh()) {
                gioiTinh = "Nữ";
            }
            modelKhachHang.addRow(new Object[] {item.getMaKH(), item.getHoTen(), item.getSoDT(), item.getSoCMND(), gioiTinh});
        }
    }
    
    public void loadDSNhanVienLenUI(ArrayList<NhanVien> dsNV) {
        modelNhanVien.setRowCount(0);
        for (NhanVien item : dsNV) {
            String gioiTinh = "Nam";
            if (item.isGioiTinh()) {
                gioiTinh = "Nữ";
            }
            String chucVu = "Lễ tân";
            if (item.isIsQuanLi()) {
                chucVu = "Quản lí";
            }
            modelNhanVien.addRow(new Object[] {item.getMaNV(), item.getHoTen(), item.getSoDT(), item.getSoCMND(), gioiTinh, chucVu});
        }
    }
    
    public void CustomTableBeautiful(JTable table, int tongCot) {
        table.setRowHeight(24);
        table.setSelectionBackground(BienMacDinh.mauSelectTable);
        table.getTableHeader().setFont(BienMacDinh.fontTieuDeTable);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(BienMacDinh.mauTieuDeTable);
        table.getTableHeader().setForeground(BienMacDinh.mauTrang);
        
        table.setFont(BienMacDinh.fontTable);
        table.setGridColor(BienMacDinh.mauGridTable);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i=0; i<tongCot; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
    }
    
    public void selectedTheoMaKH(int maKH) {
        int maMongDoi = 0;
        for (int i=0; i<dsKhachHang.size(); i++) {
            maMongDoi = Integer.parseInt(tableKH.getModel().getValueAt(i, 0).toString());
            if (maKH == maMongDoi) {
                tableKH.setRowSelectionInterval(i, i);
            }
        }
    }
    
    public void setTextDatDichVu(int maKhachHang, String hoTen, String maPhong) {
        tfDDVMaKH.setText(maKhachHang+"");
        tfDDVHoTen.setText(hoTen);
        tfDDVMaPhong.setText(maPhong);
    }
    
    public void setTextTraPhong(int maKhachHang, String hoTen, String maPhong, String ngayNhanPhong, int phanBuffetAn, int phanBuffetNuoc, Double tongTienHienTai) {
        tfTraPhongMaKH.setText(maKhachHang+""); //ok
        tfTraPhongHoTen.setText(hoTen); //ok
        tfTraPhongMaPhong.setText(maPhong); //ok
        tfTraPhongNgayNhanPhong.setText(ngayNhanPhong); //ok
        tfTraPhongBuffetAn.setText(phanBuffetAn+""); //ok
        tfTraPhongBuffetNuoc.setText(phanBuffetNuoc+""); //ok
        tfTraPhongTongTien.setText(tongTienHienTai+"");
        tfTraPhongNVLapHD.setText(lblTenNVSoDoPhong.getText());
    }
    
    public void setTextTenNhanVienDN(String tenNhanVien) {
        lblTenNVSoDoPhong.setText(tenNhanVien);
        lblTenNVQLDP.setText(tenNhanVien);
        lblTenNVQLTP.setText(tenNhanVien);
        lblTenNVQLDV.setText(tenNhanVien);
        lblTenNVQLNhanVien.setText(tenNhanVien);
        lblTenNVQLKhachHang.setText(tenNhanVien);
        lblTenNhanVienQLHoaDon.setText(tenNhanVien);
    }
    
    public void setTextChucVuNhanVien(boolean isQL) {
        String chucVu = "Lễ tân";
        if(isQL == true) {
            chucVu = "Quản lí";
            lblChucVuNhanVien1.setText(chucVu);
            lblChucVuNhanVien2.setText(chucVu);
            lblChucVuNhanVien3.setText(chucVu);
            lblChucVuNhanVien4.setText(chucVu);
            lblChucVuNhanVien5.setText(chucVu);
            lblChucVuNhanVien6.setText(chucVu);
            lblChucVuNhanVien7.setText(chucVu);
        } else {
            lblChucVuNhanVien1.setText(chucVu);
            lblChucVuNhanVien2.setText(chucVu);
            lblChucVuNhanVien3.setText(chucVu);
            lblChucVuNhanVien4.setText(chucVu);
            lblChucVuNhanVien5.setText(chucVu);
            lblChucVuNhanVien6.setText(chucVu);
            lblChucVuNhanVien7.setText(chucVu);
        }
    }
    
    public void loadDuLieuTomTatSoDoPhong() {
        
        lblChuThichPT.setText(lblChuThichPT.getText() + " (" + lstDemLoaiPhong.get(0) + ")");
        lblChuThichPDT.setText(lblChuThichPDT.getText() + " (" + lstDemLoaiPhong.get(1) + ")");
        lblChuThichPCN.setText(lblChuThichPCN.getText() + " (" + lstDemLoaiPhong.get(2) + ")");
        lblChuThichPDS.setText(lblChuThichPDS.getText() + " (" + lstDemLoaiPhong.get(3) + ")");
    }
    
    public void loadDSHoaDonLenUI(ArrayList<HoaDon> dsHoaDon) {
        modelHoaDon.setRowCount(0);
        for (HoaDon item : dsHoaDon) {
            modelHoaDon.addRow(new Object[] {item.getMaHD(), item.getTongTien(), item.getNgayNhanString(), item.getNgayTraString(), item.getMaNV(), item.getMaKH(), item.getMaPhong()});
        }
    }
    
    /**
     * Các entity đã check
     * Phong
     * LoaiPhong
     * TaiKhoan
     * NhanVien
     * KhachHang
     */
    
    public boolean kiemTraTTKhachTrong(String hoTen, String soDT, String soCMND, String diaChi) {
        boolean dungSai = true;
        int boTrong = 0;
        int viTriTrong = 0;
        
        if (hoTen.equals("")) {
            viTriTrong = 1;
            boTrong++;
        }
        if (soDT.equals("")) {
            viTriTrong = 2;
            boTrong++;
        }
        if (soCMND.equals("")) {
            viTriTrong = 3;
            boTrong++;
        }
        if (diaChi.equals("")) {
            viTriTrong = 4;
            boTrong++;
        }
        
        if (boTrong == 0) {
            return dungSai;
        }
        
        if (boTrong > 1) {
            CustomMessageDialog cmdThanhToanTraPhong = new CustomMessageDialog(this);
            cmdThanhToanTraPhong.showMessage("Cảnh báo nhập dữ liệu!", "Còn nhiều trường chưa điền đầy đủ thông tin!\nVui lòng không để trống và nhập đúng cú pháp");
            dungSai = false;
            return dungSai;
        }
        
        switch(viTriTrong) {
                case 1:
                    CustomMessageDialog cmdCanhBaoHoTen = new CustomMessageDialog(this);
                    cmdCanhBaoHoTen.showMessage("Cảnh báo tên khách hàng!", "Dữ liệu tên khách hàng chưa được nhập!\nVui lòng không để trống và nhập đúng cú pháp");
                    dungSai = false;
                    break;
                case 2:
                    CustomMessageDialog cmdCanhBaoSoDT = new CustomMessageDialog(this);
                    cmdCanhBaoSoDT.showMessage("Cảnh báo số điện thoại!", "Dữ liệu số điện thoại chưa được nhập!\nVui lòng không để trống và nhập đúng cú pháp");
                    dungSai = false;
                    break;
                case 3:
                    CustomMessageDialog cmdCanhBaoSoCMND = new CustomMessageDialog(this);
                    cmdCanhBaoSoCMND.showMessage("Cảnh báo số CMND!", "Dữ liệu số CMND chưa được nhập!\nVui lòng không để trống và nhập đúng cú pháp");
                    dungSai = false;
                    break;
                case 4:
                    CustomMessageDialog cmdCanhBaoDiaChi = new CustomMessageDialog(this);
                    cmdCanhBaoDiaChi.showMessage("Cảnh báo địa chỉ!", "Dữ liệu địa chỉ chưa được nhập!\nVui lòng không để trống và nhập đúng cú pháp");
                    dungSai = false;
                    break;
                    
                default:
                    System.out.println(viTriTrong + boTrong);
                    System.out.println("Chạy vào khối default - trống");
                    dungSai = false;
                    break;
        }

        return dungSai;
    }
    
    public boolean kiemTraTTKhachRegex(String hoTen, String soDT, String soCMND, String diaChi) {
        boolean dungSai = true;
        int viTriLoi = 0;
        int soLoi = 0;
        
        String regexHoTen = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*";
        String regexSDT = "^(032|033|034|035|036|037|038|039|086|096|097|098|" +
                "070|079|077|076|078|089|090|093|" +
                "083|084|085|081|082|088|091|094|" +
                "056|058|092|" +
                "059|099)[0-9]{7}$";
        String regexSoCMND = "[0-9]{9}|[0-9]{12}";
        
        if (hoTen.matches(regexHoTen) == false) {
            viTriLoi = 1;
            soLoi++;
        }
        if (soDT.matches(regexSDT) == false) {
            viTriLoi = 2;
            soLoi++;
        }
        if (soCMND.matches(regexSoCMND) == false) {
            viTriLoi = 3;
            soLoi++;
        }
        
        if (soLoi == 0) {
            return dungSai;
        }
        
        if (soLoi > 1) {
            CustomMessageDialog cmdThanhToanTraPhongRegex = new CustomMessageDialog(this);
            cmdThanhToanTraPhongRegex.showMessage("Cảnh báo cú pháp dữ liệu!", "Còn nhiều trường chưa điền sai cú pháp!\nVui lòng không để trống và nhập đúng cú pháp");
            dungSai = false;
            return dungSai;
        }
        
        switch(viTriLoi) {
                case 1:
                    CustomMessageDialog cmdCanhBaoHoTenRegex = new CustomMessageDialog(this);
                    cmdCanhBaoHoTenRegex.showMessage("Cảnh báo cú pháp tên khách hàng!", "Dữ liệu tên khách hàng sai cú pháp!\nCú pháp (Chữ và viết hoa chữ cái đầu)");
                    dungSai = false;
                    break;
                case 2:
                    CustomMessageDialog cmdCanhBaoSoDTRegex = new CustomMessageDialog(this);
                    cmdCanhBaoSoDTRegex.showMessage("Cảnh báo cú pháp số điện thoại!", "Dữ liệu số điện thoại sai cú pháp!\nCú pháp (Đầu số VN + 7 số sau)");
                    dungSai = false;
                    break;
                case 3:
                    CustomMessageDialog cmdCanhBaoSoCMNDRegex = new CustomMessageDialog(this);
                    cmdCanhBaoSoCMNDRegex.showMessage("Cảnh báo cú pháp số CMND!", "Dữ liệu số CMND sai cú pháp!\nCú pháp (9 hoặc 12 số)");
                    dungSai = false;
                    break;
                    
                default:
                    System.out.println("Chạy vào khối default - regex");
                    dungSai = false;
                    break;
        }
        
        return dungSai;
    }
}