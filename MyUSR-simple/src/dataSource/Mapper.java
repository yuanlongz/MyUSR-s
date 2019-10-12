package dataSource;

import domain.DomainObject;

public interface Mapper {
	abstract void delete(String id) throws Exception;
	abstract void insert(DomainObject obj) throws Exception;
	abstract void update(DomainObject obj) throws Exception;
}
