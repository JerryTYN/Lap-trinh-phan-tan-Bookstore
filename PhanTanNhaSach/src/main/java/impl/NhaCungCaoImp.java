package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.NhaCungCap;

public interface NhaCungCaoImp extends Remote {
	public List<NhaCungCap> get20NCC() throws RemoteException;

	public boolean addNhaCungCap(NhaCungCap nhaCC) throws RemoteException;

	public boolean deleteNhaCungCap(String maNhaCC) throws RemoteException;

	public boolean updateNhaCungCap(NhaCungCap nhaCC) throws RemoteException;

	public List<NhaCungCap> findNhaCungCap(String tenncc, String diachi, String sofax) throws RemoteException;
	
	public List<NhaCungCap> findNhaCungCapTrung(String tenncc, String diachi, String sofax) throws RemoteException;
}
