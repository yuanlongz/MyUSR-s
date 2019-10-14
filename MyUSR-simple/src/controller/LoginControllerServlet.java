package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import domain.Role;
import domain.User;
import security.AppSession;
import session.Session;

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
		String account = request.getParameter("account");
		String password = request.getParameter("passWord");
		// security autherization shiro
		UsernamePasswordToken token = new UsernamePasswordToken(account,
				password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		

		try {
			User user = User.getUserByAccount(account);
			String userPass = user.getPassword();
			Role userType = user.getRole();
			// check password combination
			if (!userPass.equals(password)) {
				throw new Exception("Wrong password");
			}

			// TODO:register user in session
			HttpSession newSession = Session.register(request, user.getId());

			// add Cookie as backup
			Cookie userId = new Cookie("userId", user.getId());
			userId.setMaxAge(5 * 60);
			response.addCookie(userId);
			
			//shiro version
			//AppSession.init(user);

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
