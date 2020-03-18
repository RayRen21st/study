package study.resteasy;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class); 
	
	public static void main(String[] args) throws Exception {
		start();
	}

	private static void start() throws Exception {
		Server server = new Server(18080);
		WebAppContext webappContext = new WebAppContext();
		webappContext.setContextPath("/");
		webappContext.setWar(new File("src/main/webapp").getAbsolutePath());
//		webappContext.setConfigurationClasses(new String[]{"org.eclipse.jetty.annotations.AnnotationConfiguration"});
//		webappContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*class");
		server.setHandler(webappContext);
		server.start();
//		server.dumpStdErr();
		server.join();
		
		
	}

}
