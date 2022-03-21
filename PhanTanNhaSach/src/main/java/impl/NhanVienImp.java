package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import entity.NhanVien;

public interface NhanVienImp extends Remote {
	public List<NhanVien> lay20NhanVienGanDay() throws RemoteException;

	public NhanVien getNhanVien(String manv) throws RemoteException;

	public boolean addNhanVien(NhanVien nv) throws RemoteException;

	public boolean deleteNhanVien(String maNV) throws RemoteException;

	public boolean updateNhanVien(NhanVien nv) throws RemoteException;

	public List<NhanVien> findNhanVien(String tennv, int trangThai, String email, String sdt, LocalDate ngayvaolam,
			int loainv) throws RemoteException;
	
	public NhanVien kiemTraDangNhap(String userName, String password) throws RemoteException;
	
	public List<NhanVien> findNhanVienTrung(String email, String sdt) throws RemoteException;
}
