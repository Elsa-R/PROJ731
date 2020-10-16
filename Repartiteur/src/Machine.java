import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
	public byte[] lecture(String donnees) throws RemoteException, FileNotFoundException, IOException;
	public Boolean ecriture(String nom, byte[] donnees) throws RemoteException, FileNotFoundException, IOException;
}
