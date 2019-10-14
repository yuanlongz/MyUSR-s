package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import concurrency.LockMapper;
import dataSource.MapperFactory;
import domain.Admin;

public class AdminFacade implements RemoteFacade {
	private static LockMapper mapper;
	
	@Override
	public void toXML(DTOObject DTO, OutputStream outputStream)
			throws RemoteException {
		// TODO Auto-generated method stub
		UserDTO.toXML(DTO, outputStream);
	}

	@Override
	public void update(DTOObject obj) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Admin()));
			mapper.update(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(InputStream inputStream) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Admin()));
			mapper.update(ItemDTO.fromXML(inputStream));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public DTOObject get(String id) throws RemoteException {
		//TODO: assemble the AdminDTO
		
		return null;
	}

}
