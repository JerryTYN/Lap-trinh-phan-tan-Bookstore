package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import entity.HoaDon;

public interface HoaDonImp extends Remote {
	public boolean lapHoaDon(HoaDon hoaDon) throws RemoteException;

	public double tinhTongTien(HoaDon hoaDon) throws RemoteException;

	public List<HoaDon> lay20HoaDonGanDay() throws RemoteException;

	public List<HoaDon> timHoaDon(String ma, String tenKH, String tenNV, LocalDate ngayLap) throws RemoteException;

	public Object[] thongKeHoaDon(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	
	public String phatSinhMaTuDong() throws RemoteException;
}
