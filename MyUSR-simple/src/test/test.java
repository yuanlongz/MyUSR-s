package test;

import java.util.ArrayList;

import dataSource.ItemMapper;
import dataSource.ServiceMapper;
import dataSource.UserMapper;
import domain.Admin;
import domain.Customer;
import domain.Item;
import domain.Service;
import domain.ServiceStatus;
import domain.User;

public class test {
	static String user_id = "00002";
	static String service_id = "511886c9-c73b-4faa-9f37-916839a278dd";
	static String custAccount = "customer@gmail.com";
	static String adminAccount = "admin@usr.au";
	static String itemList = "ipad:2,mac:1";
	static String itemName = "ipad";

	public static void main(String[] args) {
		// Test domain
		// Test Customer
		if (false) {
			Customer cst = new Customer("Tim", custAccount, "1");
			// Add
			//cst.add();
			// Remove
			//cst.remove(user_id);
			//cst.delete();
			// View
			try {
				cst = (Customer) User.getUserByAccount(custAccount);
				for (Service service : cst.getServiceList()) {
					System.out.println(service.getServiceID());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Update
			//cst.setPassword("2");
			//cst.update();
		}

		// Test Admin
		if (true) {
			// Add
			// Remove
			// View
			// Update
			try {
				Admin adm = (Admin) User.getUserByAccount(adminAccount);
				for (Service service : adm.getServiceList()) {
					System.out.println(service.getDescription());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		// Test Item
		if (false) {
			// Add
			Item.add("surface", "20");
			// Remove
			Item.remove("surface");
			// View
			System.out.println(Item.getPrice("mac"));
			// Update
			Item.update("ipad", "10");
		}
		

		// Test Service
		if (false) {
			Service ser = new Service(user_id, "ogilvie street", "pick up",
					"50", ServiceStatus.REQUESTED.name(), itemList);
			// Add
			ser.add();
			// Remove
			//ser.delete();
			//ser.remove("b7b77187-df32-4753-96e4-c270c2e40782");
			// View
			System.out.print(ser.calcuateBill());
			// Update
			
			
		}
		
		// Test Mappers
		// Test User
		if (false) {
			UserMapper userMapper = new UserMapper();
			Customer cust = new Customer("Jimmy", custAccount, "1");
			Admin adm = new Admin("Jim", adminAccount, "1");
			// Add: cust
			try {
				userMapper.insert(cust);
				System.out.println("new Customer added");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Add: admin

			// Remove
			try {
				// userMapper.delete(user_id);
				System.out.println("Customer removed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// View: by ID
			try {
				cust = (Customer) userMapper.findWithID(user_id);
				System.out.println(cust.getAccount());
				cust = (Customer) userMapper.findWithID(user_id);
				System.out.println(cust.getAccount());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// View: by account
			try {
				cust = (Customer) userMapper.findWithAccountEmail(custAccount);
				System.out.println(cust.getId());
				cust = (Customer) userMapper.findWithAccountEmail(custAccount);
				System.out.println(cust.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// Update
			try {
				cust.setPassword("2");
				userMapper.update(cust);
				System.out.println("Customer updated");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Test Item
		if (false) {
			ItemMapper itmMapper = new ItemMapper();
			Item itm = new Item(1, "10", itemName);
			// Add
			try {
				itmMapper.insert(itm);
				System.out.println("item Added");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Remove
			try {
				// itmMapper.delete(itemName);
				System.out.println("item deleted");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// View
			try {
				String price = itmMapper.findPrice(itemName);
				System.out.println(itemName + ":" + price);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Update
			try {
				itm.setUnitPrice("5");
				itmMapper.update(itm);
				System.out.println("item updated");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Test Service
		if (false) {
			ServiceMapper serMapper = new ServiceMapper();
			Service ser = new Service(user_id, "ogilvie street", "pick up",
					"50", ServiceStatus.REQUESTED.name(), itemList);
			// Add
			try {
				// serMapper.insert(ser);
				System.out.println("Service added");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Remove
			try {
				// serMapper.delete(service_id);
				System.out.println("Service deleted");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// View
			try {
				ser = serMapper.findWithServiceID(service_id);
				ser = serMapper.findWithServiceID(service_id);
				System.out.println(ser.getUserID() + ", find with service_id");
				ArrayList<Service> results = ServiceMapper
						.findWithUserID(user_id);
				System.out.println("find with user_id");
				for (Service service : results) {
					System.out.println(service.getServiceID());
				}
				// test record function
				for (Item item : ser.getItemList()) {
					System.out.println(item.getUnit());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Update
			try {
				// ser.setStatus(ServiceStatus.COMPLETE);
				serMapper.update(ser);
				System.out.println("Service updated");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
