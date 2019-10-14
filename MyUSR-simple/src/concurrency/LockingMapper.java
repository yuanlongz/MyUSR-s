package concurrency;

import dataSource.ItemMapper;
import dataSource.Mapper;
import domain.DomainObject;
import domain.User;
import session.Session;

public class LockingMapper implements Mapper {
	private Mapper mapper;
	
	public LockingMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	
	public void findPrice (String name) throws Exception {
		User user = null;
		ItemMapper itemMapper = (ItemMapper) mapper;
		// get lock manager
		try {
			user = User.getUserById(Session.getUserId());
			LockManager.getInstance().acquireReadLock(user);
		} catch (InterruptedException e) {
			System.out.println(
					"Acquiring read lock when read a item failed :(");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		itemMapper.findPrice(name);
		
		//release lock
		LockManager.getInstance().releaseWriteLock(user);
	}
	
	@Override
	public void delete(String id) throws Exception {
		User user = null;
		// get lock manager
		try {
			user = User.getUserById(Session.getUserId());
			LockManager.getInstance().acquireWritelock(user);
		} catch (InterruptedException e) {
			System.out.println(
					"Acquiring Write lock when updating failed :(");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mapper.delete(id);
		
		//release lock
		LockManager.getInstance().releaseWriteLock(user);

	}

	@Override
	public void insert(DomainObject obj) throws Exception {
		mapper.insert(obj);

	}

	@Override
	public void update(DomainObject obj) throws Exception {
		User user = null;
		// get lock manager
		try {
			user = User.getUserById(Session.getUserId());
			LockManager.getInstance().acquireWritelock(user);
		} catch (InterruptedException e) {
			System.out.println(
					"Acquiring Write lock when updating failed :(");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mapper.update(obj);
		
		//release lock
		LockManager.getInstance().releaseWriteLock(user);
	}


}
