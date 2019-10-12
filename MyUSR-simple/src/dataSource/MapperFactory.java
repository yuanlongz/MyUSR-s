package dataSource;

import domain.*;

public class MapperFactory {
	private static UserMapper userMapper = new UserMapper();
	private static ServiceMapper serviceMapper = new ServiceMapper();
	private static ItemMapper itemMapper = new ItemMapper();

	/**
	 * Using Factory pattern to generate mappers
	 * 
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public static Mapper getMapper(Object obj) throws Exception {
		// TODO: else if add other mappers
		if (obj instanceof User) {
			return userMapper;
		} else if (obj instanceof Service) {
			return serviceMapper;
		} else if (obj instanceof Item) {
			return itemMapper;
		} else
			throw new Exception("No matched mapper find for:" + obj.getClass());

	}
}
