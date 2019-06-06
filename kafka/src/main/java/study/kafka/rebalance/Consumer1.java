
package study.kafka.rebalance;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer1 {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer1.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.157.131:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "javaClient");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		String topicName = "mytopic-4"
				+ "";
		TopicPartition tp0 = new TopicPartition(topicName, 0);
        TopicPartition tp1 = new TopicPartition(topicName, 1);
		consumer.subscribe(Arrays.asList(topicName), new ConsumerRebalanceListener() {
			
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				logger.info("Partition Revoked: {}", partitions);
				
			}
			
			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				logger.info("Partition Assigned: {}", partitions);
			}
		});
		
		consumer.poll(Duration.ofMillis(100));
		
//		logger.info("tp0 position: {}, tp1 position: {}", consumer.position(tp0), consumer.position(tp1));
		consumer.seekToBeginning(Collections.EMPTY_SET);
		logger.info("assigned partitions: {}", consumer.assignment());
//		logger.info("tp0 offset: {}, tp1 offset: {}", consumer.committed(tp0).offset(), consumer.committed(tp1).offset());
//		logger.info("tp0 position: {}, tp1 position: {}", consumer.position(tp0), consumer.position(tp1));
		
		while (true) {
			 logger.debug("begin poll----------------");
	         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(0));
	         if(!records.isEmpty()) {
	        	 logger.info("end poll with {} records ----------------", records.count());
	        	 logger.info("tp0 offset: {}, tp1 offset: {}", consumer.committed(tp0).offset(), consumer.committed(tp1).offset());
	         }
	         for (ConsumerRecord<String, String> record : records) {
	             logger.info("partition = {}, offset = {}, timestamp = {}, key = {}, value = {}", record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
	             
	             TopicPartition tp = new TopicPartition(record.topic(), record.partition());
	             OffsetAndMetadata offset = new OffsetAndMetadata(record.offset() + 1);
	             Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
	             map.put(tp, offset);
	             consumer.commitSync(map);
	             logger.info("tp0 offset: {}, tp1 offset: {}", consumer.committed(tp0).offset(), consumer.committed(tp1).offset());
//	             System.exit(0); consumer.close();
	         }
	         Thread.sleep(1000);
	     }
		
	}

}
