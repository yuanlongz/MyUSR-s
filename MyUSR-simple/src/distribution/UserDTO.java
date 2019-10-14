package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import domain.Role;
import domain.Service;

public class UserDTO extends DTOObject{
	private String name, id, account, password;
	private Role role;
	private ArrayList<Service> serviceList;
	public UserDTO(String name, String id, String account, String password,
			Role role, ArrayList<Service> serviceList) {
		super();
		this.name = name;
		this.id = id;
		this.account = account;
		this.password = password;
		this.role = role;
		this.serviceList = serviceList;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}
	public void setServiceList(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}
	
	
}
