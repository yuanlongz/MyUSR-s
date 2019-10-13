package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Role;
import domain.User;

/**
 * Servlet implementation class LoginControllerServlet
 */
@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ")
				.append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// check session: if registered, then directly redirect, don't need to
		// double login
		// , redirect to different pages
		String account = request.getParameter("account");
		String password = request.getParameter("passWord");

		try {
			User user = User.getUserByAccount(account);
			String userPass = user.getPassword();
			Role userType = user.getRole();
			// check password combination
			if (!userPass.equals(password)) {
				throw new Exception("Wrong password");
			}

			// register user in session
			// get the old session and invalidate
			HttpSession oldSession = request.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}
			// generate a new session
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("user", account);
			// setting session to expiry in 5 mins
			newSession.setMaxInactiveInterval(5 * 60);

			Cookie userName = new Cookie("user", account);
			userName.setMaxAge(5*60);
			response.addCookie(userName);
			
			// redirect user to their pages
			if (userType == Role.ADMIN) {
				response.sendRedirect("adminHome.jsp");
			} else if (userType == Role.CUSTOMER) {
				response.sendRedirect("customerHome.jsp");
			}
		} catch (Exception e) {
			// show the page with error information
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
			e.printStackTrace();
		}
	}

}
