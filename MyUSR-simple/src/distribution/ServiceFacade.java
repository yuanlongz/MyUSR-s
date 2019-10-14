package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import concurrency.LockMapper;
import dataSource.MapperFactory;
import domain.Service;

public class ServiceFacade implements RemoteFacade{
	private static LockMapper mapper;
	
	@Override
	public ServiceDTO get(String serviceID) throws RemoteException{
		//TODO: assemble the serviceDTO
		
		return null;
	}
	
	
	@Override
	public void toXML(DTOObject DTO, OutputStream outputStream)
			throws RemoteException {
		ServiceDTO.toXML(DTO, outputStream);
		
	}

	@Override
	public void update(DTOObject obj) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Service()));
			mapper.update(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(InputStream inputStream) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Service()));
			mapper.update(ItemDTO.fromXML(inputStream));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
