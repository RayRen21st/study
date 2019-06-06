
package study.kafka.rebalance;

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

//Mutiple Topic - Rebalance
public class Consumer3 {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer1.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.157.131:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "javaClient");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		List<String> topics = Arrays.asList("mytopic-4", "mytopic-5");
		
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
		
		consumer.poll(100);
		
		AtomicBoolean secondSubscription = new AtomicBoolean(false);
		
		while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         if(!records.isEmpty()) {
	        	 logger.info("end poll with {} records ----------------", records.count());
	         }
	         for (ConsumerRecord<String, String> record : records) {
	             logger.info("partition = {}, offset = {}, timestamp = {}, key = {}, value = {}", record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
	             
	             TopicPartition tp = new TopicPartition(record.topic(), record.partition());
	             OffsetAndMetadata offset = new OffsetAndMetadata(record.offset() + 1);
	             Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
	             map.put(tp, offset);
	             consumer.commitSync(map);
	         }
	         if(secondSubscription.compareAndSet(false, true)){
	        	 Thread.sleep(10000);
	        	 consumer.subscribe(Arrays.asList("mytopic-4"));
	         }
	     }
		
	}

}
