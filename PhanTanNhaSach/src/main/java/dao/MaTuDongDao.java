package dao;

import java.rmi.RemoteException;
import java.util.stream.Stream;

import org.hibernate.query.spi.QueryImplementor;

import impl.MaTuDongImp;

public class MaTuDongDao implements MaTuDongImp{
	

	@Override
	public String fomatAA000000(String s) throws RemoteException {
		int so = Integer.parseInt(s.substring(2));
		String stringSo = "00000";
		String ma1 = s.substring(0, 1);
		String ma2 = s.substring(1, 2);
		
		
		
		if (so >= 999999) {
			if (ma2.equalsIgnoreCase("Z")) {
				if (!ma1.equalsIgnoreCase("Z")) {
					char a  =(char) ((int) ma1.charAt(0)+1);
					ma1 = String.valueOf(a);
					ma2 ="A";			
				} else {
					System.err.println("Da toi gioi han ma");
					return null;
				}
			}else {
				// chua dat toi gioi han
				char a  =(char) ((int) ma2.charAt(0)+1);
				ma2 = String.valueOf(a);
			}
		}else {
			 stringSo = String.format("%05d", so + 1);
		}
		return (ma1 + ma2 + stringSo);
	}

}
