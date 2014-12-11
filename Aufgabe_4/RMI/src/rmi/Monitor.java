package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Monitor extends Remote{
	public void THIIIS_IIS_MPS(String name) throws RemoteException;
	public void register(String name) throws RemoteException;
}
