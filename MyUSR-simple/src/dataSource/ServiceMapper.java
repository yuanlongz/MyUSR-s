package dataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.DomainObject;
import domain.Service;
import domain.ServiceStatus;

public class ServiceMapper implements Mapper {
	private static IdentityMap<Service> map = IdentityMap
			.getInstance(Service.class);
	// service table: 1.service_id 2.user_id 3.address 4.item_list 5.description
	// 6.status 7.bill

	// find service With user ID Statement String
	private static final String findServiceWithUserIDSS = "SELECT service_id "
			+ "FROM service " + "WHERE user_id = ?";
	// find service With service ID Statement String
	private static final String findServiceWithServiceIDSS = "SELECT * "
			+ "FROM service " + "WHERE service_id = ?";
	// find service With service status Statement String
	private static final String findServiceWithStatusIDSS = "SELECT * "
			+ "FROM service " + "WHERE status = ?";
	// delete service from service table with service id statement String
	private static final String delServiceWithIDSS = "DELETE FROM service "
			+ "WHERE service_id = ?";
	// insert service statement String
	private static final String insertServiceSS = "INSERT INTO service VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?)";
	// update service profile in service table statement String
	private static final String updateServiceSS = "UPDATE service "
			+ "SET address = ?, description = ?, item_list = ?, "
			+ "status = ?, bill = ?" + "WHERE service_id = ?";

	@Override
	public void delete(String id) throws Exception {
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(delServiceWithIDSS);
		sqlStatement.setString(1, id);
		sqlStatement.execute();
	}
	
	@Override
	public void insert(DomainObject obj ) throws Exception {
		Service service = (Service) obj;
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(insertServiceSS);
		sqlStatement.setString(1, service.getServiceID());
		sqlStatement.setString(2, service.getUserID());
		sqlStatement.setString(3, service.getAddress());
		sqlStatement.setString(4, service.getItemStringList());
		sqlStatement.setString(5, service.getDescription());
		sqlStatement.setString(6, service.getStatus().name());
		sqlStatement.setString(7, service.getBill());
		sqlStatement.execute();
		//update info
		// store the complete service to id_map
		map.putWithID(service.getServiceID(), service);
	}

	@Override
	public void update(DomainObject obj) throws Exception {
		Service service = (Service) obj;
		PreparedStatement sqlStatement = null;
		sqlStatement = DBConnection.prepare(updateServiceSS);
		sqlStatement.setString(1, service.getAddress());
		sqlStatement.setString(2, service.getDescription());
		sqlStatement.setString(3, service.getItemStringList());
		sqlStatement.setString(4, service.getStatus().name());
		sqlStatement.setString(5, service.getBill());
		sqlStatement.setString(6, service.getServiceID());
		sqlStatement.execute();
	}

	public static ArrayList<Service> findWithUserID(String userId)
			throws Exception {
		ArrayList<Service> result = new ArrayList<Service>();
		PreparedStatement sqlStatement = null;
		ResultSet rs = null;
		sqlStatement = DBConnection.prepare(findServiceWithUserIDSS);
		sqlStatement.setString(1, userId);
		rs = sqlStatement.executeQuery();
		while (rs.next()) {
			String serviceId = rs.getString("service_id");
			result.add(new Service(serviceId));
		}
		return result;
	}

	public Service findWithServiceID(String serviceId) throws Exception {
		Service result = map.getWithID(serviceId);
		if (result == null) {
			PreparedStatement sqlStatement = null;
			ResultSet rs = null;
			sqlStatement = DBConnection.prepare(findServiceWithServiceIDSS);
			sqlStatement.setString(1, serviceId);
			rs = sqlStatement.executeQuery();
			if (rs.next()) {
				result = new Service(serviceId, rs.getString("user_id"),
						rs.getString("address"), rs.getString("description"),
						rs.getString("bill"), rs.getString("status"),
						rs.getString("item_list"));

				// store the complete service to id_map
				map.putWithID(serviceId, result);
			} else
				throw new Exception("service record does not find with id");
		} 

		return result;
	}

	public static ArrayList<Service> findWithServiceStatus(ServiceStatus status)
			throws Exception {
		ArrayList<Service> result = new ArrayList<Service>();
		PreparedStatement sqlStatement = null;
		ResultSet rs = null;
		sqlStatement = DBConnection.prepare(findServiceWithStatusIDSS);
		sqlStatement.setString(1, status.name());
		rs = sqlStatement.executeQuery();
		while (rs.next()) {
			String serviceId = rs.getString("service_id");
			result.add(new Service(serviceId));
		}
		return result;

	}

}
