
package study.kafka.rebalance;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Heartbeat Interval
//Poll Interval
//rebalance interval
public class Consumer4 {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer1.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.157.131:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "javaClient");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        consumerProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5000);
		
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		List<String> topics = Arrays.asList("mytopic-10", "mytopic-11");
		
		consumer.subscribe(topics, new ConsumerRebalanceListener() {
			
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				logger.info("Partition Revoked: {}", partitions);
				
			}
			
			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				logger.info("Partition Assigned: {}", partitions);
			}
		});
		
		
		while (true) {
//			 consumer.pause(Collections.emptySet());
			 logger.info("start poll");
	         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
	         if(!records.isEmpty()) {
	        	 logger.info("end poll with {} records ----------------", records.count());
	         }
	         consumer.subscribe(Arrays.asList("mytopic-10"));
//	         consumer.resume(Collections.emptySet());
	         for (ConsumerRecord<String, String> record : records) {
	             logger.info("partition = {}, offset = {}, timestamp = {}, key = {}, value = {}", record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
	             
	             TopicPartition tp = new TopicPartition(record.topic(), record.partition());
	             OffsetAndMetadata offset = new OffsetAndMetadata(record.offset() + 1);
	             Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
	             map.put(tp, offset);
	             consumer.commitSync(map);
	         }
	     }
		
	}

}
