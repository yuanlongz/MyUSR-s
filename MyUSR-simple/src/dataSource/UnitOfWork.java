package dataSource;

import java.util.ArrayList;
import java.util.List;


import domain.DomainObject;

public class UnitOfWork {
	private Mapper mapper;
	private static ThreadLocal<UnitOfWork> current = 
			new ThreadLocal<UnitOfWork>();
	
	private List<DomainObject> newObjects = new ArrayList<DomainObject>();
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
	private List<DomainObject> deletedObjects = new ArrayList<DomainObject>();
	
	
	public static void newCurrent() {
		setCurrent(new UnitOfWork());
	}
	
	public static void setCurrent(UnitOfWork uow) {
		current.set(uow);
	}
	
	public static UnitOfWork getCurrent() {
		return (UnitOfWork) current.get();
	}
	
	public void registerNew(DomainObject obj) throws Exception{
		if (dirtyObjects.contains(obj))	throw new Exception("object is dirty");
		if (deletedObjects.contains(obj))	
			throw new Exception("object is deleted");
		if (newObjects.contains(obj))	throw new Exception("object is new");
		newObjects.add(obj);
	}
	
	
	public void registerDirty(DomainObject obj) throws Exception{
		if (deletedObjects.contains(obj))	
			throw new Exception("object is deleted");
		if (newObjects.contains(obj))	throw new Exception("object is new");
		if (dirtyObjects.contains(obj)) 
			throw new Exception("object is already dirty");
		dirtyObjects.add(obj);
	}
	
	
	public void registerDeleted(DomainObject obj) throws Exception{

		if (deletedObjects.contains(obj))	
			throw new Exception("object is already deleted");
		if (newObjects.contains(obj))	throw new Exception("object is new");
		if (dirtyObjects.contains(obj)) 
			throw new Exception("object object is dirty");
		deletedObjects.add(obj);		
	}
	
	 
	public void commit() throws Exception {
		for (DomainObject obj : newObjects) {
			mapper = MapperFactory.getMapper(obj.getClass());
			mapper.insert(obj);
		}
		
		for (DomainObject obj : dirtyObjects) {
			mapper = MapperFactory.getMapper(obj.getClass());
			mapper.update(obj);
		}
		
		for (DomainObject obj : deletedObjects) {
			mapper = MapperFactory.getMapper(obj.getClass());
			mapper.delete(obj.getId());
		}
		
		newObjects.clear();
		dirtyObjects.clear();
		deletedObjects.clear();
	}
}
