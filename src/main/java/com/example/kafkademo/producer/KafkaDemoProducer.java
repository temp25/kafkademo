package com.example.kafkademo.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import com.example.kafkademo.config.KafkaDemoProps;

public class KafkaDemoProducer {

	private Producer<String, String> producer;
	private static int MESSAGE_COUNT = 0;

	public KafkaDemoProducer(){
		this.producer = this.getDemoProducer();
	}

	private Producer<String, String> getDemoProducer() {
		return new KafkaProducer<String, String>(KafkaDemoProps.getProperties());
	}

	public void sendMessage(String message)throws InterruptedException, ExecutionException {

		ProducerRecord<String, String> record = new ProducerRecord<>(KafkaDemoProps.TOPIC, "Message Key "+(++MESSAGE_COUNT), message);
        long time = System.currentTimeMillis();
        RecordMetadata metadata = this.producer.send(record).get();
        long elapsedTime = System.currentTimeMillis() - time;
        System.out.printf("\n\nsent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n\n", record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
        
	}

	@Override
	protected void finalize(){
		this.producer.flush();
        this.producer.close();
	}

}