package hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class GreetingInterceptor extends HandlerInterceptorAdapter{
	
	private final static Logger logger = LoggerFactory.getLogger(GreetingInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("------------------------Pre Handler");
		return super.preHandle(request, response, handler);
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("------------------------Post Handler");
		super.postHandle(request, response, handler, modelAndView);
	}

}
