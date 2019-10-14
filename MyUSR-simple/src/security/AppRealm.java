package security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import domain.User;

public class AppRealm extends JdbcRealm {
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token){
		// identify account to log to
		UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
		// using unique account instead of username
		final String account  = userPassToken.getUsername();
		
		User user = null;
		try {
			user = User.getUserByAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user == null) {
			System.out.println("No account find with this user");
			return null;
		}

		return new SimpleAuthenticationInfo(user.getId(), user.getPassword(),getName());
	}
	
	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roles = new HashSet<>();
		
		if(principals.isEmpty()) {
			System.out.println("Given principals to authorize are empty");
			return null;
		}
		
		String account = (String) principals.getPrimaryPrincipal();
		User user = null;
		try {
			user = User.getUserByAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user == null) {
			System.out.println("No account find with this user");
			return null;
		}
		
		//add roles of the user according to its type
		roles.add(user.getRole().name());
		
		return new SimpleAuthorizationInfo(roles);
	}
	
}
