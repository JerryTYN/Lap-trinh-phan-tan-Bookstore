package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.CTHoaDon;
import entity.HoaDon;
import impl.CTHoaDonImp;

public class CTHoaDonDAO extends UnicastRemoteObject implements CTHoaDonImp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public CTHoaDonDAO() throws RemoteException {
		sessionFactory = DatabaseConnection.getInstance().getSessionFactory();
	}

	@Override
	public boolean themCTHoaDon(CTHoaDon ctHoaDon) throws RemoteException {
		boolean result = false;

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			session.save(ctHoaDon);

			tran.commit();
			result = true;

		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<CTHoaDon> getDsCTHD(HoaDon hoaDon) throws RemoteException {
		List<CTHoaDon> result = new ArrayList<CTHoaDon>();

		Session session = sessionFactory.getCurrentSession();

		Transaction tran = session.beginTransaction();

		try {
			result = session.createNativeQuery("select * from CTHoaDon where maHoaDon = :x", CTHoaDon.class)
					.setParameter("x", hoaDon.getMaHoaDon()).getResultList();

			tran.commit();

		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		}

		return result;
	}

}
