package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.DomainCombiner;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

import entity.NhaCungCap;
import impl.NhaCungCaoImp;
import net.java.balloontip.BalloonTip;


public class QuanLyNhaCungCapPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel defaultTable;
	private JTable tableNCC;
	private JScrollPane scroll;
	private JTextField txtMaNCC;
	private JTextField txtTenNCC;
	private JTextField txtDiaChi;
	private JTextField txtSoFax;
	private ColoredButton btnXoa;
	private ColoredButton btnQuayLai;
	private ColoredButton btnSua;
	private ColoredButton btnThem;
	private ColoredButton btnXoaRong;
	//	private NhaCungCapDAO nccDao;
	private ColoredButton btnTKXoaRong;
	private ColoredButton btnTKTimKiem;
	private JTextField txtTKTenNCC;
	private JTextField txtTKDiaChi;
	private JTextField txtTKSoFax;
	private JTabbedPane tabPaneNCC;
	//	private MainFrame mainFrame;
	//	private List<NhaCungCap> nccs = null;
	private int currentIndex = 0;
	private JLabel lbPage;
	private ColoredButton btnHome;
	private ColoredButton btnEnd;
	private ColoredButton btnBefore;
	private ColoredButton btnNext;
	private BalloonTip ballTenNCC;
	private BalloonTip ballDiaChi;
	private BalloonTip ballSoFax;
	private NhaCungCaoImp nccDao;
	private List<NhaCungCap> nccs= null;
	private MainFrame mainFrame;

	public QuanLyNhaCungCapPanel(MainFrame mainFrame) throws Exception {
		this.mainFrame = mainFrame;

		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.white);
		//		setBackground(UiConstrant.PRIMARY_COLOR);
		addNorth();
		addCenter();
		addEvent();
		nccDao = (NhaCungCaoImp) Naming.lookup(Port.PORT + "nccDao");
		loadData();
		//		setLookAndFeel();

		//		btnTKTimKiem.addActionListener(this);
		//		btnThem.addActionListener(this);
		//		btnTKXoaRong.addActionListener(this);
		//		btnXoa.addActionListener(this);

	}



	private void addCenter() {

		JPanel pnlCenter = new JPanel();
		this.add(pnlCenter = new JPanel(new BorderLayout()));
		pnlCenter.setOpaque(false);

		JTabbedPane tabPaneNCC = new JTabbedPane();
		tabPaneNCC.setOpaque(false);
		tabPaneNCC.setPreferredSize(new Dimension(100, 200));

		pnlCenter.add(tabPaneNCC, BorderLayout.CENTER);

		String[] header = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số Fax"};
		defaultTable = new DefaultTableModel(header, 0);
		tableNCC = new CustomTable();
		tableNCC.setModel(defaultTable);
		scroll = new JScrollPane(tableNCC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.getViewport().setBackground(Color.white);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		tabPaneNCC.addTab("Danh sách nhà cung cấp", scroll);
		tabPaneNCC.setFont(new Font("Arial", Font.BOLD, 12));

		lbPage = new JLabel("Trang 1 trong 1 trang."); 
		lbPage.setFont(UIConstant.NORMAL_FONT);

		btnHome = new ColoredButton(new ImageIcon("img/double_left.png")); 
		btnHome.setBackground(UIConstant.LINE_COLOR);
		btnEnd = new ColoredButton(new ImageIcon("img/double_right.png")); 
		btnEnd.setBackground(UIConstant.LINE_COLOR);
		btnBefore = new ColoredButton(new ImageIcon("img/left.png")); 
		btnBefore.setBackground(UIConstant.LINE_COLOR);
		btnNext = new ColoredButton(new ImageIcon("img/right.png")); 
		btnNext.setBackground(UIConstant.LINE_COLOR);

		btnHome.setToolTipText("Trang đầu");
		btnHome.setBorderRadius(20);
		btnEnd.setToolTipText("Trang cuối");
		btnEnd.setBorderRadius(20);
		btnBefore.setToolTipText("Trang trước");
		btnBefore.setBorderRadius(20);
		btnNext.setToolTipText("Trang kế tiếp");
		btnNext.setBorderRadius(20);
		Box boxPage = Box.createHorizontalBox();
		boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnHome); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnBefore); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnNext); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnEnd); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(Box.createHorizontalGlue());
		boxPage.add(lbPage); boxPage.add(Box.createHorizontalStrut(5));

		pnlCenter.add(boxPage, BorderLayout.SOUTH);

		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		pnlSouth.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		Box boxLineButton;

		boxLineButton = Box.createHorizontalBox();

		btnQuayLai = addButtonTo(boxLineButton, "Quay Lại", "img/back.png", UIConstant.PRIMARY_COLOR);
		btnQuayLai.setBorderRadius(30);

		ballTenNCC = new BalloonTip(txtTenNCC, "Tên nhà cung cấp không được rỗng"); ballTenNCC.setVisible(false); ballTenNCC.setCloseButton(null);
		ballDiaChi = new BalloonTip(txtDiaChi, "Địa chỉ cung cấp không được rỗng"); ballDiaChi.setVisible(false); ballDiaChi.setCloseButton(null);
		ballSoFax = new BalloonTip(txtSoFax, "Số fax không được rỗng, gồm 10 số, bắt đầu bằng số 0"); ballSoFax.setVisible(false); ballSoFax.setCloseButton(null);

		pnlSouth.setOpaque(false);
		pnlSouth.add(boxLineButton, BorderLayout.EAST);

		this.add(pnlSouth, BorderLayout.SOUTH);


	}

	private void addNorth() {

		//North
		Box boxNorth;

		JPanel pnlTitle = new JPanel();
		pnlTitle.setOpaque(false);


		JLabel lblHeader = new JLabel("Quản Lý Nhà Cung Cấp");


		lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
		lblHeader.setHorizontalAlignment(JLabel.CENTER);
		lblHeader.setForeground(new Color(255,127,0));



		//Tab Cập nhật

		Box boxButton;
		boxButton = Box.createHorizontalBox();
		btnThem = addButtonTo(boxButton, "Thêm nhà cung cấp", "img/add.png", UIConstant.PRIMARY_COLOR);
		btnSua = addButtonTo(boxButton, "Sửa nhà cung cấp", "img/modify.png", UIConstant.PRIMARY_COLOR);
		btnXoa = addButtonTo(boxButton, "Xoá nhà cung cấp", "img/delete.png", new Color(198, 0, 0));
		btnXoa.setBorderRadius(30);
		btnXoaRong = addButtonTo(boxButton, "Làm mới", "img/refresh3.png", new Color(0x019474D));
		btnThem.setBorderRadius(30);
		btnXoaRong.setBorderRadius(30);


		btnSua.setBorderRadius(30);
		pnlTitle.add(lblHeader);

		boxNorth = Box.createVerticalBox();
		//		boxNorth.add(Box.createVerticalStrut(15));
		boxNorth.add(pnlTitle);

		tabPaneNCC = new JTabbedPane();
		tabPaneNCC.setOpaque(false);
		tabPaneNCC.setPreferredSize(new Dimension(100, 230));
		tabPaneNCC.setFont(new Font("Arial", Font.BOLD, 12));

		Box boxThongTin;
		JPanel pnlThongTin = new JPanel(new BorderLayout());
		pnlThongTin.setOpaque(false);
		boxThongTin = Box.createVerticalBox();



		txtMaNCC = addInputItemTo(boxThongTin, "Mã nhà cung cấp:");




		txtMaNCC.setEditable(false);
		txtMaNCC.setFont(UIConstant.NORMAL_FONT);




		//		txtMaNCC.setBackground(new Color(214,182,180));
		txtTenNCC = addInputItemTo(boxThongTin, "Tên nhà cung cấp:");
		txtTenNCC.setFont(UIConstant.NORMAL_FONT);


		//		txtTenNCC.setBackground(new Color(214,182,180));
		txtDiaChi = addInputItemTo(boxThongTin, "Địa chỉ:");
		//		txtDiaChi.setBackground(new Color(214,182,180));
		txtDiaChi.setFont(UIConstant.NORMAL_FONT);


		txtSoFax = addInputItemTo(boxThongTin, "Số Fax:");
		//		txtSoFax.setBackground(new Color(214,182,180));
		txtSoFax.setFont(UIConstant.NORMAL_FONT);

		boxThongTin.add(Box.createVerticalStrut(10));
		boxThongTin.add(boxButton);
		boxThongTin.add(Box.createVerticalStrut(10));

		// Tab tìm kiếm

		Box boxTKButton;
		boxTKButton = Box.createHorizontalBox();
		btnTKTimKiem = addButtonTo(boxTKButton, "Tìm kiếm", "img/search.png", UIConstant.PRIMARY_COLOR);
		btnTKTimKiem.setBorderRadius(30);
		btnTKXoaRong = addButtonTo(boxTKButton, "Làm mới", "img/refresh3.png", new Color(0x019474D));
		btnTKXoaRong.setBorderRadius(30);

		Box boxTimKiem;
		boxTimKiem = Box.createVerticalBox();

		txtTKTenNCC = addInputItemTo(boxTimKiem, "Tên nhà cung cấp:");
		txtTKTenNCC.setFont(UIConstant.NORMAL_FONT);








		//		txtTKTenNCC.setBackground(new Color(214,182,180));


		txtTKDiaChi = addInputItemTo(boxTimKiem, "Địa chỉ:");
		txtTKDiaChi.setFont(UIConstant.NORMAL_FONT);
		//		txtTKDiaChi.setBackground(new Color(214,182,180));
		txtTKSoFax = addInputItemTo(boxTimKiem, "Số Fax:");
		txtTKSoFax.setFont(UIConstant.NORMAL_FONT);
		//		txtTKSoFax.setBackground(new Color(214,182,180));

		boxTimKiem.add(Box.createVerticalStrut(20));
		boxTimKiem.add(boxTKButton);
		boxTimKiem.add(Box.createVerticalStrut(20));

		tabPaneNCC.addTab("Tìm kiếm", boxTimKiem);
		tabPaneNCC.addTab("Cập nhật thông tin", boxThongTin);
		pnlThongTin.add(tabPaneNCC);

		boxNorth.add(pnlThongTin);
		//		boxNorth.add(Box.createVerticalStrut(15));

		this.add(boxNorth, BorderLayout.NORTH);
	}

	private ColoredButton addButtonTo(Box box, String name, String path, Color color) {
		ColoredButton btn = new ColoredButton(name, new ImageIcon(path));
		btn.setFont(UIConstant.NORMAL_FONT);
		btn.setBackground(color);
		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalStrut(5));
		boxButton.add(btn);
		boxButton.add(Box.createHorizontalStrut(5));
		box.add(boxButton);
		return btn;
	}

	private JTextField addInputItemTo(Box box, String name) {
		JLabel label = new JLabel(name); label.setPreferredSize(new Dimension(110, 25));
		label.setFont(UIConstant.NORMAL_FONT);
		JTextField text = new JTextField(); 

		Box boxItem = Box.createHorizontalBox();
		boxItem.add(Box.createHorizontalStrut(20));
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(label);
		boxItem.add(Box.createHorizontalStrut(5));
		boxItem.add(text);
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(Box.createHorizontalStrut(20));

		box.add(Box.createVerticalStrut(5));
		box.add(boxItem);
		box.add(Box.createVerticalStrut(5));

		return text;
	}



	private void taiDuLieuLenBang(List<NhaCungCap> nhaCungCaps, int minIndex) {
		if(minIndex >= nhaCungCaps.size() || minIndex < 0)
			return;

		lbPage.setText("Trang " + (minIndex / 20 + 1) + " trong " + ((nhaCungCaps.size() - 1) / 20 + 1) + " trang.");
		nccs= nhaCungCaps;
		defaultTable.setRowCount(0);
		defaultTable.getDataVector().removeAllElements();
		defaultTable.fireTableDataChanged();
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);

		for(int i = minIndex; i < minIndex + 20; i++) {
			if(i >= nhaCungCaps.size())
				break;
			NhaCungCap nhaCungCap = nhaCungCaps.get(i);
			SwingUtilities.invokeLater( () -> {
				defaultTable.addRow(new Object[] {
						nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(),
						nhaCungCap.getDiaChi(), nhaCungCap.getSoFax()
				});
			});
		}

		currentIndex = minIndex;
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
	public void loadData() throws Exception {
		List<NhaCungCap> dsNCC = new ArrayList<NhaCungCap>();
		dsNCC = nccDao.get20NCC();
		taiDuLieuLenBang(dsNCC, 0);
	}


	public Component getDefaultFocusComponent() {
		return txtTenNCC;
	}


	private void addEvent() {
		this.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("ancestor")) {
					tabPaneNCC.setSelectedIndex(0);
				}
			}
		});

		txtTenNCC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtTenNCC.getText().isEmpty())
					ballTenNCC.setVisible(true);
				else
					ballTenNCC.setVisible(false);
			}
		});

		txtDiaChi.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtDiaChi.getText().isEmpty())
					ballDiaChi.setVisible(true);
				else
					ballDiaChi.setVisible(false);
			}
		});

		txtSoFax.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtSoFax.getText().isEmpty() || !txtSoFax.getText().matches("0\\d{9}"))
					ballSoFax.setVisible(true);
				else
					ballSoFax.setVisible(false);
			}
		});


		btnHome.addActionListener(e -> {
			if(defaultTable.getRowCount() > 0) {
				taiDuLieuLenBang(nccs, 0);
			}
		});

		btnEnd.addActionListener(e -> {
			if(defaultTable.getRowCount() > 0) {
				taiDuLieuLenBang(nccs, nccs.size() - nccs.size() % 20);
			}
		});

		btnBefore.addActionListener(e -> {
			if(defaultTable.getRowCount() > 0) {
				taiDuLieuLenBang(nccs, currentIndex  - 20);
			}
		});

		btnNext.addActionListener(e -> {
			if(defaultTable.getRowCount() > 0) {
				taiDuLieuLenBang(nccs, currentIndex + 20);
			}
		});

		txtTKTenNCC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNCC();
			}
		});

		txtTKSoFax.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNCC();
			}
		});

		txtTKDiaChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNCC();
			}
		});

		tableNCC.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = tableNCC.getSelectedRow();
				if(row == -1)
					return;
				txtMaNCC.setText(tableNCC.getValueAt(row, 0).toString());
				txtTenNCC.setText(tableNCC.getValueAt(row, 1).toString());
				txtDiaChi.setText(tableNCC.getValueAt(row, 2).toString());
				txtSoFax.setText(tableNCC.getValueAt(row, 3).toString());
			}
		});

		btnXoaRong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				XoaRong();
			}
		});

		btnTKXoaRong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					loadData();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//				timNCC();
				XoaRong();


			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				if(validData()) {
					NhaCungCap ncc = new NhaCungCap(txtTenNCC.getText(),
							txtDiaChi.getText(), txtSoFax.getText());
					System.out.println("1");
					try {
						if(kiemtraTrungTen()==false) {
							UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Nhà cung cấp có tên " + txtTenNCC.getText() +" đã tồn tại");
							System.out.println("2");
							txtTenNCC.requestFocus();
							txtTenNCC.selectAll();
						}
						////

						else if(kiemtraTrungsoFax()==false) {
							UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Nhà cung cấp có số fax " + txtSoFax.getText() +" đã được sử dụng");
							System.out.println("3");
							txtSoFax.requestFocus();
							txtSoFax.selectAll();
						} else
							try {
								if(nccDao.addNhaCungCap(ncc)) {
									UIConstant.showInfo(QuanLyNhaCungCapPanel.this, "Thêm thành công");
									loadData();
									nccs.add(ncc);
									XoaRong();
								}
								else
									UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Thêm không thành công");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}		
			}
		});

		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableNCC.getSelectedRow();
				if(row == -1) {
					UIConstant.showInfo(QuanLyNhaCungCapPanel.this, "Chưa chọn nhà cung cấp");
					return;
				} 

				if(JOptionPane.showConfirmDialog(QuanLyNhaCungCapPanel.this, "Bạn có chắc là muốn xóa!", "Xác nhận", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
					return;
				}
				try {
					if(nccDao.deleteNhaCungCap(txtMaNCC.getText().toString())) {
						UIConstant.showInfo(QuanLyNhaCungCapPanel.this, "Xoá thành công");
						loadData();
						defaultTable.removeRow(row);
						nccs.remove(row + currentIndex);
						XoaRong();

					} else
						UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Xoá không thành công");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnSua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabPaneNCC.getSelectedIndex() == 0)
					tabPaneNCC.setSelectedIndex(1);
				else {
					if(tableNCC.getSelectedRow() == -1) {
						UIConstant.showInfo(QuanLyNhaCungCapPanel.this, "Chưa chọn nhà cung cấp");
						return;
					} else {
						if(validData()) {
							NhaCungCap ncc = new NhaCungCap(txtTenNCC.getText(),
									txtDiaChi.getText(), txtSoFax.getText());
							//							if(kiemtraTrungsoFax()==false) {
							//								UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Nhà cung cấp có số fax " + txtSoFax.getText() +" đã được sử dụng");
							//								txtSoFax.requestFocus();
							//								txtSoFax.selectAll();
							//								return;
							//							}
							String mancc = tableNCC.getValueAt(tableNCC.getSelectedRow(), 0).toString();
							ncc.setMaNCC(mancc);


							String tam =ncc.getSoFax();
							String tam1 =ncc.getTenNCC();
							for(NhaCungCap tenNhaCungCapTK: nccs) {
								if(!mancc.contains(tenNhaCungCapTK.getMaNCC()))
								{
									if(tam.contains(tenNhaCungCapTK.getSoFax())) 
									{
										UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Nhà cung cấp có số fax " + txtSoFax.getText() +" đã được sử dụng");
										txtSoFax.requestFocus();
										txtSoFax.selectAll();
										return;
									}
									else if(tam1.contains(tenNhaCungCapTK.getTenNCC()))
									{
										UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Nhà cung cấp có tên " + txtTenNCC.getText() +" đã tồn tại");
										txtTenNCC.requestFocus();
										txtTenNCC.selectAll();
										return;
									}
								}
							}






							try {
								if(nccDao.updateNhaCungCap(ncc)) {
									UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Sửa thành công");
									defaultTable.setValueAt(ncc.getMaNCC(), tableNCC.getSelectedRow(), 0);
									defaultTable.setValueAt(ncc.getTenNCC(), tableNCC.getSelectedRow(), 1);
									defaultTable.setValueAt(ncc.getDiaChi(), tableNCC.getSelectedRow(),2);
									defaultTable.setValueAt(ncc.getSoFax(), tableNCC.getSelectedRow(), 3);

									nccs.set(tableNCC.getSelectedRow() + currentIndex, ncc);
								} else
									UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Sửa không thành công");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});

		btnTKTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtTKTenNCC.getText().equals("") && txtTKDiaChi.getText().equals("") && txtTKSoFax.getText().equals(""))
				{
					UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Chưa nhập nội dung tìm kiếm");

				}
				else {
					timNCC();
				}
			}
		});

		btnQuayLai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCenter(mainFrame.getTrangChuPanel());
			}
		});
	}
	private void XoaRong() {
		txtMaNCC.setText("");
		txtTenNCC.setText("");
		txtSoFax.setText("");
		txtDiaChi.setText("");
		txtMaNCC.requestFocus();

		txtTKTenNCC.setText("");
		txtTKSoFax.setText("");
		txtTKDiaChi.setText("");

		ballDiaChi.setVisible(false);
		ballSoFax.setVisible(false);
		ballTenNCC.setVisible(false);

		txtTKTenNCC.requestFocus();
	}
	private boolean validData() {

		String tenNCC = txtTenNCC.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String soFax = txtSoFax.getText().trim();

		if(tenNCC.isEmpty() && diaChi.isEmpty() && soFax.isEmpty()) {
			UIConstant.showWarning(QuanLyNhaCungCapPanel.this, "Vui lòng nhập thông tin nhà cung cấp!");

		}

		//Tên nhà cung cấp: không được rỗng, không chứa ký tự đặc biệt
		if(tenNCC.isEmpty()) {
			ballTenNCC.setVisible(true);
			return false;
		} else
			ballTenNCC.setVisible(false);

		//Địa chỉ: Không được rỗng
		if(diaChi.isEmpty()) {
			ballDiaChi.setVisible(true);
			return false;
		}else
			ballDiaChi.setVisible(false);

		//Số fax: Không được rỗng, gồm 10 số, bắt đầu bằng số 0
		if(soFax.isEmpty() || !soFax.matches("0\\d{9}")) {
			ballSoFax.setVisible(true);
			return false;
		} else
			ballSoFax.setVisible(false);

		return true;
	}
	private void timNCC() {
		new Thread( () -> {
			List<NhaCungCap> list = new ArrayList<NhaCungCap>();

			try {
				list = nccDao.findNhaCungCap(txtTKTenNCC.getText(), txtTKDiaChi.getText(), txtTKSoFax.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(list.size() != 0) {
				taiDuLieuLenBang(list, 0);
			} else {
				nccs.clear();
				//				loadData();

				defaultTable.setRowCount(0);
			}
		}).start();
	}

	private boolean kiemtraTrungTen() throws Exception {
		List<NhaCungCap> list = new ArrayList<NhaCungCap>();
		System.out.println("1");
		list = nccDao.findNhaCungCapTrung(txtTenNCC.getText(), "", "");
		System.out.println("2");
		System.out.println(list);
		for(NhaCungCap tenNhaCungCapTK : list) {
			if(list.contains(tenNhaCungCapTK)) {
				System.out.println("3");
				return false;}
		}
		System.out.println("4");
		return true;
	}

	private boolean kiemtraTrungsoFax() throws Exception {
		List<NhaCungCap> list = new ArrayList<NhaCungCap>();
		System.out.println("3");
		list = nccDao.findNhaCungCapTrung("","", txtSoFax.getText());
		System.out.println("4");
		for(NhaCungCap tenNhaCungCapTK: list) {
			if(list.contains(tenNhaCungCapTK)) 
				return false;
		}
		return true;
	}


	//	@Override
	//	public void actionPerformed(ActionEvent e) {
	//		// TODO Auto-generated method stub
	//		addEvent();
	//	}


}