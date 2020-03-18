package hello.rjio;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Greeting;

@RestController
@RequestMapping("/style2")
public class GreetingRjioController {
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final static Logger logger = LoggerFactory.getLogger(GreetingRjioController.class);

    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="rjio") String name) {
    	logger.info("Rjio API is invoked");
    	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
