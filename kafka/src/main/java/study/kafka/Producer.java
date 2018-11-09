package study.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.157.128:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 3; i++) {
			Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("mytopic", Integer.toString(i), Integer.toString(i)));
			future.get();
			logger.info("message sent");
		}

		producer.close();
	}

}
