package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataSource.UserMapper;
import domain.Customer;
import domain.User;
import session.Session;

/**
 * Servlet implementation class SignupControllerServlet
 */
@WebServlet("/SignupControllerServlet")
public class SignupControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// generate new user
		String name = request.getParameter("name");
		String acc = request.getParameter("account");
		String psw = request.getParameter("password");

		Customer user;
		try {
			user = new Customer(name, acc, psw);
			UserMapper mapper = new UserMapper();
			mapper.insert(user);
			
			// show update success info
			request.setAttribute("errorMessage", "register Success!");
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
		} catch (Exception e) {
			// show update failed info
			request.setAttribute("errorMessage",
					"register Failed, try again later");
			request.getRequestDispatcher("signup.jsp").forward(request,
					response);
			e.printStackTrace();
		}
	}

}
