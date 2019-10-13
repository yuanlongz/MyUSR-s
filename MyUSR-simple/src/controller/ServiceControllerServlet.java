package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import domain.Service;
import domain.ServiceStatus;
import domain.User;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO: update service details
		String serviceId = Session.getTargetId(request.getSession());
		Service service = Service.findByServiceId(serviceId);
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
		System.out.printf("address: %s, item_list:%s description:%s status:%s\n",
				address, item_list, description, status);
		service.update();
		// send back to different pages based on customer or admin view
		String userId = Session.getUserId(request.getSession());
		try {
			User user = User.getUserById(userId);
			if (user.getRole() == Role.ADMIN) {
				response.sendRedirect("adminServiceList.jsp");
				// request.getRequestDispatcher("adminServiceList.jsp")
				// .forward(request, response);
			} else if (user.getRole() == Role.CUSTOMER) {
				response.sendRedirect("customerServiceList.jsp");
				// request.getRequestDispatcher("customerServiceList.jsp")
				// .forward(request, response);
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
