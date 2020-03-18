package study.resteasy.shopping.tmo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.resteasy.shopping.ShoppingEndpoint;
import study.resteasy.shopping.User;

@Path("/style1/shopping")
public class TMOShoppingEndpoint extends ShoppingEndpoint{
	
	private static final Logger logger = LoggerFactory.getLogger(TMOShoppingEndpoint.class);
	
	
	@Path("/user")
	@GET
	@Produces("application/json")
	public Response getDefaultUser(){
		logger.info("TMO API is Invoked getUser");
		User user = new User();
		user.setId("001");
		user.setName("TMO");
		return Response.ok().entity(user).build();
	}
	
}
