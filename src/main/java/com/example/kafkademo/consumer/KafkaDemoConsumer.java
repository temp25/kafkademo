package com.example.kafkademo.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Collections;

import com.example.kafkademo.config.KafkaDemoProps;

public class KafkaDemoConsumer {

	private Consumer<String, String> consumer;

	public KafkaDemoConsumer() {
		this.consumer = this.getConsumer();
		this.consumer.subscribe(Collections.singletonList(KafkaDemoProps.TOPIC));
	}

	private Consumer<String, String> getConsumer() {
		return new KafkaConsumer<String, String>(KafkaDemoProps.getProperties());
	}

	public void receiveMessage() {
		
		int giveUp = 100;
		int noRecordsCount = 0;

		while(true){
			ConsumerRecords<String, String> consumerRecords = this.consumer.poll(1000);

			if(consumerRecords.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp) {
					break;
				} else {
					continue;
				}
			}

			consumerRecords.forEach(record -> {
				System.out.printf(
					"Consumer Record:(%s, %s, %d, %d)\n",
					record.key(),
					record.value(),
					record.partition(),
					record.offset()
					);
			});

			consumer.commitAsync();
		}
	}

	@Override
	protected void finalize(){
		this.consumer.close();
	}

}