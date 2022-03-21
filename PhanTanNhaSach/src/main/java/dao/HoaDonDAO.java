package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.spi.QueryImplementor;

import entity.CTHoaDon;
import entity.HoaDon;
import impl.HoaDonImp;
import impl.MaTuDongImp;

public class HoaDonDAO extends UnicastRemoteObject implements HoaDonImp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public HoaDonDAO() throws RemoteException {
		sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	@Override
	public boolean lapHoaDon(HoaDon hoaDon) throws RemoteException {
		boolean result = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			session.save(hoaDon);

			tran.commit();
			result = true;

		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public double tinhTongTien(HoaDon hoaDon) throws RemoteException {
		double result = 0;

		for (CTHoaDon item : new CTHoaDonDAO().getDsCTHD(hoaDon))
			result += item.tinhThanhTien();

		return result;
	}

	@Override
	public List<HoaDon> lay20HoaDonGanDay() throws RemoteException {
		List<HoaDon> result = new ArrayList<HoaDon>();

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			result = session.createNativeQuery("select  * from HoaDon order by maHoaDon desc", HoaDon.class)
					.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<HoaDon> timHoaDon(String ma, String tenKH, String tenNV, LocalDate ngayLap) throws RemoteException {
		List<HoaDon> result = new ArrayList<HoaDon>();

		String ngay = null;

		if (ma.trim().equals(""))
			ma = null;
		if (tenKH.trim().equals(""))
			tenKH = null;
		if (tenNV.trim().equals(""))
			tenNV = null;
		if (ngayLap != null)
			ngay = ngayLap.toString();

		String query = "select * from HoaDon hd left outer join KhachHang kh on hd.maKhachHang = kh.maKhachHang left outer join NhanVien nv on hd.maNhanVien = nv.maNhanVien ";

		if (ma != null || tenKH != null || tenNV != null || ngay != null)
			query += "where ";

		if (ma != null)
			query += "maHoaDon like :ma ";
		if (tenKH != null) {
			if (ma == null)
				query += "tenKhachHang like :tenKH ";
			else
				query += "and tenKhachHang like :tenKH ";
		}
		if (tenNV != null) {
			if (ma == null && tenKH == null)
				query += "tenNhanVien like :tenNV ";
			else
				query += "and tenNhanVien like :tenNV ";
		}
		if (ngay != null) {
			if (ma == null && tenKH == null && tenNV == null)
				query += "ngayLap = :ngay ";
			else
				query += "and ngayLap = :ngay ";
		}

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			NativeQuery<HoaDon> nativeQuery = session.createNativeQuery(query, HoaDon.class);
			if (ma != null)
				nativeQuery.setParameter("ma", "%" + ma + "%");
			if (tenKH != null)
				nativeQuery.setParameter("tenKH", "%" + tenKH + "%");
			if (tenNV != null)
				nativeQuery.setParameter("tenNV", "%" + tenNV + "%");
			if (ngay != null)
				nativeQuery.setParameter("ngay", ngay);

			result = nativeQuery.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Object[] thongKeHoaDon(LocalDate fromDate, LocalDate toDate) throws RemoteException {
		Object[] result = new Object[2];

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			List<HoaDon> list = session
					.createNativeQuery("select * from HoaDon where ngayLap >= :x and ngayLap <= :y order by ngayLap",
							HoaDon.class)
					.setParameter("x", fromDate.toString()).setParameter("y", toDate.toString()).getResultList();

			double sum = (double) session
					.createNativeQuery("select SUM(donGia * soLuong) from CTHoaDon "
							+ "where maHoaDon in (select maHoaDon from HoaDon where ngayLap >= :x and ngayLap <= :y)")
					.setParameter("x", fromDate.toString()).setParameter("y", toDate.toString()).getSingleResult();

			result[0] = list;
			result[1] = sum;

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
		}

		return result;
	}

	@Override
	public String phatSinhMaTuDong() throws RemoteException {
		// TODO Auto-generated method stub
		String maHoaDon = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.beginTransaction();

		try {
//			tran.begin();
			String sql = "SELECT MAX(hd.maHoaDon) FROM HoaDon hd";
			String maHoaDon1 = (String) session.createNativeQuery(sql).getSingleResult();
			maHoaDon = fomatAA000000(maHoaDon1);
			
			tran.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			tran.rollback();
		} 
		return maHoaDon;
	}
	
	public String fomatAA000000(String s) throws RemoteException {
		int so = Integer.parseInt(s.substring(2));
		String stringSo = "000000";
		String ma1 = s.substring(0, 1);
		String ma2 = s.substring(1, 2);
		
		
		
		if (so >= 999999) {
			if (ma2.equalsIgnoreCase("Z")) {
				if (!ma1.equalsIgnoreCase("Z")) {
					char a  =(char) ((int) ma1.charAt(0)+1);
					ma1 = String.valueOf(a);
					ma2 ="A";			
				} else {
					System.err.println("Da toi gioi han ma");
					return null;
				}
			}else {
				// chua dat toi gioi han
				char a  =(char) ((int) ma2.charAt(0)+1);
				ma2 = String.valueOf(a);
			}
		}else {
			 stringSo = String.format("%06d", so + 1);
		}
		return (ma1 + ma2 + stringSo);
	}


}
