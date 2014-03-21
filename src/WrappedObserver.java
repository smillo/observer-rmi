import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class WrappedObserver implements Observer, Serializable {

	private static final long serialVersionUID = 1L;

	private RemoteObserver remoteClient = null;

	public WrappedObserver(RemoteObserver ro) {
		this.remoteClient = ro;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			remoteClient.remoteUpdate(o.toString(), arg);
		} catch (RemoteException e) {
			System.out.println("Remote exception removing observer :" + this);
			o.deleteObserver(this);
		}
	}

}
