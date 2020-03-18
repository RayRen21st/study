package study.resteasy.shopping;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/shopping")
public class ShoppingEndpoint {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ShoppingEndpoint.class);
	
	@Path("/users")
	@GET
	@Produces("application/json")
	public Response getUsers(){
		logger.info("Standard API is Invoked getUsers");
		User user1 = new User();
		user1.setId("001");
		user1.setName("john");
		User user2 = new User();
		user2.setId("002");
		user2.setName("Kathy");
		return Response.ok().entity(Arrays.asList(user1, user2)).build();
	}
	
	@Path("/user")
	@GET
	@Produces("application/json")
	public Response getDefaultUser(){
		logger.info("Standard API is Invoked getUser");
		User user = new User();
		user.setId("001");
		user.setName("john");
		return Response.ok().entity(user).build();
		
	}
}
