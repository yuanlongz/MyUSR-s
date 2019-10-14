package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import domain.ServiceStatus;

public class ServiceDTO extends DTOObject{
	private String serviceID, userID, address, description, bill;
	private ServiceStatus status;
	private ArrayList<ItemDTO> itemList;
	
	
	public ServiceDTO(String serviceID, String userID, String address,
			String description, String bill, ServiceStatus status,
			ArrayList<ItemDTO> itemList) {
		super();
		this.serviceID = serviceID;
		this.userID = userID;
		this.address = address;
		this.description = description;
		this.bill = bill;
		this.status = status;
		this.itemList = itemList;
	}
	
	//getters and setters
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public ServiceStatus getStatus() {
		return status;
	}
	public void setStatus(ServiceStatus status) {
		this.status = status;
	}
	public ArrayList<ItemDTO> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<ItemDTO> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String getId() {
		return serviceID;
	}
	
	
}
