package domain;

import java.util.ArrayList;

import dataSource.IDFieldFactory;

public class Admin extends User {
	private ArrayList<Service> serviceList;

	public Admin() {
		super();
		serviceList = null;
	}

	// admin can only create new admin account at admin page
	public Admin(String name, String account, String password) {
		super(name, IDFieldFactory.createID(), account, password, Role.ADMIN);
		serviceList = null;
	}

	public Admin(String name, String id, String account, String password,
			String role) {
		super(name, id, account, password, Role.valueOf(role));
		serviceList = null;
	}

	public Admin(String id) {
		super(id);
		serviceList = null;
	}

	public ArrayList<Service> getServiceList() {
		// choose all services that has not been completed yet, keep updating
		serviceList = new ArrayList<Service>();
		serviceList.addAll(Service.findByStatus(ServiceStatus.REQUESTED));
		serviceList.addAll(Service.findByStatus(ServiceStatus.PROCESSING));
		serviceList.addAll(Service.findByStatus(ServiceStatus.STARTED));
		return serviceList;
	}

}
