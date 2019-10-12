package domain;

import java.util.ArrayList;

import dataSource.IDFieldFactory;
import dataSource.ServiceMapper;


public class Customer extends User {
	private ArrayList<Service> serviceList;
	public Customer() {
		super();
		serviceList = null;
	}

	public Customer(String name, String account, String password) {
		super(name, IDFieldFactory.createID(), account, password,
				Role.CUSTOMER);
		serviceList = null;
	}

	public Customer(String id) {
		super(id);
		serviceList = null;
	}

	public Customer(String name, String id, String account, String password,
			String role) {
		super(name, id, account, password, Role.valueOf(role));
		serviceList = null;
	}


	public ArrayList<Service> getServiceList() {
		if (serviceList == null) {
			try {
				serviceList = ServiceMapper.findWithUserID(getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serviceList;
	}

}
