import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Aiguilleur implements AiguilleurInterface {
	private  Hashtable<Machine, Integer> dicCharge = new Hashtable<Machine,Integer>();
	private ArrayList<Machine> listeMachine = new ArrayList<Machine>();
	private String nom;
	
	public Aiguilleur(String nom) throws IOException {
		this.nom=nom;
	}

	public String lecture(String donnees) throws RemoteException, FileNotFoundException, IOException {
		Machine machine = this.getMachine();
		System.out.println("Aiguilleur " + machine.getNom() + " lecture fichier = " + donnees);
		String result = machine.lecture(donnees);
		releaseMachine(machine);
		return ""+machine.getNom()+" : "+result;
	}

	public Boolean ecriture(String nom, String donnees) throws RemoteException, FileNotFoundException, IOException {
		Machine machine = this.getMachine();
		System.out.println("Aiguilleur " + machine.getNom() + " écriture fichier = " + nom);
		Boolean result = machine.ecriture(nom,""+machine.getNom()+" : "+donnees);
		releaseMachine(machine);
		return result;
	}

	public boolean addMachine(Machine m) throws RemoteException {
		this.dicCharge.put(m,0);
		return true;
	}

	public boolean removeMachine(Machine m) throws RemoteException {
		this.dicCharge.remove(m);
		return true;
	}

	@Override
	public boolean notification(Machine m, int a) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
	private synchronized Machine getMachine() throws RemoteException {
		int num = 10000;
		Machine m = null;
		Set<Machine> keys = dicCharge.keySet();
		//Obtaining iterator over set entries
	    //java.util.Iterator<Machine> itr = keys.iterator();
		for (Machine machine : keys){
			if (num> dicCharge.get(machine) ){
				num=dicCharge.get(machine);
				m = machine;
			}
		}
		incrementeCharge(m);
		return m;
	}

	private void incrementeCharge(Machine m) throws RemoteException{
		int charge = dicCharge.get(m)+1;
		this.dicCharge.put(m, charge);
	}
	
	private synchronized void releaseMachine(Machine  m) throws RemoteException{
		int charge = dicCharge.get(m)-1;
		this.dicCharge.put(m, charge);
	}
	
	public String getNom() throws RemoteException{
		return nom;
	}
	
    public void registerClient(Client client) throws RemoteException {
		System.out.println("Aiguilleur registerClient");
    }

}
