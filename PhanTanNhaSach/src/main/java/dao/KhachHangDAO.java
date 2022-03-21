package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import entity.KhachHang;
import impl.KhachHangImp;

public class KhachHangDAO extends UnicastRemoteObject implements KhachHangImp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public KhachHangDAO() throws RemoteException {
		super();
		this.sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	@Override
	public List<KhachHang> layDSKhachHangGanDay() throws RemoteException {
		List<KhachHang> result = new ArrayList<KhachHang>();

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			result = session.createNativeQuery("select  * from KhachHang order by maKhachHang desc", KhachHang.class)
					.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean addKhachHang(KhachHang kh) throws RemoteException {
		boolean flag = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.save(kh);
			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean xoaKhachHang(String maKH) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			String sqlQuery = "delete from KhachHang where maKhachHang = :x";
			session.createNativeQuery(sqlQuery).setParameter("x", maKH).executeUpdate();

			transaction.commit();
			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean capNhatKhachHang(KhachHang khachHang) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.update(khachHang);

			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}
//	public List<KhachHang> timKhachHang(String ten, String ngaysinh, String diachi, String gioitinh, String email, String sdt){
//		List<KhachHang> list = new ArrayList<KhachHang>();
//		
//		Session session = sessionFactory.getCurrentSession();
//		
//		Transaction transaction = session.beginTransaction();
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
//        String strDate = dateFormat.format(date);  
//        System.out.println("Converted String: " + strDate);
//        
////        String cr7 = "";
////        if(email.isBlank()) {
////        	cr7 = "cr7";
////        }
////        System.out.println(email);
////        System.out.println(cr7);
//        System.out.println(email);
//		
//		try {
//			
//			
//			String sqlQuery = "select * from KhachHang where "
//					+ "(tenKhachHang like :a or :a = '') "
//					+ "and (ngaySinh = :b or :b = '' or :b = "+strDate+") ";
////					+ "and (diaChi like :c or :c = '') ";
////					+ "and (gioiTinh = :d or :d = '') ";
////					+ "and (email = :e or :email is null) "
////					+ "and (soDienThoai = :f or :f = '')";
//			
//			list = session.createNativeQuery(sqlQuery,KhachHang.class)
//			.setParameter("a",'%' + ten + '%')
//			.setParameter("b", ngaysinh)
////			.setParameter("c", '%' + diachi + '%')
////			.setParameter("d", gioitinh)
////			.setParameter("e", email)
////			.setParameter("f", sdt)
//			.getResultList();
//			
//			transaction.commit();
//			
//		} catch (Exception e) {
//			transaction.rollback();
//			e.printStackTrace();
//		}
//		
//		return list;
//	}

	@Override
	public List<KhachHang> timKhachHang(String tenKH, String diaChi, String email, String sdt) throws RemoteException {
		List<KhachHang> result = new ArrayList<KhachHang>();

		if (tenKH.trim().equals(""))
			tenKH = null;
		if (diaChi.trim().equals(""))
			diaChi = null;
		if (email.trim().equals(""))
			email = null;
		if (sdt.trim().equals(""))
			sdt = null;

		String query = "select * from KhachHang ";

		if (tenKH != null || diaChi != null || email != null || sdt != null)
			query += "where ";

		if (tenKH != null)
			query += "tenKhachHang like :ten ";
		if (diaChi != null) {
			if (tenKH == null)
				query += "diaChi like :dc ";
			else
				query += "and diaChi like :dc ";
		}
		if (email != null) {
			if (tenKH == null && diaChi == null)
				query += "email like :email ";
			else
				query += "and email like :email ";
		}
		if (sdt != null) {
			if (tenKH == null && diaChi == null && email == null)
				query += "soDienThoai like :sdt ";
			else
				query += "and soDienThoai like :sdt ";
		}

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			NativeQuery<KhachHang> nativeQuery = session.createNativeQuery(query, KhachHang.class);
			if (tenKH != null)
				nativeQuery.setParameter("ten", "%" + tenKH + "%");
			if (diaChi != null)
				nativeQuery.setParameter("dc", "%" + diaChi + "%");
			if (email != null)
				nativeQuery.setParameter("email", "%" + email + "%");
			if (sdt != null)
				nativeQuery.setParameter("sdt", "%" + sdt + "%");

			result = nativeQuery.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<KhachHang> timKhachHangTrung(String tenKH, String diaChi, String email, String sdt)
			throws RemoteException {
		List<KhachHang> result = new ArrayList<KhachHang>();

		if (tenKH.trim().equals(""))
			tenKH = null;
		if (diaChi.trim().equals(""))
			diaChi = null;
		if (email.trim().equals(""))
			email = null;
		if (sdt.trim().equals(""))
			sdt = null;

		String query = "select * from KhachHang ";

		if (tenKH != null || diaChi != null || email != null || sdt != null)
			query += "where ";

		if (tenKH != null)
			query += "tenKhachHang like :ten ";
		if (diaChi != null) {
			if (tenKH == null)
				query += "diaChi like :dc ";
			else
				query += "and diaChi like :dc ";
		}
		if (email != null) {
			if (tenKH == null && diaChi == null)
				query += "email like :email ";
			else
				query += "and email like :email ";
		}
		if (sdt != null) {
			if (tenKH == null && diaChi == null && email == null)
				query += "soDienThoai like :sdt ";
			else
				query += "and soDienThoai like :sdt ";
		}

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			NativeQuery<KhachHang> nativeQuery = session.createNativeQuery(query, KhachHang.class);
			if (tenKH != null)
				nativeQuery.setParameter("ten", "%" + tenKH + "%");
			if (diaChi != null)
				nativeQuery.setParameter("dc", "%" + diaChi + "%");
			if (email != null)
				nativeQuery.setParameter("email", email);
			if (sdt != null)
				nativeQuery.setParameter("sdt", "%" + sdt + "%");

			result = nativeQuery.getResultList();

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

}
