package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import concurrency.LockMapper;
import dataSource.MapperFactory;
import domain.Item;

public class ItemFacade implements RemoteFacade {
	private static LockMapper mapper;

	public String getItemPrice(String name) throws RemoteException {
		String result = null;
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Item()));
			result = mapper.findPrice(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void toXML(DTOObject DTO, OutputStream outputStream)
			throws RemoteException {
		ItemDTO.toXML(DTO, outputStream);
	}

	@Override
	public void update(DTOObject obj) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Item()));
			mapper.update(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(InputStream inputStream) throws RemoteException {
		try {
			mapper = new LockMapper(MapperFactory.getMapper(new Item()));
			mapper.update(ItemDTO.fromXML(inputStream));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public DTOObject get(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
