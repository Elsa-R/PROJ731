import java.io.*;
import java.net.*; //pour avoir les Sockets
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Machine {
	private String nom;
	
	public Server(String nom) throws RemoteException {
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
			Machine m1 = new Server("Bob");
			
			// Exportation du server et registration
			UnicastRemoteObject.exportObject(m1, 0);
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry.bind("Bob", m1);
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
		}
	}
}