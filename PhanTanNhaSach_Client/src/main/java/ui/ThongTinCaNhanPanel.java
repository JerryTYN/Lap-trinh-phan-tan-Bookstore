package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.NhanVienDao;
//import dao.ThongTinCaNhanDAO;
import entity.NhanVien;
import impl.NhanVienImp;
import net.java.balloontip.BalloonTip;

public class ThongTinCaNhanPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static NhanVien nhanVien;
	private JPasswordField txtNewPasssWord;
	private JPasswordField txtOldPassWord;
	private JPasswordField txtConfirmPassWord;
	private ColoredButton btnHide;
	private ColoredButton btnChange;
	private ColoredButton btnHoanThongTin;
	private ColoredButton btnSua;
	private ColoredButton btnQuayLai;
	private ColoredButton btnXoa;
	private ColoredButton btnCapNhap;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private JDateChooser dateNgayVaoLam;
	private JDateChooser dateNgaySinh;
	private JComboBox<String> jcbGioiTinh;
	private ColoredButton btnTim;
	private NhanVienImp nvDao;
	private MainFrame mainFrame;

	private JTextField txtLoaiNV;
	private JTextField txtTrangThai;
	private ColoredButton btnXoaRong2;
	private ColoredButton btnXoaRong1;
	private BalloonTip ballNgaySinh;
	private BalloonTip ballTenNV;
	private BalloonTip ballNgayVaoLam;
	private BalloonTip ballEmail;
	private BalloonTip ballSoDienThoai;
//private ThongTinCaNhanDAO dao_tt = new ThongTinCaNhanDAO(); 
	private JTabbedPane tabPaneTT;

	public ThongTinCaNhanPanel(MainFrame mainFrame, NhanVien nhanVien) throws Exception {
		this.mainFrame = mainFrame;
		ThongTinCaNhanPanel.nhanVien = nhanVien;
		setOpaque(true);

//		setLookAndFeel();
		setLayout(new BorderLayout());

		addNorth();
		addCenter();
//		addSouth();
		addEvent();

		getAllComponents(this).forEach(item -> {
			item.addKeyListener(new KeyAdapter() {
				private boolean isCtrlPressed = false;

				@Override
				public void keyPressed(KeyEvent e) {
					if (isCtrlPressed) {
						// Nh???n ph??m N khi ??ang gi??? ph??m Ctrl
						if (e.getKeyCode() == KeyEvent.VK_N)
							btnHoanThongTin.doClick();
						else if (e.getKeyCode() == KeyEvent.VK_X)
							btnXoa.doClick();
						else if (e.getKeyCode() == KeyEvent.VK_LEFT)
							btnQuayLai.doClick();
						else if (e.getKeyCode() == KeyEvent.VK_E) {
							btnXoaRong1.doClick();
							btnXoaRong2.doClick();
						} else if (e.getKeyCode() == KeyEvent.VK_F)
							btnTim.doClick();

					} else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
						isCtrlPressed = true;
					else
						isCtrlPressed = false;
				}

				@Override
				public void keyReleased(KeyEvent e) {
					isCtrlPressed = false;
				}
			});
		});
		nvDao = (NhanVienImp) Naming.lookup(Port.PORT + "nvDao");
	}

	public static NhanVien getNhanVien() {
		return nhanVien;
	}

	private void addNorth() {
		Box boxLine1;
		boxLine1 = Box.createHorizontalBox();
		boxLine1.setPreferredSize(new Dimension(100, 100));
		JPanel pnlNorth = new JPanel();
		this.add(pnlNorth = new JPanel(new BorderLayout()), BorderLayout.NORTH);
		pnlNorth.setOpaque(false);

		JPanel pnlTitle = new JPanel();
		pnlTitle.setOpaque(false);
		pnlTitle.setPreferredSize(new Dimension(100, 100));
		JLabel lblHeader = new JLabel("Th??ng tin c?? nh??n");
		lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
		lblHeader.setHorizontalAlignment(JLabel.CENTER);

		lblHeader.setForeground(new Color(255,127,0));

		pnlTitle.add(boxLine1);
		pnlTitle.add(lblHeader);

		pnlNorth.add(pnlTitle, BorderLayout.NORTH);
	}

	private void addCenter() throws ParseException {

		JPanel pnlCenter = new JPanel();
		this.add(pnlCenter = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		pnlCenter.setOpaque(false);

		tabPaneTT = new JTabbedPane();
		tabPaneTT.setOpaque(false);
		tabPaneTT.setPreferredSize(new Dimension(100, 230));

		// Tab Th??ng tin
		Box boxLine1, boxLine2, boxLine3, boxLine4, boxLine5, boxLine6, boxLine7, boxLine8, boxLine9, boxThongTin;
		boxLine1 = Box.createHorizontalBox();
		boxLine2 = Box.createHorizontalBox();
		boxLine3 = Box.createHorizontalBox();
		boxLine4 = Box.createHorizontalBox();
		boxLine5 = Box.createHorizontalBox();
		boxLine6 = Box.createHorizontalBox();
		boxLine7 = Box.createHorizontalBox();
		boxLine8 = Box.createHorizontalBox();
		boxLine9 = Box.createHorizontalBox();
		boxThongTin = Box.createVerticalBox();

		JLabel lblMaNV = new JLabel("M?? nh??n vi??n:");
		JLabel lblTenNV = new JLabel("T??n nh??n vi??n:");
		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		JLabel lblTrangThai = new JLabel("Tr???ng th??i:");
		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		JLabel lblLoaiNV = new JLabel("Lo???i nh??n vi??n:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblSoDienThoai = new JLabel("S??? ??i???n tho???i:");
		JLabel lblNgayVaoLam = new JLabel("Ng??y v??o l??m:");

		lblMaNV.setFont(UIConstant.NORMAL_FONT);
		lblMaNV.setPreferredSize(new Dimension(90, 20));
		lblTenNV.setFont(UIConstant.NORMAL_FONT);
		lblTenNV.setPreferredSize(new Dimension(90, 20));
		lblNgaySinh.setFont(UIConstant.NORMAL_FONT);
		lblNgaySinh.setPreferredSize(new Dimension(90, 20));
		lblTrangThai.setFont(UIConstant.NORMAL_FONT);
		lblTrangThai.setPreferredSize(new Dimension(90, 20));
		lblGioiTinh.setFont(UIConstant.NORMAL_FONT);
		lblGioiTinh.setPreferredSize(new Dimension(90, 20));
		lblLoaiNV.setFont(UIConstant.NORMAL_FONT);
		lblLoaiNV.setPreferredSize(new Dimension(90, 20));
		lblEmail.setFont(UIConstant.NORMAL_FONT);
		lblEmail.setPreferredSize(new Dimension(90, 20));
		lblSoDienThoai.setFont(UIConstant.NORMAL_FONT);
		lblSoDienThoai.setPreferredSize(new Dimension(90, 20));
		lblNgayVaoLam.setFont(UIConstant.NORMAL_FONT);
		lblNgayVaoLam.setPreferredSize(new Dimension(90, 20));

		txtMaNV = new JTextField();
		txtMaNV.setEditable(false);
		txtMaNV.setFont(UIConstant.NORMAL_FONT);
//		txtMaNV.setText(nhanVien.getMaNhanVien().toString());
		txtTenNV = new JTextField();
		txtTenNV.setFont(UIConstant.NORMAL_FONT);
//		txtTenNV.setText(nhanVien.getTenNhanVien().toString());

		txtEmail = new JTextField();
		txtEmail.setFont(UIConstant.NORMAL_FONT);
//		txtEmail.setText(nhanVien.getEmail().toString());
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(UIConstant.NORMAL_FONT);
//		txtSoDienThoai.setText(nhanVien.getSoDienThoai().toString());
		txtLoaiNV = new JTextField();
		txtLoaiNV.setEditable(false);
		txtLoaiNV.setFont(UIConstant.NORMAL_FONT);
//		String loai = "";
//		if (nhanVien.getLoaiNhanVien() == 1) {
//			loai = "Nh??n vi??n b??n h??ng";
//		} else {
//			loai = "Qu???n l?? vi??n";
//		}
//		txtLoaiNV.setText(loai);
		txtTrangThai = new JTextField();
		txtTrangThai.setFont(UIConstant.NORMAL_FONT);
		txtTrangThai.setEditable(false);
//		String tt = "";
//		if (nhanVien.getTrangThai() == 0) {
//			tt = "??ang l??m";
//		} else {
//			tt = "T???m ngh???";
//		}
//		txtTrangThai.setText(tt);

		dateNgayVaoLam = new JDateChooser();
		dateNgayVaoLam.setDateFormatString("dd-MM-yyyy");
		dateNgayVaoLam.setFont(UIConstant.NORMAL_FONT);

//		LocalDate localDate = nhanVien.getNgayVaoLam();// For reference
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		String formattedString = localDate.format(formatter);
//		Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString);
//		dateNgayVaoLam.setDate(date2);
		dateNgayVaoLam.setEnabled(false);
		dateNgaySinh = new JDateChooser();
		dateNgaySinh.setDateFormatString("dd-MM-yyyy");
		dateNgaySinh.setFont(UIConstant.NORMAL_FONT);
//		
//		LocalDate localDate2 = nhanVien.getNgaySinh();// For reference
//		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		String formattedString2 = localDate2.format(formatter2);
//		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
//		dateNgaySinh.setDate(date);

		jcbGioiTinh = new JComboBox<String>();
		jcbGioiTinh.setFont(UIConstant.NORMAL_FONT);
		jcbGioiTinh.addItem("N???");
		jcbGioiTinh.addItem("Nam");

//		String gt = "";
//		if (nhanVien.isGioiTinh() == true)
//			gt = "Nam";
//		else
//			gt = "N???";
//
////		System.out.println(gt);
//		jcbGioiTinh.setSelectedItem(gt);
		taiDuLieuLenBang();
		boxLine1.add(Box.createHorizontalStrut(50));
		boxLine1.add(lblMaNV);
		boxLine1.add(txtMaNV);
		boxLine2.add(Box.createHorizontalStrut(50));
		boxLine2.add(lblNgaySinh);
		boxLine2.add(dateNgaySinh);
		boxLine1.add(Box.createHorizontalStrut(50));
		boxLine2.add(Box.createHorizontalStrut(50));

		boxLine3.add(Box.createHorizontalStrut(50));
		boxLine3.add(lblTenNV);
		boxLine3.add(txtTenNV);

		boxLine4.add(Box.createHorizontalStrut(50));
		boxLine4.add(lblNgayVaoLam);
		boxLine4.add(dateNgayVaoLam);
		boxLine3.add(Box.createHorizontalStrut(50));
		boxLine4.add(Box.createHorizontalStrut(50));

		boxLine5.add(Box.createHorizontalStrut(50));
		boxLine5.add(lblEmail);
		boxLine5.add(txtEmail);
		txtEmail.setPreferredSize(new Dimension(300, 25));
		boxLine5.add(Box.createHorizontalStrut(50));
		boxLine6.add(Box.createHorizontalStrut(50));
		boxLine6.add(lblGioiTinh);
		boxLine6.add(jcbGioiTinh);
		boxLine6.add(Box.createHorizontalStrut(60));
		jcbGioiTinh.setPreferredSize(new Dimension(300, 35));

		boxLine7.add(Box.createHorizontalStrut(50));
		boxLine7.add(lblSoDienThoai);
		boxLine7.add(txtSoDienThoai);
		boxLine7.add(Box.createHorizontalStrut(50));
		boxLine8.add(Box.createHorizontalStrut(50));
		boxLine8.add(lblTrangThai);
		boxLine8.add(txtTrangThai);
		boxLine8.add(Box.createHorizontalStrut(50));
		boxLine9.add(Box.createHorizontalStrut(50));
		boxLine9.add(lblLoaiNV);
		boxLine9.add(txtLoaiNV);
		boxLine9.add(Box.createHorizontalStrut(50));

		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine1);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine2);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine3);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine4);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine5);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine6);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine7);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine8);
		boxThongTin.add(Box.createVerticalStrut(20));
		boxThongTin.add(boxLine9);
		boxThongTin.add(Box.createVerticalStrut(20));

		Box boxButton = Box.createHorizontalBox();
		btnHoanThongTin = addButtonTo(boxButton, "Tr??? l???i th??ng tin ban ?????u", "img/undo1_.png",
				UIConstant.PRIMARY_COLOR);
		btnHoanThongTin.setBorderRadius(30);
		btnSua = addButtonTo(boxButton, "C???p nh???p th??ng tin", "img/modify.png", UIConstant.WARNING_COLOR);
		btnSua.setBorderRadius(30);
		btnCapNhap = addButtonTo(boxButton, "L??m m???i", "img/refresh3.png", UIConstant.WARNING_COLOR);
		btnCapNhap.setBorderRadius(30);
		boxThongTin.add(Box.createVerticalStrut(50));
		boxThongTin.add(boxButton);
		boxThongTin.add(Box.createVerticalStrut(200));

		ballNgaySinh = new BalloonTip(dateNgaySinh, "Ng??y sinh ph???i tr?????c ng??y hi???n t???i");
		ballNgaySinh.setVisible(false);
		ballNgaySinh.setCloseButton(null);
		ballTenNV = new BalloonTip(txtTenNV, " + H??? v?? t??n nh??n vi??n ph???i b???t ?????u ch??? c??i in hoa \n"

				+ " + Kh??ng ch???a c??c k?? t??? ?????c bi???t v?? s???");
		ballTenNV.setVisible(false);
		ballTenNV.setCloseButton(null);
		ballNgayVaoLam = new BalloonTip(dateNgayVaoLam, "Ng??y v??o l??m ph???i tr?????c ng??y hi???n t???i v?? sau ng??y sinh");
		ballNgayVaoLam.setVisible(false);
		ballNgayVaoLam.setCloseButton(null);

		ballEmail = new BalloonTip(txtEmail,
				" + Email ph???i b???t ?????u b???ng 1 k?? t??? \n" + "+ Ch??? ch???a k?? t??? a-z, 0-9 v?? k?? t??? d???u ch???m (.) \n"
						+ "+ ????? d??i t???i thi???u l?? 8, ????? d??i t???i ??a l?? 32 \n"
						+ "+ T??n mi???n c?? th??? l?? t??n mi???n c???p 1 ho???c c???p 2");
		ballEmail.setVisible(false);
		ballEmail.setCloseButton(null);
		ballSoDienThoai = new BalloonTip(txtSoDienThoai, "S??? ??i???n tho???i kh??ng ???????c r???ng, g???m 10 s???, b???t ?????u b???ng s??? 0");
		ballSoDienThoai.setVisible(false);
		ballSoDienThoai.setCloseButton(null);

//		pnlCenter.add(boxThongTin, BorderLayout.CENTER);
		this.add(pnlCenter = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		pnlCenter.setLayout(new GridLayout(1, 2));
		pnlCenter.add(tabPaneTT);
//		tabPaneTT.addTab("T??m ki???m", boxTimKiem);
		tabPaneTT.add("C???p nh???t nh??n vi??n", boxThongTin);
		tabPaneTT.setFont(new Font("Arial", Font.BOLD, 12));

		// ========================================================

//		JPanel pnlSouth = new JPanel();
//		this.add(pnlSouth = new JPanel(new BorderLayout()), BorderLayout.SOUTH);
//		pnlSouth.setOpaque(false);

		JTabbedPane tabPanePassword = new JTabbedPane();
		tabPanePassword.setOpaque(false);
		tabPanePassword.setPreferredSize(new Dimension(100, 200));

		Box boxPassword, boxNorth;
		boxPassword = Box.createVerticalBox();
		boxNorth = Box.createHorizontalBox();
		boxButton = Box.createHorizontalBox();

		boxNorth.add(Box.createHorizontalStrut(10));

		boxPassword.add(Box.createVerticalStrut(10));
		boxPassword.add(boxNorth, BorderLayout.SOUTH);
		boxPassword.add(Box.createVerticalStrut(10));
		txtOldPassWord = addInputItemTo(boxPassword, "M???t kh???u hi???n t???i:");
		boxPassword.add(Box.createVerticalStrut(20));
		txtNewPasssWord = addInputItemTo(boxPassword, "M???t kh???u m???i:");
		boxPassword.add(Box.createVerticalStrut(20));
		txtConfirmPassWord = addInputItemTo(boxPassword, "Nh???p l???i m???t kh???u m???i:");
		boxPassword.add(Box.createVerticalStrut(10));

		btnChange = addButtonTo(boxButton, "?????i m???t kh???u", "Images/changePassword.png", UIConstant.WARNING_COLOR);
		btnChange.setBorderRadius(30);
		btnHide = addButtonTo(boxButton, "Hi???n", "Images/show.png", UIConstant.PRIMARY_COLOR);
		btnHide.setBorderRadius(30);
//		btnHide.setPreferredSize(new Dimension(90, 50));
		boxPassword.add(Box.createVerticalStrut(50));
		boxPassword.add(boxButton);
		boxPassword.add(Box.createVerticalStrut(510));

		tabPanePassword.addTab("?????i m???t kh???u", boxPassword);
		tabPanePassword.setFont(new Font("Arial", Font.BOLD, 12));
		pnlCenter.add(tabPanePassword);
//		pnlSouth.add(tabPanePassword, BorderLayout.SOUTH);
//		pnlSouth.add(Box.createVerticalStrut(10), BorderLayout.CENTER);

	}

	private ColoredButton addButtonTo(Box box, String name, String path, Color color) {
		ColoredButton btn = new ColoredButton(name, new ImageIcon(path));
		btn.setFont(UIConstant.NORMAL_FONT);
		btn.setBackground(color);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btn);
		boxButton.add(Box.createHorizontalGlue());

		box.add(boxButton);

		return btn;
	}

	private JPasswordField addInputItemTo(Box box, String name) {
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(130, 25));
		label.setFont(UIConstant.NORMAL_FONT);
		JPasswordField text = new JPasswordField();

		Box boxItem = Box.createHorizontalBox();
		boxItem.add(Box.createHorizontalStrut(15));
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(label);
		boxItem.add(Box.createHorizontalStrut(5));
		boxItem.add(text);
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(Box.createHorizontalStrut(15));

		box.add(Box.createVerticalStrut(5));
		box.add(boxItem);
		box.add(Box.createVerticalStrut(5));

		return text;
	}

	public List<Component> getAllComponents(Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

//	private void setLookAndFeel() {
//		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	private void addEvent() {
		this.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("ancestor")) {
					tabPaneTT.setSelectedIndex(0);
				}
			}
		});

		btnHide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon hideIcon = new ImageIcon("img/hide.png");
				String hideText = "???n";
				ImageIcon showIcon = new ImageIcon("img/show.png");
				String showText = "Hi???n";
				if (btnHide.getText().equalsIgnoreCase(hideText)) {
					btnHide.setIcon(showIcon);
					btnHide.setText(showText);
					txtConfirmPassWord.setEchoChar('*');
					txtNewPasssWord.setEchoChar('*');
				} else {
					btnHide.setIcon(hideIcon);
					btnHide.setText(hideText);
					txtConfirmPassWord.setEchoChar((char) 0);
					txtNewPasssWord.setEchoChar((char) 0);
				}
			}
		});

		btnChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NhanVien nvn = null;
				try {
					nvn = nvDao.getNhanVien(txtMaNV.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(nvn.getMatKhau());
				System.out.println(!String.valueOf(txtOldPassWord.getPassword()).equals(nvn.getMatKhau()));

				if (String.valueOf(txtNewPasssWord.getPassword()).equals("")
						|| String.valueOf(txtConfirmPassWord.getPassword()).equals("")
						|| String.valueOf(txtOldPassWord.getPassword()).equals(""))
					UIConstant.showWarning(ThongTinCaNhanPanel.this, "Vui l??ng nh???p ?????y ????? th??ng tin!");

				else if (!String.valueOf(txtOldPassWord.getPassword()).equals(nvn.getMatKhau())) {
					UIConstant.showWarning(ThongTinCaNhanPanel.this, "Sai m???t kh???u c??");
				}
//				} else if (String.valueOf(txtNewPasssWord.getPassword()).equals("")
//						|| String.valueOf(txtConfirmPassWord.getPassword()).equals("") || String.valueOf(txtOldPassWord.getPassword()).equals(""))
//					UIConstant.showWarning(ThongTinCaNhanPanel.this, "Vui l??ng nh???p ?????y ????? th??ng tin!");
				else if (!String.valueOf(txtNewPasssWord.getPassword())
						.equals(String.valueOf(txtConfirmPassWord.getPassword())))
					UIConstant.showWarning(ThongTinCaNhanPanel.this, "X??c nh???n m???t kh???u kh??ng th??nh c??ng");
				else {
					System.out.println(txtMaNV.getText().toString());
					NhanVien nv = null;
					try {
						nv = nvDao.getNhanVien(txtMaNV.getText().toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(nv);
					nv.setMatKhau(String.valueOf(txtConfirmPassWord.getPassword()));

					try {
						if (nvDao.updateNhanVien(nv)) {
							UIConstant.showInfo(ThongTinCaNhanPanel.this, "?????i m???t kh???u th??nh c??ng");
							XoaRong();
						} else
							UIConstant.showWarning(ThongTinCaNhanPanel.this, "?????i m???t kh???u kh??ng th??nh c??ng");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		btnCapNhap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	

				NhanVien nv = null;
				try {
					nv = nvDao.getNhanVien(nhanVien.getMaNhanVien());
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				txtMaNV.setText(nv.getMaNhanVien().toString());
				txtTenNV.setText(nv.getTenNhanVien().toString());
				txtEmail.setText(nv.getEmail().toString());
				txtSoDienThoai.setText(nv.getSoDienThoai().toString());
				String loai = "";
				if (nv.getLoaiNhanVien() == 1) {
					loai = "Nh??n vi??n b??n h??ng";
				} else {
					loai = "Qu???n l?? vi??n";
				}
				txtLoaiNV.setText(loai);

				String tt = "";
				if (nv.getTrangThai() == 0) {
					tt = "??ang l??m";
				} else {
					tt = "T???m ngh???";
				}
				txtTrangThai.setText(tt);

				LocalDate localDate = nv.getNgayVaoLam();// For reference
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formattedString = localDate.format(formatter);
				Date date2 = null;

				try {
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dateNgayVaoLam.setDate(date2);
				LocalDate localDate2 = nv.getNgaySinh();// For reference
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formattedString2 = localDate2.format(formatter2);
				Date date = null;

				try {
					date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dateNgaySinh.setDate(date);

				String gt = "";
				if (nv.isGioiTinh() == true)
					gt = "Nam";
				else
					gt = "N???";

//				System.out.println(gt);
				jcbGioiTinh.setSelectedItem(gt);
			}
		});

		btnHoanThongTin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				LocalDate localDate = nhanVien.getNgayVaoLam();// For reference
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formattedString = localDate.format(formatter);
				Date date = null;

				try {
					date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				dateNgayVaoLam.setDate(date);
				LocalDate localDate2 = nhanVien.getNgaySinh();// For reference
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formattedString2 = localDate2.format(formatter2);
				Date date2 = null;
				try {
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dateNgaySinh.setDate(date2);

				txtMaNV.setText(nhanVien.getMaNhanVien().toString());
				txtTenNV.setText(nhanVien.getTenNhanVien().toString());
				txtEmail.setText(nhanVien.getEmail().toString());
				txtSoDienThoai.setText(nhanVien.getSoDienThoai().toString());

				String loai = "";
				if (nhanVien.getLoaiNhanVien() == 1) {
					loai = "Nh??n vi??n b??n h??ng";
				} else {
					loai = "Qu???n l?? vi??n";
				}
				txtLoaiNV.setText(loai);

				String tt = "";
				if (nhanVien.getTrangThai() == 0) {
					tt = "??ang l??m";
				} else {
					tt = "T???m ngh???";
				}
				txtTrangThai.setText(tt);

				String gt = "";
				if (nhanVien.isGioiTinh())
					gt = "Nam";
				else
					gt = "N???";

				jcbGioiTinh.setSelectedItem(gt);
			}
		});

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validData()) {
					boolean gt;
					if (jcbGioiTinh.getSelectedItem().toString().equals("Nam"))
						gt = true;
					else
						gt = false;

					int lnv;
					if (txtLoaiNV.getText().equalsIgnoreCase("Qu???n l?? vi??n"))
						lnv = 0;
					else
						lnv = 1;

					int tt;

					if (txtTrangThai.getText().equalsIgnoreCase("??ang l??m"))
						tt = 0;
					else if (txtTrangThai.getText().equalsIgnoreCase("T???m ngh???"))
						tt = 1;
					else
						tt = 2;

					Date dateNS = dateNgaySinh.getDate();
					LocalDate lcdNS = dateNS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					Date dateNVL = dateNgayVaoLam.getDate();
					LocalDate lcdNVL = dateNVL.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					NhanVien nv = null;
					try {
						nv = nvDao.getNhanVien(txtMaNV.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(nv.getMatKhau());
					NhanVien nvs = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), lcdNS, tt, gt,
							txtEmail.getText(), txtSoDienThoai.getText(), lcdNVL, lnv, nv.getMatKhau());

					try {
						if (nvDao.updateNhanVien(nvs)) {

							UIConstant.showInfo(ThongTinCaNhanPanel.this, "S???a th??nh c??ng");
						} else
							UIConstant.showWarning(ThongTinCaNhanPanel.this, "S???a kh??ng th??nh c??ng");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

	}

	private void XoaRong() {
		txtConfirmPassWord.setText("");
		txtNewPasssWord.setText("");
		txtOldPassWord.setText("");
	}

//	private void timNV() {
//		new Thread(() -> {
//			List<NhanVien> list = new ArrayList<NhanVien>();
//
//			int lnv;
//
//			if (jcbTKLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Qu???n l?? vi??n"))
//				lnv = 0;
//			else if (jcbTKLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Nh??n vi??n b??n h??ng"))
//				lnv = 1;
//			else
//				lnv = 2;
//			
//			int tt;
//			
//			if (jcbTKTrangThaiNV.getSelectedItem().toString().equalsIgnoreCase("??ang l??m"))
//				tt = 0;
//			else if (jcbTKTrangThaiNV.getSelectedItem().toString().equalsIgnoreCase("T???m ngh???"))
//				tt = 1;
//			else
//				tt = 3;
//
//			LocalDate lcdNVL = null;
//
//			if (dateTKNgayVaoLam.getDate() != null)
//				lcdNVL = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateTKNgayVaoLam.getDate()));
//
//			list = nvDao.findNhanVien(txtTKTenNV.getText(), tt, txtTKEmail.getText(),
//					txtTKSoDienThoai.getText(), lcdNVL, lnv);
//
//			if (list.size() != 0) {
//				taiDuLieuLenBang(list);
//
//			} else {
//				dsNVs.clear();
//				defaultTable.setRowCount(0);
//			}
//		}).start();
//		;
//
//	}

	private void taiDuLieuLenBang() {
		new Thread(() -> {
//========================================
			txtMaNV.setText(nhanVien.getMaNhanVien().toString());
			txtTenNV.setText(nhanVien.getTenNhanVien().toString());
			txtEmail.setText(nhanVien.getEmail().toString());
			txtSoDienThoai.setText(nhanVien.getSoDienThoai().toString());
			String loai = "";
			if (nhanVien.getLoaiNhanVien() == 1) {
				loai = "Nh??n vi??n b??n h??ng";
			} else {
				loai = "Qu???n l?? vi??n";
			}
			txtLoaiNV.setText(loai);

			String tt = "";
			if (nhanVien.getTrangThai() == 0) {
				tt = "??ang l??m";
			} else {
				tt = "T???m ngh???";
			}
			txtTrangThai.setText(tt);

			LocalDate localDate = nhanVien.getNgayVaoLam();// For reference
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedString = localDate.format(formatter);
			Date date2 = null;
			try {
				date2 = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dateNgayVaoLam.setDate(date2);
			LocalDate localDate2 = nhanVien.getNgaySinh();// For reference
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedString2 = localDate2.format(formatter2);
			Date date = null;
			try {
				date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedString2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dateNgaySinh.setDate(date);

			String gt = "";
			if (nhanVien.isGioiTinh() == true)
				gt = "Nam";
			else
				gt = "N???";

//			System.out.println(gt);
			jcbGioiTinh.setSelectedItem(gt);
			// ========================================

		}).start();
		;

	}

	private boolean validData() {

		String tennv = txtTenNV.getText().trim();
		String sodt = txtSoDienThoai.getText().trim();
		String email = txtEmail.getText().trim();

		// Ng??y sinh: Ng??y sinh ph???i tr?????c ng??y hi???n t???i
		if (dateNgaySinh.getDate() == null || dateNgaySinh.getDate().after(new Date())) {
			ballNgaySinh.setVisible(true);
			return false;
		} else
			ballNgaySinh.setVisible(false);

		// T??n nh??n vi??n: Kh??ng ???????c r???ng, kh??ng ch???a k?? t??? ?????c bi???t
		if (tennv.isEmpty() || !tennv.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
			ballTenNV.setVisible(true);
			return false;
		} else
			ballTenNV.setVisible(false);

		// Ng??y v??o l??m: Tr?????c ng??y hi???n t???i v?? sau ng??y sinh
		if (dateNgayVaoLam.getDate() == null || dateNgayVaoLam.getDate().after(new Date())
				|| dateNgayVaoLam.getDate().before(dateNgaySinh.getDate())) {
			ballNgayVaoLam.setVisible(true);
			return false;
		} else
			ballNgayVaoLam.setVisible(false);

		// Email: ???????c ph??p r???ng, ch??? ???????c ch???a c??c k?? t??? '@', '_', '.', '-'
		if (!email.isEmpty()) {
			if (!email.matches("^[a-z][a-z0-9\\.]{7,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
				ballEmail.setVisible(true);
				return false;
			} else
				ballEmail.setVisible(false);
		}

		// S??? ??i???n tho???i: Kh??ng ???????c r???ng, g???m 10 s???, b???t ?????u b???ng s??? 0
		if (sodt.isEmpty() || !sodt.matches("0\\d{9}")) {
			ballSoDienThoai.setVisible(true);
			return false;
		} else
			ballSoDienThoai.setVisible(false);

		return true;

	}
}
