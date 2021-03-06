package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.toedter.calendar.JDateChooser;

//import dao.CThdDao;
//import dao.hdDao;
//import dao.khDao;
import dao.SanPhamDao;
import entity.CTHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;
import impl.CTHoaDonImp;
import impl.HoaDonImp;
import impl.KhachHangImp;
import net.java.balloontip.BalloonTip;

public class CapNhatHoaDonDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private CustomTable tableCTHD;
	private DefaultTableModel modelCTHD;
	private ColoredButton btnXoaCTHD;
	private ColoredButton btnSuaCTHD;
	private ColoredButton btnThemSanPham;
	private JTextField txtMaHoaDon;
	private JDateChooser dateNgayLap;
	private ColoredButton btnChonKhachHang;
	private ColoredButton btnIn;
	private ColoredButton btnLuu;
	private JTextField txtSoLuong;
	private JTextField txtDonGia;
	private ColoredButton btnQuayLai;
	protected ChonKhachHangDialog chonKhachHangDialog;
	private ColoredButton btnXoaRong;
	private JTextField txtMaKhachHang;
	private JTextField txtTenKhachHang;
	private JTextField txtTongTien;

	private JTextField txtMaSanPham;
	private JTextField txtTenSanPham;
	protected chonSanPhamDialog chonSanPhamDialog;
	private List<CTHoaDon> dsCTHD;
	private HoaDonImp hdDao;
	private KhachHang khachHang;
	private HoaDon hoaDon;
	private CTHoaDonImp cthdDao;
	NumberFormat nf = NumberFormat.getInstance(Locale.US);
	private JTextField txtTienKhachDua;
	private JTextField txtTienTra;
	private double tongTien = 0;
	private JComboBox<String> txtGioiTinh;
	private JTextField txtDC;
	private JTextField txtEmail;
	private JTextField txtSDT;
	private BalloonTip ballSoLuong;
	private BalloonTip ballDG;
	private JPanel pnlRight;
	private double tientra;
	private JDateChooser txtNgaySinh;
	// them
	private BalloonTip ballTen;
	private BalloonTip ballNS;
	private BalloonTip ballDC;
	private BalloonTip ballEmail;
	private BalloonTip ballSDT;
	private KhachHangImp khDao;
	private List<KhachHang> dsKH;
	private BalloonTip balltienkhachdua;

	public CapNhatHoaDonDialog(Container owner) throws Exception {
		super(getFrame(owner));
		System.out.println("hd1");

		setSize(owner.getSize());
		setTitle("L???p h??a ????n");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("img/hoadon.png").getImage());
		setLayout(new BorderLayout());
		// setLookAndFeel();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setEnabled(true);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				frame.setEnabled(false);
			}
		});
		getContentPane().setBackground(Color.white);

		setBackground(Color.white);

		JPanel pnlMain;
		add(pnlMain = new JPanel(new BorderLayout()));
		pnlMain.setOpaque(false);

		add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		add(Box.createHorizontalStrut(10), BorderLayout.WEST);

		JPanel pnlNorth;
		pnlMain.add(pnlNorth = new JPanel(new BorderLayout()), BorderLayout.NORTH);
		pnlNorth.setOpaque(false);

		addNorth(pnlNorth);

		JPanel pnlEast;
		pnlMain.add(pnlEast = new JPanel(new BorderLayout()), BorderLayout.EAST);
		pnlEast.setOpaque(false);
		pnlEast.setPreferredSize(new Dimension(300, 200));

		addEast(pnlEast);

		JPanel pnlCenter;
		pnlMain.add(pnlCenter = new JPanel(new BorderLayout()));
		pnlCenter.setOpaque(false);
		pnlCenter.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstant.LINE_COLOR));

		addCenter(pnlCenter);

		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setOpaque(false);

		addSouth(pnlSouth);

		add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		addEvent();

//		System.out.println("b2");
//		hoaDon = new HoaDon();
//		System.out.println("b3");
//		hoaDon.setNhanVienLap(MainFrame.getNhanVien());
//		System.out.println("b5");
//		hoaDon.setNgayLap(LocalDate.now());
//		System.out.println("b6");
//		
//		try {
//			System.out.println("dhd"+hdDao.phatSinhMaTuDong());
//			hoaDon.setMaHoaDon(hdDao.phatSinhMaTuDong());
//		} catch (RemoteException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println("b4");

		

		hdDao = (HoaDonImp) Naming.lookup(Port.PORT + "hdDao");
		cthdDao = (CTHoaDonImp) Naming.lookup(Port.PORT + "cthdDao");
		khDao = (KhachHangImp) Naming.lookup(Port.PORT + "khDao");
		System.out.println(hdDao.phatSinhMaTuDong());
		String mahd = "HD000002";
		String a = fomatAA000000(mahd);
		System.out.println(a);
		
		
		hoaDon = new HoaDon();

		hoaDon.setNhanVienLap(MainFrame.getNhanVien());

		hoaDon.setNgayLap(LocalDate.now());

		System.out.println("dhd" + hdDao.phatSinhMaTuDong());
		hoaDon.setMaHoaDon(hdDao.phatSinhMaTuDong());
	}

	public String fomatAA000000(String s) throws RemoteException {
		int so = Integer.parseInt(s.substring(2));
		String stringSo = "000000";
		String ma1 = s.substring(0, 1);
		String ma2 = s.substring(1, 2);

		if (so >= 999999) {
			if (ma2.equalsIgnoreCase("Z")) {
				if (!ma1.equalsIgnoreCase("Z")) {
					char a = (char) ((int) ma1.charAt(0) + 1);
					ma1 = String.valueOf(a);
					ma2 = "A";
				} else {
					System.err.println("Da toi gioi han ma");
					return null;
				}
			} else {
				// chua dat toi gioi han
				char a = (char) ((int) ma2.charAt(0) + 1);
				ma2 = String.valueOf(a);
			}
		} else {
			stringSo = String.format("%06d", so + 1);
		}
		return (ma1 + ma2 + stringSo);
	}

	public void setHoaDon(HoaDon hoaDon) throws Exception {
		this.hoaDon = hoaDon;
		txtMaHoaDon.setText(hoaDon.getMaHoaDon());

		try {
			dateNgayLap.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(hoaDon.getNgayLap().toString()));

		} catch (ParseException e) {
		}

		if (hoaDon.getKhachHang() != null) {
			txtMaKhachHang.setText(hoaDon.getKhachHang().getMaKhachHang());
			txtTenKhachHang.setText(hoaDon.getKhachHang().getTenKhachHang());
			LocalDate localDate2 = hoaDon.getKhachHang().getNgaySinh();// For reference
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedString2 = localDate2.format(formatter2);
			Date date;
			try {

				date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
				txtNgaySinh.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String gt = "";
			if (hoaDon.getKhachHang().isGioiTinh() == true)
				gt = "Nam";
			else
				gt = "N???";
			txtGioiTinh.setSelectedItem(gt);
			txtDC.setText(hoaDon.getKhachHang().getDiaChi());
			txtEmail.setText(hoaDon.getKhachHang().getEmail());
			txtSDT.setText(hoaDon.getKhachHang().getSoDienThoai());
		}

		dsCTHD = cthdDao.getDsCTHD(hoaDon);
		// b???t d???u ch???m
		// NumberFormat nf = NumberFormat.getInstance(Locale.US);
		// nf.setMinimumIntegerDigits(2);
		// nf.setMaximumFractionDigits(2);
		double tongTien = 0;

		for (CTHoaDon item : dsCTHD) {
			tongTien += item.tinhThanhTien();
			modelCTHD.addRow(new Object[] { item.getSanPham().getTenSanPham(), item.getSoLuong(), item.getDonGia(),
					item.tinhThanhTien() });
		}

		txtTongTien.setText(nf.format(tongTien) + " VND");

		this.setTitle("Xem th??ng tin h??a ????n");
		System.out.println(hoaDon);
		pnlRight.setVisible(false);
		btnChonKhachHang.setVisible(false);
		btnChonKhachHang.setEnabled(true);
		btnLuu.setVisible(false);
		btnIn.setVisible(true);
		btnIn.setEnabled(true);
		btnXoaCTHD.setVisible(false);
		btnSuaCTHD.setVisible(false);

		this.setVisible(true);
	}

	private void addSouth(JPanel pnlSouth) {
		btnQuayLai = new ColoredButton("Quay l???i", new ImageIcon("img/back.png"));
		btnQuayLai.setBackground(UIConstant.PRIMARY_COLOR);
		btnQuayLai.setBorderRadius(30);

		Box boxButtonCTHD;
		pnlSouth.add(boxButtonCTHD = Box.createHorizontalBox());

		boxButtonCTHD.add(Box.createHorizontalStrut(1100));

		boxButtonCTHD.add(btnQuayLai);
		boxButtonCTHD.add(Box.createHorizontalGlue());

		pnlSouth.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
		pnlSouth.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);
	}

	private void addCenter(JPanel pnlCenter) {
		JPanel pnlCTHD = new JPanel();
		pnlCTHD.setOpaque(false);
		pnlCTHD.setLayout(new BoxLayout(pnlCTHD, BoxLayout.X_AXIS));

		JPanel pnlLeft = new JPanel();
		pnlLeft.setBorder(BorderFactory.createLineBorder(UIConstant.LINE_COLOR, 1));
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
		pnlLeft.setOpaque(false);

		pnlRight = new JPanel(new BorderLayout());
		pnlRight.setOpaque(false);
		pnlRight.setBorder(BorderFactory.createLineBorder(UIConstant.LINE_COLOR, 1));

		pnlCTHD.add(pnlLeft);
		pnlCTHD.add(Box.createHorizontalStrut(10));
		pnlCTHD.add(pnlRight);

		pnlCenter.add(pnlCTHD, BorderLayout.NORTH);

		JLabel lbMaSanPham = new JLabel("M?? s???n ph???m");
		JLabel lbTenSanPham = new JLabel("T??n s???n ph???m");
		JLabel lbSL = new JLabel("S??? l?????ng");
		JLabel lbDG = new JLabel("????n gi??");

		lbMaSanPham.setPreferredSize(new Dimension(83, 20));
		lbMaSanPham.setFont(UIConstant.NORMAL_FONT);
		lbTenSanPham.setPreferredSize(new Dimension(83, 20));
		lbTenSanPham.setFont(UIConstant.NORMAL_FONT);
		lbSL.setPreferredSize(new Dimension(83, 20));
		lbSL.setFont(UIConstant.NORMAL_FONT);
		lbDG.setPreferredSize(new Dimension(83, 20));
		lbDG.setFont(UIConstant.NORMAL_FONT);

		txtMaSanPham = new JTextField();
		txtMaSanPham.setEnabled(false);
		txtTenSanPham = new JTextField();
		txtTenSanPham.setEnabled(false);
		txtSoLuong = new JTextField();
		txtDonGia = new JTextField();
		txtDonGia.setEnabled(false);

		btnThemSanPham = new ColoredButton("Th??m s???n ph???m v??o h??a ????n", new ImageIcon("img/add.png"));
		btnThemSanPham.setBackground(UIConstant.PRIMARY_COLOR);
		btnThemSanPham.setToolTipText("Th??m m???t chi ti???t m???i v??o h??a ????n");
		btnThemSanPham.setBorderRadius(30);

		ballSoLuong = new BalloonTip(txtSoLuong, "S??? l?????ng ph???i l?? s??? nguy??n l???n h??n 0!");
		ballSoLuong.setVisible(false);
		ballSoLuong.setCloseButton(null);
		// ballDG = new BalloonTip(txtDonGia, "????n gi?? ph???i l?? s??? l???n h??n 0!");
		// ballDG.setVisible(false); ballDG.setCloseButton(null);

		Box boxMaTenSanPham = Box.createHorizontalBox();
		Box boxDVSLDG = Box.createHorizontalBox();

		pnlLeft.add(Box.createVerticalStrut(5));
		pnlLeft.add(boxMaTenSanPham);
		pnlLeft.add(Box.createVerticalStrut(5));
		pnlLeft.add(boxDVSLDG);
		pnlLeft.add(Box.createVerticalStrut(5));

		boxMaTenSanPham.add(Box.createHorizontalStrut(5));
		boxMaTenSanPham.add(lbMaSanPham);
		boxMaTenSanPham.add(txtMaSanPham);
		boxMaTenSanPham.add(Box.createHorizontalStrut(5));
		boxMaTenSanPham.add(lbTenSanPham);
		boxMaTenSanPham.add(txtTenSanPham);
		boxMaTenSanPham.add(Box.createHorizontalStrut(5));

		boxDVSLDG.add(Box.createHorizontalStrut(5));

		boxDVSLDG.add(lbSL);
		boxDVSLDG.add(txtSoLuong);
		boxDVSLDG.add(Box.createHorizontalStrut(5));
		boxDVSLDG.add(lbDG);
		boxDVSLDG.add(txtDonGia);
		boxDVSLDG.add(Box.createHorizontalStrut(5));

		Box boxThem = Box.createHorizontalBox();
		boxThem.add(Box.createHorizontalGlue());
		boxThem.add(Box.createHorizontalStrut(5));
		boxThem.add(btnThemSanPham);
		boxThem.add(Box.createHorizontalStrut(5));
		boxThem.add(Box.createHorizontalGlue());

		Box boxRight = Box.createVerticalBox();
		pnlRight.add(boxRight, BorderLayout.NORTH);
		pnlRight.setMaximumSize(new Dimension(200, 60));
		pnlRight.setPreferredSize(new Dimension(250, 60));

		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(boxThem);

		tableCTHD = new CustomTable();
		tableCTHD.setModel(
				modelCTHD = new DefaultTableModel(new String[] { "S???n Ph???m", "S??? l?????ng", "????n gi??", "Th??nh ti???n" }, 0));
		tableCTHD.setFont(UIConstant.NORMAL_FONT);

		JTabbedPane tabPaneCTHD = new JTabbedPane();
		tabPaneCTHD.setOpaque(false);
		pnlCenter.add(tabPaneCTHD, BorderLayout.CENTER);

		JScrollPane cthdScroll;
		tabPaneCTHD.addTab("Chi ti???t h??a ????n", cthdScroll = new JScrollPane(tableCTHD));
		cthdScroll.getViewport().setBackground(Color.white);
		cthdScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		btnXoaCTHD = new ColoredButton("Xo?? chi ti???t", new ImageIcon("img/delete.png"));
		btnXoaCTHD.setBackground(UIConstant.DANGER_COLOR);
		btnXoaCTHD.setToolTipText("Xo?? s???n ph???m ???? ch???n trong b???ng");
		btnXoaCTHD.setBorderRadius(30);
		btnSuaCTHD = new ColoredButton("S???a chi ti???t", new ImageIcon("img/modify.png"));
		btnSuaCTHD.setBackground(UIConstant.WARNING_COLOR);
		btnSuaCTHD.setToolTipText("S???a chi ti???t h??a ????n ???? ch???n trong b???ng v???i th??ng tin ph??a tr??n");
		btnSuaCTHD.setBorderRadius(30);
		btnXoaRong = new ColoredButton("L??m m???i", new ImageIcon("img/refresh3.png"));
		btnXoaRong.setBackground(new Color(0x019474D));
		btnXoaRong.setToolTipText("L??m r???ng to??n b???");
		btnXoaRong.setBorderRadius(30);

		Box box = Box.createVerticalBox();
		pnlCenter.add(box, BorderLayout.SOUTH);

		Box boxButton = Box.createHorizontalBox();
		box.add(Box.createVerticalStrut(5));
		box.add(boxButton);
		box.add(Box.createVerticalStrut(5));

		boxButton.add(Box.createHorizontalStrut(5));
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnSuaCTHD);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnXoaCTHD);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnXoaRong);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(Box.createHorizontalStrut(5));

		JLabel lbTongTien = new JLabel("T???ng ti???n");
		lbTongTien.setFont(UIConstant.NORMAL_FONT);
		lbTongTien.setHorizontalAlignment(SwingConstants.RIGHT);

		txtTongTien = new JTextField("0");
		txtTongTien.setEnabled(false);
		txtTongTien.setFont(UIConstant.NORMAL_FONT);
		txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);

		Box boxTongTien = Box.createHorizontalBox();
		boxTongTien.add(Box.createHorizontalStrut(10));
		boxTongTien.add(lbTongTien);
		boxTongTien.add(Box.createHorizontalStrut(5));
		boxTongTien.add(txtTongTien);
		boxTongTien.add(Box.createHorizontalStrut(5));

		box.add(Box.createVerticalStrut(5));
		box.add(boxTongTien);
		box.add(Box.createVerticalStrut(10));
	}

	private void addEast(JPanel pnlEast) {
		pnlEast.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
		pnlEast.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
		pnlEast.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(0x9C9C9C)));

		JLabel lbKH = new JLabel("Kh??ch h??ng");
		JLabel lbMaKH = new JLabel("M?? kh??ch h??ng");
		JLabel lbTenKH = new JLabel("T??n kh??ch h??ng");
		// them
		JLabel lbNgaySinh = new JLabel("Ng??y sinh");
		JLabel lbGioiTinh = new JLabel("Gi???i t??nh");
		JLabel lbDC = new JLabel("?????a ch???");
		// them
		JLabel lbEmail = new JLabel("Email");
		JLabel lbSDT = new JLabel("S??T");

		lbKH.setPreferredSize(new Dimension(100, 20));
		lbKH.setFont(UIConstant.NORMAL_FONT);
		lbMaKH.setPreferredSize(new Dimension(100, 20));
		lbMaKH.setFont(UIConstant.NORMAL_FONT);
		lbTenKH.setPreferredSize(new Dimension(100, 20));
		lbTenKH.setFont(UIConstant.NORMAL_FONT);
		// them
		lbNgaySinh.setPreferredSize(new Dimension(100, 20));
		lbTenKH.setFont(UIConstant.NORMAL_FONT);
		lbGioiTinh.setPreferredSize(new Dimension(100, 20));
		lbGioiTinh.setFont(UIConstant.NORMAL_FONT);
		lbDC.setPreferredSize(new Dimension(100, 20));
		lbDC.setFont(UIConstant.NORMAL_FONT);
		// them
		lbEmail.setPreferredSize(new Dimension(100, 20));
		lbSDT.setFont(UIConstant.NORMAL_FONT);
		lbSDT.setPreferredSize(new Dimension(100, 20));
		lbSDT.setFont(UIConstant.NORMAL_FONT);

		txtMaKhachHang = new JTextField();
		txtMaKhachHang.setEnabled(false);
		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setEnabled(false);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("dd-MM-yyyy");
		txtNgaySinh.setEnabled(false);
		txtGioiTinh = new JComboBox<String>();
		txtGioiTinh.setFont(UIConstant.NORMAL_FONT);
		txtGioiTinh.setPreferredSize(new Dimension(100, 20));
		txtGioiTinh.addItem("Nam");
		txtGioiTinh.addItem("N???");
		txtGioiTinh.setEnabled(false);
		txtDC = new JTextField();
		txtDC.setEnabled(false);
		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		txtSDT = new JTextField();
		txtSDT.setEnabled(false);
		// them
		ballTen = new BalloonTip(txtTenKhachHang, "");
		ballTen.setVisible(false);
		ballTen.setCloseButton(null);
		ballNS = new BalloonTip(txtNgaySinh, "");
		ballNS.setVisible(false);
		ballNS.setCloseButton(null);
		ballDC = new BalloonTip(txtDC, "");
		ballDC.setVisible(false);
		ballDC.setCloseButton(null);
		ballEmail = new BalloonTip(txtEmail, "");
		ballEmail.setVisible(false);
		ballEmail.setCloseButton(null);
		ballSDT = new BalloonTip(txtSDT, "");
		ballSDT.setVisible(false);
		ballSDT.setCloseButton(null);
		btnChonKhachHang = new ColoredButton("Ch???n kh??ch h??ng", new ImageIcon("img/add.png"));
		btnChonKhachHang.setBackground(UIConstant.PRIMARY_COLOR);
		btnChonKhachHang.setBorderRadius(30);
		btnChonKhachHang.setEnabled(false);
		btnIn = new ColoredButton("In h??a ????n", new ImageIcon("img/inhoadon.png"));
		btnIn.setBackground(UIConstant.WARNING_COLOR);
		btnIn.setEnabled(false);
		btnIn.setBorderRadius(30);

		btnLuu = new ColoredButton("L??u h??a ????n", new ImageIcon("img/save.png"));
		btnLuu.setBackground(UIConstant.PRIMARY_COLOR);
		btnLuu.setBorderRadius(30);
		JPanel pnlHoaDon = new JPanel();
		pnlHoaDon.setOpaque(false);

		pnlEast.add(pnlHoaDon, BorderLayout.NORTH);

		pnlHoaDon.setLayout(new BoxLayout(pnlHoaDon, BoxLayout.Y_AXIS));

		Box boxLoai = Box.createHorizontalBox();
		Box boxMa = Box.createHorizontalBox();
		Box boxKH = Box.createHorizontalBox();
		Box boxMaKH = Box.createHorizontalBox();
		Box boxTenKH = Box.createHorizontalBox();
		Box boxNS = Box.createHorizontalBox();
		Box boxGioiTinh = Box.createHorizontalBox();
		Box boxDiaChi = Box.createHorizontalBox();
		Box boxEmail = Box.createHorizontalBox();
		Box boxSDT = Box.createHorizontalBox();
		Box boxButton = Box.createHorizontalBox();
		Box boxTienKhachDua = Box.createHorizontalBox();
		Box boxTienTraKhach = Box.createHorizontalBox();

		pnlHoaDon.add(Box.createVerticalStrut(15));
		pnlHoaDon.add(boxLoai);
		pnlHoaDon.add(Box.createVerticalStrut(15));
		pnlHoaDon.add(boxMa);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxKH);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxMaKH);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxTenKH);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxNS);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxGioiTinh);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxDiaChi);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxEmail);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxSDT);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxTienKhachDua);
		pnlHoaDon.add(Box.createVerticalStrut(5));
		pnlHoaDon.add(boxTienTraKhach);
		pnlHoaDon.add(Box.createVerticalStrut(20));
		pnlHoaDon.add(boxButton);
		pnlHoaDon.add(Box.createVerticalStrut(5));

		boxKH.add(Box.createHorizontalStrut(5));
		boxKH.add(lbKH);
		boxKH.add(Box.createHorizontalGlue());
		boxKH.add(btnChonKhachHang);
		boxKH.add(Box.createHorizontalStrut(5));

		boxMaKH.add(Box.createHorizontalStrut(15));
		boxMaKH.add(lbMaKH);
		boxMaKH.add(txtMaKhachHang);
		boxMaKH.add(Box.createHorizontalStrut(5));

		boxTenKH.add(Box.createHorizontalStrut(15));
		boxTenKH.add(lbTenKH);
		boxTenKH.add(txtTenKhachHang);
		boxTenKH.add(Box.createHorizontalStrut(5));

		boxNS.add(Box.createHorizontalStrut(15));
		boxNS.add(lbNgaySinh);
		boxNS.add(txtNgaySinh);
		boxNS.add(Box.createHorizontalStrut(5));

		boxGioiTinh.add(Box.createHorizontalStrut(15));
		boxGioiTinh.add(lbGioiTinh);
		boxGioiTinh.add(txtGioiTinh);
		boxGioiTinh.add(Box.createHorizontalStrut(5));

		boxDiaChi.add(Box.createHorizontalStrut(15));
		boxDiaChi.add(lbDC);
		boxDiaChi.add(txtDC);
		boxDiaChi.add(Box.createHorizontalStrut(5));

		boxEmail.add(Box.createHorizontalStrut(15));
		boxEmail.add(lbEmail);
		boxEmail.add(txtEmail);
		boxEmail.add(Box.createHorizontalStrut(5));

		boxSDT.add(Box.createHorizontalStrut(15));
		boxSDT.add(lbSDT);
		boxSDT.add(txtSDT);
		boxSDT.add(Box.createHorizontalStrut(5));

		boxButton.add(Box.createHorizontalStrut(5));
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnLuu);
		boxButton.add(Box.createHorizontalStrut(5));
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnIn);
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(Box.createHorizontalStrut(5));

		JLabel lbTienKhachDua = new JLabel("Ti???n kh??ch ????a");
		lbTienKhachDua.setFont(UIConstant.NORMAL_FONT);
		JLabel lbTienTra = new JLabel("Ti???n tr??? kh??ch");
		lbTienTra.setFont(UIConstant.NORMAL_FONT);

		lbTienTra.setPreferredSize(lbTienKhachDua.getPreferredSize());

		txtTienKhachDua = new JTextField();
		txtTienKhachDua.setEnabled(false);
		balltienkhachdua = new BalloonTip(txtTienKhachDua, "S??? l?????ng ph???i l?? s??? nguy??n l???n h??n 0!");
		balltienkhachdua.setVisible(false);
		balltienkhachdua.setCloseButton(null);
		txtTienKhachDua.setFont(UIConstant.NORMAL_FONT);
		txtTienTra = new JTextField();
		txtTienTra.setEnabled(false);
		txtTienTra.setFont(UIConstant.NORMAL_FONT);

		boxTienKhachDua.add(Box.createHorizontalStrut(10));
		boxTienKhachDua.add(lbTienKhachDua);
		boxTienKhachDua.add(Box.createHorizontalStrut(5));
		boxTienKhachDua.add(txtTienKhachDua);
		boxTienKhachDua.add(Box.createHorizontalStrut(10));

		boxTienTraKhach.add(Box.createHorizontalStrut(10));
		boxTienTraKhach.add(lbTienTra);
		boxTienTraKhach.add(Box.createHorizontalStrut(5));
		boxTienTraKhach.add(txtTienTra);
		boxTienTraKhach.add(Box.createHorizontalStrut(10));
	}

	private void addNorth(JPanel pnlNorth) {
		JPanel pnlHD = new JPanel();
		pnlHD.setOpaque(false);
		pnlHD.setLayout(new BoxLayout(pnlHD, BoxLayout.Y_AXIS));
		pnlHD.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, UIConstant.LINE_COLOR));

		pnlNorth.add(pnlHD, BorderLayout.NORTH);

		JLabel lbMaHD = new JLabel("M?? h??a ????n");

		lbMaHD.setPreferredSize(new Dimension(100, 20));
		lbMaHD.setFont(UIConstant.NORMAL_FONT);
		JLabel lbNgayLap = new JLabel("Ng??y l???p");
		lbNgayLap.setPreferredSize(new Dimension(100, 20));
		lbNgayLap.setFont(UIConstant.NORMAL_FONT);

		dateNgayLap = new JDateChooser();
		dateNgayLap.setDate(new Date());
		dateNgayLap.setDateFormatString("dd-MM-yyyy");
		dateNgayLap.setEnabled(false);

		txtMaHoaDon = new JTextField(30);
		txtMaHoaDon.setEnabled(false);
		txtMaHoaDon.setToolTipText("M?? h??a ????n ???????c t??? ?????ng kh???i t???o.");

		Box boxHD = Box.createHorizontalBox();

		pnlHD.add(Box.createVerticalStrut(10));
		pnlHD.add(boxHD);
		pnlHD.add(Box.createVerticalStrut(10));

		boxHD.add(Box.createHorizontalStrut(5));
		boxHD.add(lbMaHD);
		boxHD.add(txtMaHoaDon);
		boxHD.add(Box.createHorizontalStrut(5));

		boxHD.add(Box.createHorizontalStrut(5));
		boxHD.add(lbNgayLap);
		boxHD.add(dateNgayLap);
		boxHD.add(Box.createHorizontalStrut(5));
	}

	private void addEvent() {
		txtTienKhachDua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					double tienDua = Double.parseDouble(txtTienKhachDua.getText());
					tientra = tienDua - tongTien;
					if (tienDua > 0) {
						// txtTienTra.setText(nf.format(tienDua - tongTien) + " VND");
						txtTienTra.setText(nf.format(tientra) + " VND");
					} else {
						UIConstant.showWarning(CapNhatHoaDonDialog.this, "Vui l??ng nh???p s??? ti???n l???n h??n 0");
						return;
					}
					balltienkhachdua.setVisible(false);
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					txtTienTra.setText("");
					if (txtTienKhachDua.getText().equals("")) {
						balltienkhachdua.setVisible(false);
					} else {
						balltienkhachdua.setTextContents("Ti???n kh??ch ????a l?? s???");
						balltienkhachdua.setVisible(true);
						return;
					}

					// if(txtTienKhachDua.getText().equals(""))
					// {
					// balltienkhachdua.setVisible(false);
					// }
				}
			}
		});
		// sua
		btnIn.addActionListener(e -> {

			try {
				new PrintPreview(CapNhatHoaDonDialog.this, hoaDon).setVisible(true);
				System.out.println("ss" + hoaDon);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		btnChonKhachHang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chonKhachHangDialog == null)
					try {
						chonKhachHangDialog = new ChonKhachHangDialog(CapNhatHoaDonDialog.this);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				chonKhachHangDialog.setVisible(true);
			}
		});

		btnThemSanPham.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (chonSanPhamDialog == null)
					try {
						chonSanPhamDialog = new chonSanPhamDialog(CapNhatHoaDonDialog.this);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				chonSanPhamDialog.setVisible(true);
				// them
				btnChonKhachHang.setEnabled(true);
				txtTenKhachHang.setEnabled(true);
				
				txtTienKhachDua.setEnabled(true);
			}
		});
		txtTenKhachHang.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtNgaySinh.setEnabled(true);
				txtGioiTinh.setEnabled(true);
				txtDC.setEnabled(true);
				txtEmail.setEnabled(true);
				txtSDT.setEnabled(true);
			}
		});

		btnQuayLai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CapNhatHoaDonDialog.this.dispose();
				frame.setEnabled(true);
				frame.setVisible(true);
			}
		});

		btnLuu.addActionListener(new ActionListener() {

			private KhachHang khachhang1;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (dsCTHD == null || dsCTHD.size() == 0) {
						UIConstant.showWarning(CapNhatHoaDonDialog.this, "Kh??ng c?? s???n ph???m n??o trong h??a ????n!");
						return;
					}
					String tkd = txtTienKhachDua.getText().trim();

					if (tkd.isEmpty()) {
						UIConstant.showWarning(CapNhatHoaDonDialog.this, "Ch??a nh???p ti???n kh??ch ????a");
						return;
					}
					// them
					// double tam =Double.parseDouble(txtTienTra.getText());
					if (tientra < 0) {
						UIConstant.showWarning(CapNhatHoaDonDialog.this, "Ti???n Kh??ch ????a c??n thi???u");
						return;
					}

					if (txtMaKhachHang.getText().equals("") && (!txtTenKhachHang.getText().equals(""))) {
						if (validData()) {
							LocalDate ngaySinh = null;
							Date date = txtNgaySinh.getDate();
							if (date != null) {
								SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
								String str_date = format1.format(date);

								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
								// convert String to LocalDate
								ngaySinh = LocalDate.parse(str_date, formatter);
							}
							LocalDate now = LocalDate.now();
							Period tuoi = Period.between(ngaySinh, now);
							String ten = txtTenKhachHang.getText().trim();
							String diachi = txtDC.getText().trim();
							String sdt = txtSDT.getText().trim();
							String email = txtEmail.getText().trim();
							boolean gt = txtGioiTinh.getSelectedItem().toString().equals("Nam") ? true : false;
							 khachHang = new KhachHang(ten, ngaySinh, diachi, gt, email, sdt);
							if (tuoi.getYears() < 10) {
								UIConstant.showInfo(CapNhatHoaDonDialog.this, "Kh??ng nh???p kh??ch h??ng nh??? h??n 10 tu???i");
								return;
							}
						
								if (kiemtraTrungSDT() == false) {
									UIConstant.showWarning(CapNhatHoaDonDialog.this, "S??? ??i???n tho???i ???? ???????c s??? d???ng!");
									txtSDT.requestFocus();
									txtSDT.selectAll();
									return;
								} else if (kiemtraTrungEmail() == false) {
									UIConstant.showWarning(CapNhatHoaDonDialog.this,
											"Nh?? cung c???p c?? s??? fax " + txtEmail.getText() + " ???? ???????c s??? d???ng");
									System.out.println("3");
									txtEmail.requestFocus();
									txtEmail.selectAll();
								}
					
							khDao.addKhachHang(khachHang);
							hoaDon.setKhachHang(khachHang);
						} else {
							UIConstant.showWarning(CapNhatHoaDonDialog.this, "Th??ng tin kh??ch h??ng ch??a ????ng!");
							return;
						}

					}

					else if (txtTenKhachHang.getText().equals("") && (!txtDC.getText().equals(""))
							&& (!txtSDT.getText().equals(""))) {
						UIConstant.showWarning(CapNhatHoaDonDialog.this, "Vui l??ng nh???p t??n kh??ch h??ng!");
						return;
					}
						if (hdDao.lapHoaDon(hoaDon)) {
							for (CTHoaDon item : dsCTHD) {
								cthdDao.themCTHoaDon(item);
							}
							UIConstant.showInfo(CapNhatHoaDonDialog.this, "L???p h??a ????n th??nh c??ng!");
							btnIn.setEnabled(true);

						} else {
							UIConstant.showInfo(CapNhatHoaDonDialog.this, "L???p h??a ????n kh??ng th??nh c??ng!");
						}
				} catch (Exception e2) {
					// TODO: handle exception
				}

				
			}
		});

		btnSuaCTHD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableCTHD.getSelectedRow();

				if (row == -1) {
					UIConstant.showWarning(CapNhatHoaDonDialog.this, "Ch??a ch???n chi ti???t c???n s???a ?????i!");
					return;
				}

				String soLuong = txtSoLuong.getText();

				int sl = 0;
				try {

					sl = Integer.parseInt(soLuong);
					if (sl <= 0)
						throw new NumberFormatException();
					ballSoLuong.setVisible(false);
				} catch (NumberFormatException e1) {
					ballSoLuong.setTextContents("S??? l?????ng ph???i l?? s??? nguy??n l???n h??n 0");
					ballSoLuong.setVisible(true);
					return;
				}
				// s???a
				String donGia = txtDonGia.getText();

				double tam = Double.parseDouble(donGia);
				double dg = tam * sl;
				// try {
				//// dg = Double.parseDouble(donGia);
				// dg=tam*sl;
				// if(dg <= 0)
				// throw new NumberFormatException();
				// ballDG.setVisible(false);
				// }catch (NumberFormatException e1) {
				// ballDG.setVisible(true);
				// return;
				// }
				int slTon = dsCTHD.get(row).getSanPham().getSoLuong();

				if (sl > slTon) {
					ballSoLuong.setTextContents("S??? l?????ng kh??ng ?????, ch??? c??n " + slTon + "!");
					ballSoLuong.setVisible(true);
					return;
				}
				dsCTHD.get(row).setSoLuong(sl);
				dsCTHD.get(row).tinhThanhTien();
				modelCTHD.setValueAt(sl, row, 1);
				modelCTHD.setValueAt(dg, row, 3);

				tongTien = 0;

				for (CTHoaDon item : dsCTHD) {
					tongTien += item.tinhThanhTien();
				}
				txtTongTien.setText(nf.format(tongTien) + " VND");
				double tienDua = Double.parseDouble(txtTienKhachDua.getText());
				txtTienTra.setText(nf.format(tienDua - tongTien) + " VND");

				UIConstant.showInfo(CapNhatHoaDonDialog.this, "S???a th??nh c??ng!");
			}
		});
		// s???a
		tableCTHD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tableCTHD.getSelectedRow() == -1)
					return;

				int row = tableCTHD.getSelectedRow();

				nf.setMinimumIntegerDigits(2);
				nf.setMaximumFractionDigits(2);

				txtMaSanPham.setText(dsCTHD.get(row).getSanPham().getMaSanPham());
				txtTenSanPham.setText(dsCTHD.get(row).getSanPham().getTenSanPham());
				txtSoLuong.setText(dsCTHD.get(row).getSoLuong() + "");
				txtDonGia.setText(dsCTHD.get(row).getDonGia() + "");

			}
		});

		btnXoaRong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtMaSanPham.setText("");
				txtTenSanPham.setText("");
				txtMaKhachHang.setText("");
				txtTenKhachHang.setText("");
				txtSoLuong.setText("");
				txtDonGia.setText("");
				txtTienTra.setText("");
				txtTienKhachDua.setText("");
				txtDC.setText("");
				txtSDT.setText("");
				txtEmail.setText("");
				txtNgaySinh.setCalendar(null);

				// ballDG.setVisible(false);
				ballSoLuong.setVisible(false);
				hoaDon.setKhachHang(null);
			}
		});

		btnXoaCTHD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableCTHD.getSelectedRow();

				if (row == -1) {
					UIConstant.showWarning(CapNhatHoaDonDialog.this, "Ch??a ch???n s???n ph???m trong chi ti???t c???n xo??!");
					return;
				}
				int selection = JOptionPane.showConfirmDialog(CapNhatHoaDonDialog.this, "X??c nh???n x??a chi ti???t n??y?",
						"X??c nh???n", JOptionPane.OK_CANCEL_OPTION);
				if (selection == JOptionPane.CANCEL_OPTION)
					return;

				dsCTHD.remove(row);
				modelCTHD.removeRow(row);
				tongTien = 0;

				for (CTHoaDon item : dsCTHD) {
					tongTien += item.tinhThanhTien();
				}
				txtTongTien.setText(nf.format(tongTien) + " VND");

			}
		});
		// them
		txtTenKhachHang.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtTenKhachHang.getText().trim().isEmpty()) {
					ballTen.setVisible(false);
				} else if (!txtTenKhachHang.getText().trim()
						.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
					ballTen.setTextContents(" + H??? v?? t??n kh??ch h??ng ph???i b???t ?????u ch??? c??i in hoa \n"
							+ " + Kh??ng ch???a c??c k?? t??? ?????c bi???t v?? s???");
					ballTen.setVisible(true);
				} else
					ballTen.setVisible(false);
			}
		});
		txtNgaySinh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ballNS.setVisible(false);
				Date date = txtNgaySinh.getDate();
				if (date != null) {
					LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (!ngayHopLe(LocalDate.now(), localDate)) {
						ballNS.setTextContents("Ng??y sinh ph???i tr?????c ng??y hi???n t???i");
						ballNS.setVisible(true);
					}
				}
			}
		});

		txtDC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ballDC.setVisible(false);
				// if (txtDC.getText().trim().isEmpty()) {
				// ballDC.setTextContents("?????a ch??? kh??ng ???????c r???ng");
				// ballDC.setVisible(false);
				// }
			}
		});

		txtSDT.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ballSDT.setVisible(false);
				if (txtSDT.getText().trim().isEmpty())
					ballSDT.setVisible(false);
				else if (!txtSDT.getText().trim().matches("[0]\\d{9}")) {
					ballSDT.setTextContents("S??? ??i???n tho???i g???m 10 ch??? s???, b???t ?????u l?? 0");
					ballSDT.setVisible(true);
				} else
					ballSDT.setVisible(false);
			}
		});
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ballEmail.setVisible(false);
				if (txtEmail.getText().trim().isEmpty())
					ballEmail.setVisible(false);
				else if (!txtEmail.getText().trim().isEmpty()) {
					if (!txtEmail.getText().trim()
							.matches("^[a-z][a-z0-9\\.]{7,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
						ballEmail.setTextContents(" + Email ph???i b???t ?????u b???ng 1 k?? t??? \n"
								+ "+ Ch??? ch???a k?? t??? a-z, 0-9 v?? k?? t??? d???u ch???m (.) \n"
								+ "+ ????? d??i t???i thi???u l?? 8, ????? d??i t???i ??a l?? 32 \n"
								+ " +T??n mi???n c?? th??? l?? t??n mi???n c???p 1 ho???c c???p 2");
						ballEmail.setVisible(true);
					}
				} else
					ballEmail.setVisible(false);
			}
		});

	}

	// s???a
	public void themChiTietHD(SanPham sanpham, int soLuong, double donGia) {

		if (dsCTHD == null)
			dsCTHD = new ArrayList<CTHoaDon>();

		CTHoaDon cthd = new CTHoaDon();
		cthd.setHoaDon(hoaDon);
		cthd.setSanPham(sanpham);
		cthd.setSoLuong(soLuong);
		cthd.setDonGia(donGia);
		if (dsCTHD.contains(cthd)) {
			dsCTHD.remove(cthd);
		}

		// hdDao tong = new hdDao();
		// tong.tinhTongTien(hoaDon);

		// tongTien += cthd.tinhThanhTien();
		// txtTongTien.setText(nf.format(tongTien) + " VND");
		String tam = "";
		for (CTHoaDon item : dsCTHD) {

			tam = item.getSanPham().getMaSanPham();
		}
		if (tam != sanpham.getMaSanPham()) {
			dsCTHD.add(cthd);
		} else {
			UIConstant.showError(CapNhatHoaDonDialog.this, "S???n ph???m ???? ???????c ch???n!!!");
			return;
		}

		tongTien += cthd.tinhThanhTien();
		txtTongTien.setText(nf.format(tongTien) + " VND");
		modelCTHD.addRow(
				new Object[] { sanpham.getTenSanPham(), soLuong, nf.format(donGia), nf.format(soLuong * donGia) });
	}

	// them
	public void setKhachHangDuocChon(KhachHang kh) {
		khachHang = kh;

		txtMaKhachHang.setText(kh.getMaKhachHang());
		txtTenKhachHang.setText(kh.getTenKhachHang());

		LocalDate localDate2 = kh.getNgaySinh();// For reference
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedString2 = localDate2.format(formatter2);
		Date date;
		try {

			date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
			txtNgaySinh.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String gt = "";
		if (kh.isGioiTinh() == true)
			gt = "Nam";
		else
			gt = "N???";
		txtGioiTinh.setSelectedItem(gt);

		txtDC.setText(kh.getDiaChi());
		txtEmail.setText(kh.getEmail());
		txtSDT.setText(kh.getSoDienThoai());

		hoaDon.setKhachHang(kh);

	}

	private static JFrame getFrame(Container owner) {
		while (!(owner.getParent() instanceof JFrame))
			owner = owner.getParent();

		frame = (JFrame) owner.getParent();

		return frame;
	}

	public void themKhachHangMoi() {
		this.dispose();
		((MainFrame) frame).setEnabled(true);
		((MainFrame) frame).setVisible(true);
		((MainFrame) frame).changeCenter(((MainFrame) frame).getQuanLyKhachHangPanel());
		((MainFrame) frame).getQuanLyKhachHangPanel().themKhachHangMoi();

	}

	// them
	private boolean ngayHopLe(LocalDate ngayHienTai, LocalDate ngaySinh) {
		if (ngaySinh.isBefore(ngayHienTai))
			return true;

		return false;
	}

	private boolean validData() {
		String diaChi = txtDC.getText().trim();
		String email = txtEmail.getText().trim();
		String sdt = txtSDT.getText().trim();
		String ten = txtTenKhachHang.getText().trim();
		Date date = txtNgaySinh.getDate();

		// t??n
		if (ten.isEmpty()) {
			ballTen.setTextContents("T??n kh??ch h??ng kh??ng ???????c r???ng");
			ballTen.setVisible(true);
			return false;
		} else if (!ten.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
			ballTen.setTextContents(" + H??? v?? t??n kh??ch h??ng ph???i b???t ?????u ch??? c??i in hoa \n"
					+ " + Kh??ng ch???a c??c k?? t??? ?????c bi???t v?? s???");
			ballTen.setVisible(true);
			return false;
		} else
			ballTen.setVisible(false);

		// ng??y
		if (date != null) {
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (!ngayHopLe(LocalDate.now(), localDate)) {
				ballNS.setTextContents("Ng??y sinh ph???i tr?????c ng??y hi???n t???i");
				ballNS.setVisible(true);
				return false;
			}
		}
		ballNS.setVisible(false);

		// s??? ??i???n tho???i
		if (!sdt.matches("[0]\\d{9}")) {
			ballSDT.setTextContents("S??? ??i???n tho???i g???m 10 ch??? s???, b???t ?????u l?? 0");
			ballSDT.setVisible(true);
			return false;
		}
		ballSDT.setVisible(false);

		// ??ia ch???
		if (diaChi.isEmpty()) {
			ballDC.setTextContents("?????a ch??? kh??ng ???????c r???ng");
			ballDC.setVisible(true);
			return false;
		}
		ballDC.setVisible(false);

		// email
		if (!email.isEmpty()) {
			if (!email.matches("^[a-z][a-z0-9\\.]{7,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
				ballEmail.setTextContents(
						" + Email ph???i b???t ?????u b???ng 1 k?? t??? \n" + "+ Ch??? ch???a k?? t??? a-z, 0-9 v?? k?? t??? d???u ch???m (.) \n"
								+ "+ ????? d??i t???i thi???u l?? 8, ????? d??i t???i ??a l?? 32 \n"
								+ " +T??n mi???n c?? th??? l?? t??n mi???n c???p 1 ho???c c???p 2");
				ballEmail.setVisible(true);
				return false;
			}
		}
		ballEmail.setVisible(false);
		return true;
	}

	private boolean kiemtraTrungSDT()  {
		List<KhachHang> list = new ArrayList<KhachHang>();

		try {
			list = khDao.timKhachHang("", "", "", txtSDT.getText());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (KhachHang kh : list) {
			if (list.contains(kh)) {

				return false;
			}
		}
		return true;
	}

	private boolean kiemtraTrungEmail()  {
		List<KhachHang> list = new ArrayList<KhachHang>();
		if (txtEmail.getText().equals(""))
			return true;
		try {
			list = khDao.timKhachHang("", "", txtEmail.getText().trim(), "");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (KhachHang kh : list) {
			if (list.contains(kh)) {

				return false;
			}
		}
		return true;

	}
}
