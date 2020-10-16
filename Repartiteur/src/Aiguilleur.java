import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

public class Aiguilleur implements AiguilleurInterface {
	private Machine machine;
	private String nom;
	
	public Aiguilleur(String nom, Machine m) throws IOException {
		this.nom=nom;
		this.addMachine(m);
	}

	public byte[] lecture(String donnees) throws RemoteException, FileNotFoundException, IOException {
		return machine.lecture(donnees);
	}

	public Boolean ecriture(String nom, byte[] donnees) throws RemoteException, FileNotFoundException, IOException {
		return machine.ecriture(nom, donnees);
	}

	public boolean addMachine(Machine m) throws RemoteException {
		machine = m;
		return true;
	}

	@Override
	public boolean removeMachine(Machine m) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean notification(Machine m, int a) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
