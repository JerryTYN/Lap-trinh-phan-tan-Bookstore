package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import entity.SanPham;
import impl.SanPhamImp;

public class SanPhamDao extends UnicastRemoteObject implements SanPhamImp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public SanPhamDao() throws RemoteException {
		super();
		this.sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	@Override
	public List<SanPham> lay20SanPhamGanDay() throws RemoteException {
		List<SanPham> list = new ArrayList<SanPham>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			list = session.createNativeQuery("select  * from SanPham order by maSanPham desc", SanPham.class)
					.getResultList();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<String> getDSPhanLoai() throws RemoteException {
		List<String> list = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {

			List<?> resultList = session
					.createNativeQuery("select phanLoai from SanPham where phanLoai not like '' group by phanLoai")
					.getResultList();
			for (Object item : resultList) {
				list.add((String) item);
			}

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean xoaSanPham(String maSanPham) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			String sqlQuery = "delete from SanPham where maSanPham = :x";
			session.createNativeQuery(sqlQuery).setParameter("x", maSanPham).executeUpdate();

			transaction.commit();
			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean themSanPham(SanPham sanpham) throws RemoteException {

		boolean flag = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.save(sanpham);
			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean capNhatSanPham(SanPham sanPham) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.update(sanPham);

			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public List<SanPham> timSanPham(String tenSanPham, String nhaCungCap, String tacGia, String theLoai)
			throws RemoteException {
		List<SanPham> result = new ArrayList<SanPham>();
		if (tenSanPham.trim().equals(""))
			tenSanPham = null;
		if (nhaCungCap.trim().equals(""))
			nhaCungCap = null;
		if (tacGia.trim().equals(""))
			tacGia = null;
		if (theLoai.trim().equals(""))
			theLoai = null;

		String queryNCC = "select * from SanPham INNER JOIN NhaCungCap ON SanPham.maNCC = NhaCungCap.maNCC ";

		if (tenSanPham != null || nhaCungCap != null || tacGia != null || theLoai != null) {

			queryNCC += "where ";

			if (tenSanPham != null)
				queryNCC += "tenSanPham like :ten ";
			if (nhaCungCap != null) {
				if (tenSanPham == null)
					queryNCC += "tenNCC like :ncc ";
				else
					queryNCC += "and tenNCC like :ncc ";
			}
			if (tacGia != null) {
				if (tenSanPham == null && nhaCungCap == null)
					queryNCC += "tacGia like :tg ";
				else
					queryNCC += "and tacGia like :tg ";
			}
			if (theLoai != null) {
				if (tenSanPham == null && nhaCungCap == null && tacGia == null)
					queryNCC += "theLoai like :tl ";
				else
					queryNCC += "and theLoai like :tl ";
			}

		}

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			NativeQuery<SanPham> nativeQuery = session.createNativeQuery(queryNCC, SanPham.class);
			if (tenSanPham != null)
				nativeQuery.setParameter("ten", "%" + tenSanPham + "%");
			if (nhaCungCap != null)
				nativeQuery.setParameter("ncc", "%" + nhaCungCap + "%");
			if (tacGia != null)
				nativeQuery.setParameter("tg", "%" + tacGia + "%");
			if (theLoai != null)
				nativeQuery.setParameter("tl", "%" + theLoai + "%");

			result = nativeQuery.getResultList();
			tran.commit();

		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public Object[] thongKeSanPham(boolean isHetHang, boolean isSapHet, boolean isChuaBanLanNao)
			throws RemoteException {
		Object[] result = new Object[4];

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			int soHetHang = 0;
			int soSapHet = 0;
			int soChuaBanLanNao = 0;

			if (isHetHang)
				soHetHang = (int) session.createNativeQuery(
						"select COUNT(maSanPham) from SanPham where maSanPham in (select maSanPham from SanPham where soLuong = 0)")
						.getSingleResult();

			if (isSapHet)
				soSapHet = (int) session.createNativeQuery(
						"select COUNT(maSanPham) from SanPham where maSanPham in (select maSanPham from SanPham where soLuong > 0 and soLuong <= 10)")
						.getSingleResult();

			if (isChuaBanLanNao)
				soChuaBanLanNao = (int) session.createNativeQuery(
						"select COUNT(maSanPham) from SanPham where maSanPham not in (select maSanPham from CTHoaDon)")
						.getSingleResult();

			String query = "select * from SanPham where ";
			if (isHetHang)
				query += "maSanPham in (select maSanPham from SanPham where soLuong = 0)";

			if (isSapHet) {
				if (!isHetHang)
					query += "maSanPham in (select maSanPham from SanPham where soLuong > 0 and soLuong <= 10)";
			}
			if (isChuaBanLanNao) {
				if (isHetHang || isSapHet)
					query += "or maSanPham not in (select maSanPham from CTHoaDon)";
				else
					query += "maSanPham not in (select maSanPham from CTHoaDon)";

			}

			List<SanPham> resultList = session.createNativeQuery(query, SanPham.class).getResultList();

			result[0] = soHetHang;
			result[1] = soSapHet;
			result[2] = soChuaBanLanNao;
			result[3] = resultList;

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
		}

		return result;
	}

}
