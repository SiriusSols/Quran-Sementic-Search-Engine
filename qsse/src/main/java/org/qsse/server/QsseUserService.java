package org.qsse.server;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class QsseUserService implements UserService{
	
public static final String USER_ID_ATTRIBUTE = "user";
	
	private final Provider<HttpSession> session;

	@Inject
	public QsseUserService(Provider<HttpSession> session) {
		super();
		this.session = session;
	}

	public void setUser(String email) {

		session.get().setAttribute(USER_ID_ATTRIBUTE, "" + email);

	}

	public void clearUser() {

		session.get().removeAttribute(USER_ID_ATTRIBUTE);
		session.get().invalidate();
		
	}

	@Override
	public String createLoginURL(String arg0) {
		return "/login";
	}

	@Override
	public String createLoginURL(String arg0, String arg1) {
		return createLoginURL(arg0);
	}

	@Override
	public String createLoginURL(String arg0, String arg1, String arg2,
			Set<String> arg3) {
		return createLoginURL(arg0);
	}

	@Override
	public String createLogoutURL(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String createLogoutURL(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public User getCurrentUser() {
//		if (inDevelopmentMode()) {
//			return new User("test@abidshafiq.com", "");
//		}
		String openid = (String) session.get().getAttribute(USER_ID_ATTRIBUTE);
		if (openid == null) {
			return null;
		}
		String email = (String) session.get().getAttribute(USER_ID_ATTRIBUTE);
		if (email == null && isUserAdmin()) {
			email = "@abidshafiq.com";
		} else if (email == null) {
			email = "user@user.com";
		}
		return new User(email,  openid);
	}

	protected boolean inDevelopmentMode() {
		return System.getProperty("com.google.appengine.runtime.environment")
				.equals("Development");
	}

	@Override
	public boolean isUserAdmin() {
		if (inDevelopmentMode()) {
			return true;
		}
		return true;
	}

	@Override
	public boolean isUserLoggedIn() {
		return getCurrentUser() != null;
	}

}
