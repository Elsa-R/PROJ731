import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			Machine m1 = (Machine) registry.lookup("Bob");
			System.out.println("L'ecriture s'est bien faite : " + m1.ecriture("slovenie.txt", new byte[]{(byte)00001111}));
			System.out.println("Contenu du fichier : \n" + m1.lecture("slovenie.txt"));
		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
	}
}