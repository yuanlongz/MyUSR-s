package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import concurrency.LockManager;
import domain.Role;
import domain.Service;
import domain.ServiceStatus;
import domain.User;
import service.ServiceLogic;
import session.Session;

/**
 * Servlet implementation class ServiceControllerServlet
 */
@WebServlet("/ServiceControllerServlet")
public class ServiceControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiceControllerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (Session.checkSession(request, response)) {
			User user = null;
			// get lock manager
			try {
				user = User.getUserById(Session.getUserId(request));
				LockManager.getInstance().acquireWritelock(user);
			} catch (InterruptedException e) {
				System.out.println(
						"Acquiring Write lock when updating a service failed :(");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//TODO: check action and use different methods 
			updateService(request, response);
			
			//release lock
			LockManager.getInstance().releaseWriteLock(user);
			
		}

	}

	private void updateService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get onject from session
		String serviceId = Session.getTargetId(request);
		Service service = Service.findByServiceId(serviceId);

		// update service details
		String address = request.getParameter("address");
		String item_list = request.getParameter("item_list");
		String description = request.getParameter("description");
		String status = request.getParameter("isStatus");
		if (address != null)
			service.setAddress(address);
		if (item_list != null)
			service.setItemStringList(item_list);
		if (description != null)
			service.setDescription(description);
		if (status != null)
			service.setStatus(ServiceStatus.valueOf(status));

		// TODO: remove
		System.out.printf(
				"address: %s, item_list:%s description:%s status:%s\n", address,
				item_list, description, status);

		// complicate business logic when service is canceled or completed
		if (status == ServiceStatus.CANCEL.name()) {
			ServiceLogic.cancelService(service);
		} else if (status == ServiceStatus.COMPLETE.name()) {
			ServiceLogic.completeService(service);
		} else {
			service.update();
		}

		// send back to different pages based on customer or admin view
		String userId = Session.getUserId(request);
		try {
			User user = User.getUserById(userId);
			if (user.getRole() == Role.ADMIN) {
				request.getRequestDispatcher("adminServiceList.jsp")
						.forward(request, response);
			} else if (user.getRole() == Role.CUSTOMER) {
				request.getRequestDispatcher("customerServiceList.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("errorMessage",
					"session Expired or unknow error when update service");
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
			e.printStackTrace();
		}
	}

}
