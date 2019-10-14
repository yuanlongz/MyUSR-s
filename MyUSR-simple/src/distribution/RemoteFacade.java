package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

public interface RemoteFacade {
	abstract void toXML(DTOObject DTO, OutputStream outputStream)
			throws RemoteException;

	abstract void update(DTOObject obj) throws RemoteException;

	abstract void update(InputStream inputStream) throws RemoteException;
	
	abstract DTOObject get(String id) throws RemoteException;
}
