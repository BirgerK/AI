package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.Monitor;

public class MPS {
	  public static void main( String[] args ) throws  RemoteException, NotBoundException
	  {
	    Registry registry = LocateRegistry.getRegistry();
	    Monitor adder = (Monitor) registry.lookup( "Monitor" );
	    adder.register("LEONIDAS");
	  }
}
