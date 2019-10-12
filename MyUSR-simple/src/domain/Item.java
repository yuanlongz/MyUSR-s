package domain;

import dataSource.ItemMapper;
import dataSource.MapperFactory;

public class Item implements DomainObject{
	private int unit;
	private String name, unitPrice;
	
	public Item() {
		super();
	}
	
	public Item(int unit, String price, String name) {
		super();
		this.unit = unit;
		this.unitPrice = price;
		this.name = name;
	}

	public static void add(String name, String price) {
		try {
			ItemMapper mapper = (ItemMapper) MapperFactory
					.getMapper(new Item());
			mapper.insert(new Item(0, price, name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void remove(String name) {
		try {
			ItemMapper mapper = (ItemMapper) MapperFactory
					.getMapper(new Item());
			mapper.delete(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(String name, String price) {
		try {
			ItemMapper mapper = (ItemMapper) MapperFactory
					.getMapper(new Item());
			mapper.update(new Item(0, price, name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getPrice(String name) {
		try {
			return ItemMapper.findPrice(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getRebate() {
		return unit * Integer.valueOf(unitPrice);
	}



	public String getName() {
		return name;
	}



	public int getUnit() {
		return unit;
	}



	public String getUnitPrice() {
		return unitPrice;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String getId() {
		return name;
	}
	
	
	
	
}
