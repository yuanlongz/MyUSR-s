package session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;;

public class Session {
	public static final String USER_ATTRIBUTE_ID = "user_id";
	public static final String Target_ATTRIBUTE_ID = "target_id";

	private HttpSession httpSession;

	private Session(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public static HttpSession register(HttpServletRequest request,
			String userId) {
		// get the old session and invalidate
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}

		// generate a new session
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute(USER_ATTRIBUTE_ID, userId);
		// setting session to expire in 5 mins
		newSession.setMaxInactiveInterval(5 * 60);

		return newSession;
	}

	public static String getUserId(HttpSession session) {
		return (String) (session.getAttribute(USER_ATTRIBUTE_ID));
	}

	/**
	 * handle secondary id, e.g service id/user_id
	 * 
	 * @param session
	 * @return
	 */
	public static String getTargetId(HttpSession session) {
		return (String) (session.getAttribute(Target_ATTRIBUTE_ID));
	}

	public static String getSessionId(HttpServletRequest request) {
		String sessionID = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("JSESSIONID"))
				sessionID = cookie.getValue();
		}
		return sessionID;
	}

	public static void expire(HttpServletRequest request) {
		// invalidate the session if exists
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	public static boolean checkSession(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// if at any stage session expires, redirect the user back to the log in
		// page
		if (getUserId(request.getSession()) == null) {
			request.setAttribute("errorMessage", "Invalid Session");
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
			return false;
		} else {
			// refresh expiring time
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(5 * 60);
			return true;
		}
	}

}
