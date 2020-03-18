package study.resteasy;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import study.resteasy.filter.RequestLoggingFilter;
import study.resteasy.shopping.ShoppingEndpoint;
import study.resteasy.shopping.tmo.TMOShoppingEndpoint;

public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set =  new HashSet<Class<?>>();
		set.add(ShoppingEndpoint.class);
		set.add(TMOShoppingEndpoint.class);
		set.add(RequestLoggingFilter.class);
		return set;
	}
	

}
