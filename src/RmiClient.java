import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements RemoteObserver {
	private static final long serialVersionUID = 1L;

	protected RmiClient() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(9999);
			RmiObservableService remoteService = (RmiObservableService) registry.lookup("RmiService");

			RmiClient client = new RmiClient(); // Observer
			remoteService.addObserver(client); // add Observer to Observable

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void remoteUpdate(Object observable, Object updateMsg) throws RemoteException {
		System.out.println("got message :" + updateMsg);
	}
}
