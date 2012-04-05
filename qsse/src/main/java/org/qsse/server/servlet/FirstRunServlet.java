package org.qsse.server.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.qsse.server.BCrypt;
import org.qsse.server.model.User;
import org.qsse.shared.type.UserType;


@SuppressWarnings("serial")
@Singleton
public class FirstRunServlet extends HttpServlet {

	private final EntityManager entityManager;
	
	@Inject
	public FirstRunServlet(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		try{

			List<User> users = entityManager.find(entityManager.createQuery(User.class));

			if(users.size() < 1){

				User admin = new User();
				
				admin.setEmail("admin@admin.com");
				admin.setPassword(BCrypt.hashpw("admin", BCrypt
						.gensalt()));
				admin.setUserType(UserType.ADMIN);
				admin.setFirstName("admin");
				admin.setLastName("");
				admin.setDob(null);
				admin.setGender("");
				admin.setAddress("");
				admin.setPhone("");
				admin.setUserDp("");
				admin.setActive(true);
				
				entityManager.put(admin);
				
				User aalim = new User();
				
				aalim.setEmail("aalim@aalim.com");
				aalim.setPassword(BCrypt.hashpw("aalim", BCrypt.gensalt()));
				aalim.setUserType(UserType.AALIM);
				aalim.setFirstName("aalim");
				aalim.setLastName("");
				aalim.setDob(null);
				aalim.setGender("");
				aalim.setAddress("");
				aalim.setPhone("");
				aalim.setUserDp("");
				aalim.setActive(true);
				
				entityManager.put(aalim);
				
				User user = new User();
				
				user.setEmail("user@user.com");
				user.setPassword(BCrypt.hashpw("user", BCrypt.gensalt()));
				user.setUserType(UserType.USER);
				user.setFirstName("user");
				user.setDob(null);
				user.setGender("");
				user.setAddress("");
				user.setPhone("");
				user.setUserDp("");
				user.setActive(true);
				entityManager.put(user);
			}
			else{
				throw new Exception();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
			
	
		
	}
		
}
