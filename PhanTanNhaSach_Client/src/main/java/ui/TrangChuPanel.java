/*
 * Võ Thành Nhớ
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.Box.Filler;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.FontUIResource;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

public class TrangChuPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	public TrangChuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
		
	}

	private void initComponents() {
		//
		
		JPanel pnlMain = new JPanel();
		JPanel pnlC = new JPanel();
		JPanel pnlE = new JPanel();
		JPanel pnlD = new JPanel();
		JPanel pnlA = new JPanel();
		JLabel lbIcon = new JLabel();
		JLabel lbNhaSach = new JLabel();
		JLabel lbDescription = new JLabel();
		JLabel lbImg1 = new JLabel();
		JLabel lbImg2 = new JLabel();
		JLabel lbName = new JLabel();
		
//		setLookAndFeel();
		


		setBackground(new java.awt.Color(255, 255, 255));
		setLayout(new java.awt.BorderLayout());
		
		pnlMain.setOpaque(false);
		pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));

		pnlC.setOpaque(false);
		pnlC.setLayout(new BoxLayout(pnlC,BoxLayout.LINE_AXIS));

		lbIcon.setIcon(new ImageIcon("img/logo.jpg"));
		
		pnlC.add(lbIcon);

		pnlE.setOpaque(false);
		pnlE.setLayout(new javax.swing.BoxLayout(pnlE, javax.swing.BoxLayout.Y_AXIS));

		lbNhaSach.setFont(new Font("Forte", 0, 36)); // NOI18N
		lbNhaSach.setForeground(new Color(255,127,0));
		lbNhaSach.setText("SachHay");
		
		pnlE.add(lbNhaSach);

		lbDescription.setText("Hệ Thống Quản Lý Nhà Sách");
		lbDescription.setFont(new Font("Times New Roman", 0, 20));
		lbDescription.setForeground(new Color(255,127,0));
		pnlE.add(lbDescription);

		pnlC.add(pnlE);
		pnlC.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		pnlMain.add(pnlC);
		pnlMain.add(new Box.Filler(new Dimension(0, 20), new Dimension(0, 20), new Dimension(32767,20)));
		
	
		pnlD.add(lbName);
		pnlD.setOpaque(false);
		pnlD.setLayout(new BoxLayout(pnlD,BoxLayout.LINE_AXIS));
		lbName.setText("SachHay Store");
		lbName.setFont(new Font("Forte", 0, 60));
		lbName.setForeground(new Color(255,127,0));
		pnlMain.add(pnlD);
		add(pnlMain,BorderLayout.NORTH);

		pnlA.setOpaque(false);
		pnlA.setLayout(new javax.swing.BoxLayout(pnlA, BoxLayout.LINE_AXIS));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767)));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));
		lbImg1.setIcon(new ImageIcon("img/backroudleft.jpg")); 
		pnlA.add(lbImg1);
		pnlA.add(new Box.Filler(new Dimension(20, 0), new Dimension(20,0), new java.awt.Dimension(20, 32767)));

		lbImg2.setIcon(new javax.swing.ImageIcon("img/backroundright.jpg")); // NOI18N
		pnlA.add(lbImg2);
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767)));

		add(pnlA,BorderLayout.CENTER);

	}




}
