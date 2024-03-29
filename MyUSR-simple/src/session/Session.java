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
	private static String sessionID, userId, targetId;

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

	public static String getUserId(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(USER_ATTRIBUTE_ID));
	}

	/**
	 * handle secondary id, e.g service id/user_id
	 * 
	 * @param request
	 * @return
	 */
	public static String getTargetId(HttpServletRequest request) {

		return (String) (request.getSession()
				.getAttribute(Target_ATTRIBUTE_ID));
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
		if (getUserId(request) == null) {
			request.setAttribute("errorMessage", "Invalid Session");
			request.getRequestDispatcher("login.jsp").forward(request,
					response);
			return false;
		} else {
			// refresh expiring time
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(5 * 60);
			update(request);
			return true;
		}
	}

	private static void update(HttpServletRequest request) {
		sessionID = getSessionId(request);
		userId = getUserId(request);
		targetId = getTargetId(request);
	}

	public static String getSessionID() {
		return sessionID;
	}

	public static String getUserId() {
		return userId;
	}

	public static String getTargetId() {
		return targetId;
	}
	
	

}
