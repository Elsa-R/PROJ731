import java.io.*;
import java.net.*; //pour avoir les Sockets
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class Serveur implements Machine {
	private String nom;
	private static Object sync = new Object();
	private static Hashtable<String, Object> dic = new Hashtable<String,Object>();
	
	public Serveur(String nom) throws RemoteException {
		this.nom = nom;
		System.out.println("Serveur " + nom + " is running...");
	}
	
	private Object  getSyncForFile(String fichier) {
		synchronized(sync) {
			Object object = dic.get(fichier);
			if (object == null){
				object = new Object();
				dic.put(fichier, object);
			}
			return object;
		}
	}
	
	// Retourne le contenu du fichier texte
	public String lecture(String fichier) throws IOException {
		synchronized(this.getSyncForFile(fichier)) {
			System.out.println("Serveur >" + this.nom + " lecture fichier = " + fichier);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader in = new BufferedReader(new FileReader(fichier));
			String texte = "";
			String texteFinal = "";
			String line;
			// Tant qu'il reste une ligne dans le fichier :
			while ((line = in.readLine()) != null){
				texteFinal = texte + line;
				texte = texteFinal;
			}
			in.close();
			System.out.println("Serveur <" + this.nom + " lecture fichier = " + fichier);
			return texteFinal;
		}
	}
	
	// Ecrit dans le fichier et retourne true si l'ecriture c'est bien passee
	public Boolean ecriture(String fichier, String donnees) throws IOException {
		synchronized(this.getSyncForFile(fichier)) {
			System.out.println("Serveur >" + this.nom + " écriture fichier = " + fichier);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			FileWriter sortie = new FileWriter(fichier,true);
			sortie.write(donnees);
			sortie.close();
			System.out.println("Serveur <" + this.nom + " écriture fichier = " + fichier);
			return true;
		}
	}
	
	public String getNom() throws RemoteException{
		return nom;
	}

	public static void main(String args[]) {
		try {
			// Creation d'un server de type Machine
			Machine m1 = new Serveur("Bob");
			Machine m2 = new Serveur("Maurice");
			
			// Exportation du server et registration
			UnicastRemoteObject.exportObject(m1, 0);
			UnicastRemoteObject.exportObject(m2, 0);
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry.bind("Bob", m1);
			registry.bind("Maurice", m2);
			
			// Creation d'un aiguilleur de type Machine						
			//Aiguilleur a1 = new Aiguilleur("Aiguilleur");
	//		a1.addMachine(m1);
		//	a1.addMachine(m2);
								
			// Exportation de l'aiguilleur et registration
	//		UnicastRemoteObject.exportObject(a1, 0);
		//	registry.bind("Aiguilleur", a1);
			
			} catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
		    }
		System.out.println("End of main");
	}
}