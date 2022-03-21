package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.CTHoaDonDAO;
import dao.HoaDonDAO;
import entity.CTHoaDon;
import entity.HoaDon;
import impl.CTHoaDonImp;
import impl.HoaDonImp;

public class PrintPreview extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColoredButton btnIn;
	private NumberFormat nf;
	private HoaDonImp hdDao;
	private CTHoaDonImp cthdDao;
	public PrintPreview(JDialog owner, HoaDon hoaDon) throws Exception {
		super(owner);
		
		setSize(900, 600);
		setTitle("Xem trước khi in");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("img/print.png").getImage());
		setLayout(new BorderLayout());
		hdDao = (HoaDonImp) Naming.lookup(Port.PORT + "hdDao");
		cthdDao = (CTHoaDonImp) Naming.lookup(Port.PORT + "cthdDao");
		nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				owner.setEnabled(true);
			}
			@Override
			public void windowActivated(WindowEvent e) {
				owner.setEnabled(false);
			}
		});
		getContentPane().setBackground(Color.white);	
		
		btnIn = new ColoredButton("In", new ImageIcon("img/print.png"));
		btnIn.setBackground(UIConstant.PRIMARY_COLOR);
		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btnIn);
		boxButton.add(Box.createHorizontalGlue());
		
		this.add(boxButton, BorderLayout.NORTH);
		
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		pnlMain.setOpaque(false);
		
		pnlMain.add(Box.createVerticalStrut(20));
		addNewLine(pnlMain, "", "Nhà sách SachHay store", true, true);
		addNewLine(pnlMain, "", "12, Nguyễn Văn Bảo, P4, Gò Vấp, Thành Phố Hồ Chí Minh", true, false);
		pnlMain.add(Box.createVerticalStrut(20));
		addNewLine(pnlMain, "Mã hóa đơn: ", hoaDon.getMaHoaDon(), false, false);
		addNewLine(pnlMain, "Ngày lập: ", hoaDon.getNgayLap().toString(), false, false);
		addNewLine(pnlMain, "Nhân viên lập: ", hoaDon.getNhanVienLap().getTenNhanVien(), false, false);
		
		if(hoaDon.getKhachHang() != null)
			addNewLine(pnlMain, "Tên khách hàng: ", hoaDon.getKhachHang().getTenKhachHang(), false, false);

		pnlMain.add(Box.createVerticalStrut(20));
		
		JLabel lbSTTTitle = new JLabel("STT");
		lbSTTTitle.setPreferredSize(new Dimension(50, 20));
		lbSTTTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		
		JTextArea txtSanPhamTitle = new JTextArea("Tên Sản phẩm");
		txtSanPhamTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		txtSanPhamTitle.setMaximumSize(new Dimension(250, 20));
		txtSanPhamTitle.setMinimumSize(new Dimension(250, 20));
//		txtSanPhamTitle.setSize(150,0);
		txtSanPhamTitle.setLineWrap(true);
		txtSanPhamTitle.setWrapStyleWord(true);
//		txtSanPhamTitle.setEditable(false);

//		JLabel txtSanPhamTitle = new JLabel("Tên sản phẩm");
//		txtSanPhamTitle.setPreferredSize(new Dimension(150, 20));
//		txtSanPhamTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		
		JLabel lbSLTitle = new JLabel("Số lượng");
		lbSLTitle.setPreferredSize(new Dimension(80, 20));
		lbSLTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		lbSLTitle.setHorizontalTextPosition(JLabel.RIGHT);
		
		JLabel lbDGTitle = new JLabel("Đơn giá");
		lbDGTitle.setPreferredSize(new Dimension(80, 20));
		lbDGTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		lbDGTitle.setHorizontalTextPosition(JLabel.RIGHT);
		
		JLabel lbTTTitle = new JLabel("Thành tiền");
		lbTTTitle.setPreferredSize(new Dimension(80, 20));
		lbTTTitle.setFont(new Font("Times new roman", Font.PLAIN, 16));
		lbTTTitle.setHorizontalTextPosition(JLabel.RIGHT);
		
		Box boxTitle = Box.createHorizontalBox();
		boxTitle.add(Box.createHorizontalStrut(20));
		boxTitle.add(lbSTTTitle);
		boxTitle.add(Box.createHorizontalStrut(5));
		boxTitle.add(txtSanPhamTitle);
//		txtSanPhamTitle.setTabSize(200);
		boxTitle.add(Box.createHorizontalStrut(5));
		boxTitle.add(lbSLTitle);
		boxTitle.add(Box.createHorizontalStrut(5));
		boxTitle.add(lbDGTitle);
		boxTitle.add(Box.createHorizontalStrut(5));
		boxTitle.add(lbTTTitle);
		boxTitle.add(Box.createHorizontalStrut(10));
		boxTitle.add(Box.createHorizontalGlue());
		
		pnlMain.add(boxTitle);
		
		pnlMain.add(Box.createVerticalStrut(5));
		
		List<CTHoaDon> ds = cthdDao.getDsCTHD(hoaDon);
		for(CTHoaDon item : ds) {
			JLabel lbSTT = new JLabel(ds.indexOf(item) + 1 + "");
			lbSTT.setPreferredSize(lbSTTTitle.getPreferredSize());
			lbSTT.setFont(new Font("Times new roman", Font.PLAIN, 16));
			
			JTextArea txtTenSanPham = new JTextArea(item.getSanPham().getTenSanPham());
			txtTenSanPham.setFont(new Font("Times new roman", Font.PLAIN, 16));
			txtTenSanPham.setMaximumSize(new Dimension(250, 50));
			txtTenSanPham.setLineWrap(true);
			txtTenSanPham.setWrapStyleWord(true);
//			txtTenSanPham.setEditable(false);
//			
////			JLabel txtTenSanPham = new JLabel(item.getSanPham().getTenSanPham() + "");
////			txtTenSanPham.setPreferredSize(new Dimension(150, 20));
////			txtTenSanPham.setFont(new Font("Times new roman", Font.PLAIN, 16));
////			
////			txtTenSanPham.setHorizontalTextPosition(JLabel.RIGHT);
//			
			JLabel lbSL = new JLabel(item.getSoLuong() + "");
			lbSL.setPreferredSize(lbSLTitle.getPreferredSize());
			lbSL.setFont(new Font("Times new roman", Font.PLAIN, 16));
			lbSL.setHorizontalTextPosition(JLabel.RIGHT);
			
			JLabel lbDG = new JLabel(item.getDonGia() + "");
			lbDG.setPreferredSize(lbDGTitle.getPreferredSize());
			lbDG.setFont(new Font("Times new roman", Font.PLAIN, 16));
			lbDG.setHorizontalTextPosition(JLabel.RIGHT);
			
			JLabel lbTT = new JLabel(nf.format(item.tinhThanhTien()) + "");
			lbTT.setPreferredSize(lbTTTitle.getPreferredSize());
			lbTT.setFont(new Font("Times new roman", Font.PLAIN, 16));
			lbTT.setHorizontalTextPosition(JLabel.RIGHT);
			
			Box box = Box.createHorizontalBox();
			box.add(Box.createHorizontalStrut(20));
			box.add(lbSTT);
			box.add(Box.createHorizontalStrut(5));
			box.add(txtTenSanPham);
			box.add(Box.createHorizontalStrut(5));
			box.add(lbSL);
			box.add(Box.createHorizontalStrut(5));
			box.add(lbDG);
			box.add(Box.createHorizontalStrut(5));
			box.add(lbTT);
			box.add(Box.createHorizontalStrut(20));
			box.add(Box.createHorizontalGlue());
			
			pnlMain.add(box);
			pnlMain.add(Box.createVerticalStrut(5));
		};
		
		double gia = hdDao.tinhTongTien(hoaDon);
		System.out.println(gia);
		addNewLine(pnlMain, "Tổng tiền: ", nf.format(gia) + " VND", false, false);
		pnlMain.add(Box.createVerticalStrut(20));
		pnlMain.add(Box.createVerticalGlue());
		
		JScrollPane scroll = new JScrollPane(pnlMain);
		scroll.setOpaque(false);
		scroll.setBorder(null);
		scroll.getViewport().setBackground(Color.white);
		
		this.add(scroll, BorderLayout.CENTER);
	}
	
	private void addNewLine(JPanel panel, String label, String content, boolean isCenter, boolean isBold) {
		Box box = Box.createHorizontalBox();
		panel.add(box);
		panel.add(Box.createVerticalStrut(5));
		
		JLabel lbContent = new JLabel(content);
		lbContent.setFont(new Font("Times new roman", Font.PLAIN, 16));
		lbContent.setHorizontalTextPosition(JLabel.LEFT);
		
		if(isBold)
			lbContent.setFont(new Font("Times new roman", Font.BOLD, 16));
		
		if(isCenter) {
			box.add(Box.createHorizontalGlue());
			lbContent.setHorizontalTextPosition(JLabel.CENTER);
		}
		
		box.add(Box.createHorizontalStrut(10));
		
		if(label != null && !label.isEmpty()) {
			JLabel lb = new JLabel(label);
			lb.setFont(new Font("Times new roman", Font.PLAIN, 16));
			lb.setHorizontalTextPosition(JLabel.LEFT);
			lb.setPreferredSize(new Dimension(120, 20));
			
			box.add(lb);
		}
		
		box.add(lbContent);
		
		box.add(Box.createHorizontalStrut(10));
		
		box.add(Box.createHorizontalGlue());
	}

}
