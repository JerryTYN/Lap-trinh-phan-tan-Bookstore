package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.SanPham;

public interface SanPhamImp extends Remote {
	public List<SanPham> lay20SanPhamGanDay() throws RemoteException;

	public List<String> getDSPhanLoai() throws RemoteException;

	public boolean xoaSanPham(String maSanPham) throws RemoteException;

	public boolean themSanPham(SanPham sanpham) throws RemoteException;

	public boolean capNhatSanPham(SanPham sanPham) throws RemoteException;

	public List<SanPham> timSanPham(String tenSanPham, String nhaCungCap, String tacGia, String theLoai)
			throws RemoteException;

	public Object[] thongKeSanPham(boolean isHetHang, boolean isSapHet, boolean isChuaBanLanNao) throws RemoteException;
}
