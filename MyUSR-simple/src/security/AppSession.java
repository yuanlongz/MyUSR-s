package security;


import org.apache.shiro.SecurityUtils;

import domain.User;

public class AppSession {
	private static final String USER_ATRIBUTE_ACCOUNT = "user";

	public static boolean hasRole(String role) {
		return SecurityUtils.getSubject().hasRole(role);
	}

	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public static void init(User user) {
		SecurityUtils.getSubject().getSession().setAttribute(USER_ATRIBUTE_ACCOUNT,
				user);
	}

	public static User getUser() {
		return (User) SecurityUtils.getSubject().getSession()
				.getAttribute(USER_ATRIBUTE_ACCOUNT);
	}
}
