
package study.kafka;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	public static void main(String[] args) {
		
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.157.128:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "javaClient");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		
		consumer.subscribe(Arrays.asList("mytopic"));
		consumer.seekToBeginning(Collections.EMPTY_SET);
		while (true) {
			 logger.debug("begin poll----------------");
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         logger.debug("end poll with {} records ----------------", records.count());
	         records.isEmpty();
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	     }
		
	}

}
