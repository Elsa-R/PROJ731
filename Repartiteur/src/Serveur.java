import java.io.*;
import java.net.*; //pour avoir les Sockets
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur implements Machine {
	private String nom;
	
	public Serveur(String nom) throws RemoteException {
	this.nom = nom;
	}
	
	// Retourne le contenu du fichier texte
	public byte[] lecture(String fichier) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fichier));
		byte[] texte = new byte[]{};
		byte[] texteFinal = new byte[]{};
		String line;
		// Tant qu'il reste une ligne dans le fichier :
		while ((line = in.readLine()) != null){
			texteFinal = new byte[texte.length + line.getBytes().length];
			System.arraycopy(texte, 0, texteFinal, 0, texte.length);
			System.arraycopy(line.getBytes(), 0, texteFinal, texte.length, line.getBytes().length);
			texte = texteFinal;
		}
		in.close();
		return texteFinal;
	}
	
	// Ecrit dans le fichier et retourne true si l'ecriture c'est bien passee
	public Boolean ecriture(String fichier, byte[] donnees) throws IOException {
		PrintWriter sortie = new PrintWriter(fichier,"UTF-8");
		sortie.println(donnees);
		sortie.close();
		return true;
	}

	public static void main(String args[]) {
		try {
					
			// Creation d'un server de type Machine
			Machine m1 = new Serveur("Bob");
			
			// Exportation du server et registration
			UnicastRemoteObject.exportObject(m1, 0);
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry = LocateRegistry.getRegistry("localhost");
			registry.bind("Bob", m1);
			
			// Creation d'un aiguilleur de type Machine						
			Aiguilleur a1 = new Aiguilleur("Aiguilleur",m1);
								
			// Exportation de l'aiguilleur et registration
			UnicastRemoteObject.exportObject(a1, 0);
			registry.bind("Aiguilleur", a1);
			
			} catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
		    }
	}
}