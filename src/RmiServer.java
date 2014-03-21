import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Observable;

public class RmiServer extends Observable implements RmiObservableService,
		Runnable {
	private static final long serialVersionUID = 1L;

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private String response;

	public RmiServer() {
		new Thread(this).start();
	}

	@Override
	public void addObserver(RemoteObserver o) throws RemoteException {
		WrappedObserver mo = new WrappedObserver(o);
		addObserver(mo);
		System.out.println("Added observer :" + mo);
	}

	@Override
	public void run() {
		while (true) {
			try {
				response = br.readLine();
			} catch (IOException e) {
				// ignore
			}
			setChanged();
			notifyObservers(response);
		}
	}

	public static void main(String[] args) {

		try {

			RmiServer obj = new RmiServer();
			RmiObservableService stub = (RmiObservableService) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object ’s stub in the registry
			Registry registry = LocateRegistry.createRegistry(9999);
			registry.rebind("RmiService", stub);

			System.err.println("Server ready");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}