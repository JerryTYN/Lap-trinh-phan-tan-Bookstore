package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import entity.NhaCungCap;
import impl.NhaCungCaoImp;

public class NhaCungCapDAO extends UnicastRemoteObject implements NhaCungCaoImp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public NhaCungCapDAO() throws RemoteException {
		this.sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	@Override
	public List<NhaCungCap> get20NCC() throws RemoteException {

		List<NhaCungCap> list = new ArrayList<NhaCungCap>();

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			list = session.createNativeQuery("select  * from NhaCungCap order by maNCC desc", NhaCungCap.class)
					.getResultList();

			transaction.commit();

		} catch (Exception e) {

			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean addNhaCungCap(NhaCungCap nhaCC) throws RemoteException {
		boolean flag = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			session.save(nhaCC);
			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean deleteNhaCungCap(String maNhaCC) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {
			String sqlQuery = "delete from NhaCungCap where maNCC = :x";
			session.createNativeQuery(sqlQuery).setParameter("x", maNhaCC).executeUpdate();

			transaction.commit();
			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean updateNhaCungCap(NhaCungCap nhaCC) throws RemoteException {
		boolean flag = false;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {

			session.update(nhaCC);

			transaction.commit();

			flag = true;

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public List<NhaCungCap> findNhaCungCap(String tenncc, String diachi, String sofax) throws RemoteException {
		List<NhaCungCap> list = new ArrayList<NhaCungCap>();

		if (tenncc.trim().equals(""))
			tenncc = null;
		if (diachi.trim().equals(""))
			diachi = null;
		if (sofax.trim().equals(""))
			sofax = null;

		String query = "select * from NhaCungCap ";

		if (tenncc != null || diachi != null || sofax != null)
			query += "where ";

		if (tenncc != null)
			query += "tenNCC like :ten ";
		if (diachi != null) {
			if (tenncc == null)
				query += "diaChi like :dc ";
			else
				query += "and diaChi like :dc ";
		}
		if (sofax != null) {
			if (tenncc == null && diachi == null)
				query += "soFax like :fax ";
			else
				query += "and soFax like :fax ";
		}

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {

			NativeQuery<NhaCungCap> nativeQuery = session.createNativeQuery(query, NhaCungCap.class);
			if (tenncc != null)
				nativeQuery.setParameter("ten", "%" + tenncc + "%");
			if (diachi != null)
				nativeQuery.setParameter("dc", "%" + diachi + "%");
			if (sofax != null)
				nativeQuery.setParameter("fax", "%" + sofax + "%");

			list = nativeQuery.getResultList();

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<NhaCungCap> findNhaCungCapTrung(String tenncc, String diachi, String sofax) throws RemoteException {
		List<NhaCungCap> list = new ArrayList<NhaCungCap>();

		if (tenncc.trim().equals(""))
			tenncc = null;
		if (diachi.trim().equals(""))
			diachi = null;
		if (sofax.trim().equals(""))
			sofax = null;

		String query = "select * from NhaCungCap ";

		if (tenncc != null || diachi != null || sofax != null)
			query += "where ";

		if (tenncc != null)
			query += "tenNCC like :ten ";
		if (diachi != null) {
			if (tenncc == null)
				query += "diaChi like :dc ";
			else
				query += "and diaChi like :dc ";
		}
		if (sofax != null) {
			if (tenncc == null && diachi == null)
				query += "soFax like :fax ";
			else
				query += "and soFax like :fax ";
		}

		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();

		try {

			NativeQuery<NhaCungCap> nativeQuery = session.createNativeQuery(query, NhaCungCap.class);
			if (tenncc != null)
				nativeQuery.setParameter("ten", tenncc);
			if (diachi != null)
				nativeQuery.setParameter("dc", diachi);
			if (sofax != null)
				nativeQuery.setParameter("fax", sofax);

			list = nativeQuery.getResultList();

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return list;
	}

}
