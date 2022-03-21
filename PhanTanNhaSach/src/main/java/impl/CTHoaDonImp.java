package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.CTHoaDon;
import entity.HoaDon;

public interface CTHoaDonImp extends Remote {
	public boolean themCTHoaDon(CTHoaDon ctHoaDon) throws RemoteException;

	public List<CTHoaDon> getDsCTHD(HoaDon hoaDon) throws RemoteException;
}
