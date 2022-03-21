package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.KhachHang;

public interface KhachHangImp extends Remote {
	public List<KhachHang> layDSKhachHangGanDay() throws RemoteException;

	public boolean addKhachHang(KhachHang kh) throws RemoteException;

	public boolean xoaKhachHang(String maKH) throws RemoteException;

	public boolean capNhatKhachHang(KhachHang khachHang) throws RemoteException;

	public List<KhachHang> timKhachHang(String tenKH, String diaChi, String email, String sdt) throws RemoteException;

	public List<KhachHang> timKhachHangTrung(String tenKH, String diaChi, String email, String sdt)
			throws RemoteException;
}
