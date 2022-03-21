package app;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import dao.CTHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhaCungCapDAO;
import dao.NhanVienDao;
import dao.SanPhamDao;
import impl.CTHoaDonImp;
import impl.HoaDonImp;
import impl.KhachHangImp;
import impl.NhaCungCaoImp;
import impl.NhanVienImp;
import impl.SanPhamImp;


public class ServerApp {
	public static void main(String[] args) {
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
		}

		try {
			
			SanPhamImp spDao = new SanPhamDao();
			NhanVienImp nvDao = new NhanVienDao();
			KhachHangImp khDao = new KhachHangDAO();
			NhaCungCaoImp nccDao = new NhaCungCapDAO();
			HoaDonImp hdDao = new HoaDonDAO();
			CTHoaDonImp cthdDao = new CTHoaDonDAO();
			LocateRegistry.createRegistry(9090);

			Naming.bind("rmi://127.0.0.1:9090/spDao", spDao);
			Naming.bind("rmi://127.0.0.1:9090/nvDao", nvDao);
			Naming.bind("rmi://127.0.0.1:9090/khDao", khDao);
			Naming.bind("rmi://127.0.0.1:9090/nccDao", nccDao);
			Naming.bind("rmi://127.0.0.1:9090/hdDao", hdDao);
			Naming.bind("rmi://127.0.0.1:9090/cthdDao", cthdDao);
			
			System.out.println("Server bound in RMI");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
//cthdDao = (CTHoaDonImp) Naming.lookup(Port.PORT + "cthdDao");
//nccDao = (NhaCungCaoImp) Naming.lookup(Port.PORT + "nccDao");
//nvDao = (NhanVienImp) Naming.lookup(Port.PORT + "nvDao");
//khDao = (KhachHangImp) Naming.lookup(Port.PORT + "khDao");
//hdDao = (HoaDonImp) Naming.lookup(Port.PORT + "hdDao");
//spDao = (SanPhamImp) Naming.lookup(Port.PORT + "spDao");
