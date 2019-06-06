package study.resteasy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
	
	public static void main(String[] args) throws Exception {
		start();
	}

	private static void start() throws Exception {
		Server server = new Server(18080);
		WebAppContext webappContext = new WebAppContext();
		webappContext.setContextPath("/");
		webappContext.setWar("src/main/webapp");
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.set
		webappContext.setServ
		webappContext.setConfigurationClasses(new String[]{"org.eclipse.jetty.annotations.AnnotationConfiguration"});
//		webappContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*class");
		server.setHandler(webappContext);
		server.start();
//		server.dumpStdErr();
		server.join();
		
		
	}

}
