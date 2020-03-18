package study.logback.logrotate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriter {

	public static void main(String[] args) throws InterruptedException {

		int i = 1;
		while (true) {
			Logger logger = LoggerFactory.getLogger(LogWriter.class);
			logger.info("Hello World");
			i++;
			if (i == 1000) {
				Thread.sleep(1000);
				i = 0;
			}
		}

	}

}
