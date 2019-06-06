package study.resteasy.shopping;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/shopping")
public class ShoppingEndpoint {
	
	@Path("/users")
	@GET
	@Produces("application/json")
	public List<User> getUsers(){
		User user = new User();
		user.setId("001");
		user.setId("john");
		return Arrays.asList(user);
		
	}
}
