package study.logback.logrotate;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class SLMMDCConverter extends ClassicConverter{
    private ObjectMapper mapper = new ObjectMapper();
 
    @Override
    public String convert(ILoggingEvent event) {
        Map<String, String> mdcProperty = event.getMDCPropertyMap();
        String json = "";
        try {
            json = mapper.writeValueAsString(mdcProperty);
        } catch (IOException e) { //NOSONAR
            // Cannot print log since this is a log converter
            e.printStackTrace(); //NOSONAR
        }
        if (!"".equals(json) && !"{}".equals(json)) {
            return json;
        } else {
            return "{}";
        }
    }
}
