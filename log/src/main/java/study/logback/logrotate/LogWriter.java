package study.logback.logrotate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogWriter {

	public static void main(String[] args) throws InterruptedException {

		int i = 1;
		while (true) {
			MDC.put("TraceId", UUID.randomUUID().toString());
			Logger logger = LoggerFactory.getLogger(LogWriter.class);
			Logger auditLogger = LoggerFactory.getLogger("AuditLogger");
			auditLogger.info("Audit Hello");
			logger.info("Hello World");
			i++;
			if (i == 1000) {
				Thread.sleep(1000);
				i = 0;
			}
		}

	}

}
