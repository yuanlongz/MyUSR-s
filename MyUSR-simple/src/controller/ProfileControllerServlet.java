package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import concurrency.LockManager;
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
						"Acquiring Write lock when updating a user failed :(");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//TODO: check action and use different methods 
			updateProfile(request, response);
			
			//release lock
			LockManager.getInstance().releaseWriteLock(user);
		}
	}

	private void updateProfile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// update user profile
		String name = request.getParameter("name");
		String psw = request.getParameter("psw");

		String userId = Session.getUserId(request);
		User user;
		try {
			user = User.getUserById(userId);
			user.setName(name);
			user.setPassword(psw);
			user.update();
			// show update success info
			request.setAttribute("errorMessage", "Update Success!");
			request.getRequestDispatcher("profile.jsp").forward(request,
					response);
		} catch (Exception e) {
			// show update failed info
			request.setAttribute("errorMessage",
					"Update Failed, try again later");
			request.getRequestDispatcher("profile.jsp").forward(request,
					response);
			e.printStackTrace();
		}
	}

}
