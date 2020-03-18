package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final static Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	logger.info("Standard API is invoked /greeting");
    	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping("/greeting/world")
    public Greeting greeting() {
    	logger.info("Standard API is invoked /greeting/world");
    	return new Greeting(counter.incrementAndGet(), String.format(template, "World"));
    }
}
