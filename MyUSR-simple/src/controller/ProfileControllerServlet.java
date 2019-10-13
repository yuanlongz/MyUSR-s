package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import session.Session;

/**
 * Servlet implementation class ProfileControllerServlet
 */
@WebServlet("/ProfileControllerServlet")
public class ProfileControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//update user profile
		String name = request.getParameter("name");
		String psw = request.getParameter("psw");
		
		if (Session.checkSession(request, response)) {
			String userId = Session.getUserId(request.getSession());
			User user;
			try {
				user = User.getUserById(userId);
				user.setName(name);
				user.setPassword(psw);
				user.update();
				//show update success info
				request.setAttribute("errorMessage", "Update Success!");
				request.getRequestDispatcher("profile.jsp").forward(request,
						response);
			} catch (Exception e) {
				// show update failed info
				request.setAttribute("errorMessage", "Update Failed, try again later");
				request.getRequestDispatcher("profile.jsp").forward(request,
						response);
				e.printStackTrace();
			}
		}
	}

}
