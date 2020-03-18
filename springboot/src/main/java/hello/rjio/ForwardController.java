package hello.rjio;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rjio")
public class ForwardController {
	
    private final static Logger logger = LoggerFactory.getLogger(ForwardController.class);

    @RequestMapping("/**")
    public String forward(HttpServletRequest request) {
    	logger.info("Forward Request. URI: " + request.getRequestURI());
    	return "forward:/" + request.getRequestURI().substring(5);
    }
    

}