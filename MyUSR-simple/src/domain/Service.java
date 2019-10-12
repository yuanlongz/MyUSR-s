package domain;

import java.util.ArrayList;

import dataSource.IDFieldFactory;
import dataSource.ItemMapper;
import dataSource.MapperFactory;
import dataSource.ServiceMapper;

public class Service {
	private ServiceMapper mapper;
	private static final int COST = 50;
	private String serviceID, userID, address, description, bill;
	private ServiceStatus status;

	private ArrayList<Item> itemList;
	private String itemStringList;

	public Service() {
		super();
		serviceID = null;
		userID = null;
		bill = null;
		address = null;
		description = null;
		status = null;
		itemList = null;
		itemStringList = null;
	}

	public Service(String userID, String address, String description,
			String bill, String status, String itemlist) {
		super();
		this.serviceID = IDFieldFactory.createID();
		this.userID = userID;
		this.address = address;
		this.description = description;
		this.bill = bill;
		this.status = ServiceStatus.valueOf(status);
		this.itemStringList = itemlist;
		this.itemList = record(itemlist);
	}

	// lazy initialization
	public Service(String serviceID) {
		super();
		this.serviceID = serviceID;
		userID = null;
		bill = null;
		address = null;
		description = null;
		status = null;
		itemList = null;
		itemStringList = null;
	}

	public Service(String serviceID, String userID, String address,
			String description, String bill, String status, String itemlist) {
		super();
		this.serviceID = serviceID;
		this.userID = userID;
		this.address = address;
		this.description = description;
		this.bill = bill;
		this.status = ServiceStatus.valueOf(status);
		this.itemStringList = itemlist;
		this.itemList = record(itemlist);
	}

	private ArrayList<Item> record(String item_list) {
		ArrayList<Item> result = new ArrayList<Item>();
		// item_list in the format of "item,unit;"
		// seprate item
		if (item_list != null) {
			String[] splitStr = item_list.split(",");
			// get item price
			for (String item : splitStr) {
				String[] splitItem = item.split(":");
				String name = splitItem[0];
				int unit = Integer.valueOf(splitItem[1]);
				try {
					String price = ItemMapper.findPrice(name);
					result.add(new Item(unit, price, name));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return result;
	}

	private void load() {
		ServiceMapper mapper = new ServiceMapper();
		try {
			Service result = mapper.findWithServiceID(serviceID);
			if (result != null) {
				userID = result.getUserID();
				bill = result.getBill();
				address = result.getAddress();
				description = result.getDescription();
				status = result.getStatus();
				itemList = result.getItemList();
				itemStringList = result.getItemStringList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * bill = base cost - rebate value
	 * 
	 * @return
	 */
	public String calcuateBill() {
		int sum = 0 + COST;
		for (Item item : itemList) {
			sum -= item.getRebate();
		}
		String result = Integer.toString(sum);
		this.bill = result;
		return result;
	}

	public void add() {
		try {
			mapper = (ServiceMapper) MapperFactory.getMapper(this);
			mapper.insert(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(String id) {
		try {
			ServiceMapper mapper = (ServiceMapper) MapperFactory
					.getMapper(new Service());
			mapper.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		try {
			mapper = (ServiceMapper) MapperFactory.getMapper(this);
			mapper.delete(this.getServiceID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			mapper = (ServiceMapper) MapperFactory.getMapper(this);
			mapper.update(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getItemStringList() {
		// lazy load:ghost
		if (itemStringList == null)
			load();
		return itemStringList;
	}

	public static ArrayList<Service> findByUserId(String id) {
		try {
			return ServiceMapper.findWithUserID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Service> findByStatus(ServiceStatus status) {
		try {
			return ServiceMapper.findWithServiceStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Service findByServiceId(String id) {
		try {
			ServiceMapper mapper = (ServiceMapper) MapperFactory
					.getMapper(new Service());
			return mapper.findWithServiceID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getServiceID() {
		// lazy load:ghost
		if (serviceID == null)
			load();
		return serviceID;
	}

	public String getUserID() {
		// lazy load:ghost
		if (userID == null)
			load();
		return userID;
	}

	public String getAddress() {
		// lazy load:ghost
		if (address == null)
			load();
		return address;
	}

	public String getDescription() {
		// lazy load:ghost
		if (description == null)
			load();
		return description;
	}

	public String getBill() {
		// lazy load:ghost
		if (bill == null)
			load();
		return bill;
	}

	public ServiceStatus getStatus() {
		// lazy load:ghost
		if (status == null)
			load();
		return status;
	}

	public ArrayList<Item> getItemList() {
		// lazy load:ghost
		if (itemList == null)
			load();
		return itemList;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public void setItemStringList(String itemStringList) {
		this.itemStringList = itemStringList;
	}

}
