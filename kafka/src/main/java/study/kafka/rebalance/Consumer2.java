
package study.kafka.rebalance;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer2 {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer2.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.157.131:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "javaClient1");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
		
		String topicName = "mytopic-10";
		
		
		List<TopicPartition> tpList = consumer.partitionsFor(topicName).stream().map(pi -> new TopicPartition(topicName, pi.partition())).collect(Collectors.toList());
				
		consumer.subscribe(Arrays.asList(topicName));
		logCommitedOffset(consumer, tpList);
		
		logger.info("assigned partitions before poll: {}", consumer.assignment());
		consumer.poll(Duration.ofMillis(100));
		logger.info("assigned partitions after poll: {}", consumer.assignment());
//		Map<TopicPartition, Long> partitionToOffset = consumer.beginningOffsets(tpList);
		
//		Map<TopicPartition, Long> partitionToOffset = consumer.endOffsets(tpList);
		
		Map<TopicPartition, Long> timestampsToSearch = tpList.stream().collect(Collectors.toMap(tp -> tp, tp -> 1548753180000l));
		Map<TopicPartition, Long> partitionToOffset = consumer.offsetsForTimes(timestampsToSearch)
				.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->  entry.getValue() == null ? 0 : entry.getValue().offset()));
		logger.info("Partition To Offset {}", partitionToOffset);
		
		
		
       Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = partitionToOffset.entrySet().stream()
    		   .collect(Collectors.toMap(Map.Entry::getKey, entry -> new OffsetAndMetadata(1)));
        
        
       consumer.commitSync(offsetsToCommit);
       logCommitedOffset(consumer, tpList);
       
       logger.info("end of offset {}", consumer.endOffsets(consumer.assignment()));
       
       
//       consumer.unsubscribe();
//       consumer.close();
//       
//       logger.info("unsubscribe close and subscribe again");
//       KafkaConsumer<String, String> consumer2 = new KafkaConsumer<>(consumerProps);
//       consumer2.subscribe(Arrays.asList(topicName));
//       logCommitedOffset(consumer2, tpList);
       
       
//		partitionToOffset.forEach((partition, offset) -> consumer.seek(partition, offset));
//       consumer.pause(consumer.assignment()); 
       
//       Thread.sleep(10*60*1000);
        
        
        consumer.close(); //trigger rebalance
		
	}

	private static void logCommitedOffset(KafkaConsumer<String, String> consumer, List<TopicPartition> tpList) {
		for(TopicPartition tp : tpList) {
			logger.info("Commited Offset for Partition {}: {}", tp.partition(), consumer.committed(tp));
		}
	}

}
