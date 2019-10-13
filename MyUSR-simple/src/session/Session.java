package session;

import javax.servlet.http.HttpSession;;

public class Session {
	private static final String USER_ATTRIBUTE_NAME = "user";

	private HttpSession httpSession;

	private Session(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public static Session refreshSession(HttpSession httpSession) {
		if (httpSession.getAttribute(USER_ATTRIBUTE_NAME) == null) {
			//set default user
			//in the future we will require the user to login
			
		}
		return null;
	}
}
