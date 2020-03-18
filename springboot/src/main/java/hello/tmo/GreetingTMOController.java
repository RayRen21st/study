package hello.tmo;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.Greeting;
import hello.GreetingController;

@RestController
@RequestMapping("/style1")
public class GreetingTMOController extends GreetingController {
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final static Logger logger = LoggerFactory.getLogger(GreetingTMOController.class);

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="tmo") String name) {
    	logger.info("TMO API is invoked");
    	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
