
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;


class Sample {
    
    private static String TOPIC;
    private final static String BOOTSTRAP_SERVERS = "velomobile-01.srvs.cloudkafka.com:9094,velomobile-02.srvs.cloudkafka.com:9094,velomobile-03.srvs.cloudkafka.com:9094";
    
    static Properties getKafkaProps(String username, String password, String brokers){
        TOPIC = username + "-default";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", username + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);
        
        return props;
    }
    
    private static Producer<String, String> createProducer() {
        
        String brokers = System.getenv("CLOUDKARAFKA_BROKERS");
        String username = System.getenv("CLOUDKARAFKA_USERNAME");
        String password = System.getenv("CLOUDKARAFKA_PASSWORD");
        System.out.printf("\n\nBroker : %s\nUser : %s\nPassword\n\n",brokers, username, password);
		
        Properties producerKafkaProps = getKafkaProps(username, password, brokers);
        
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerKafkaProps);
        
        return producer;
    }
    
    public static void main(String[] args)throws Exception {
        final Producer<String, String> producer = createProducer();
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "Test msg Key "+01, "Test message cloudkarafka #" + 01);
        long time = System.currentTimeMillis();
        RecordMetadata metadata = producer.send(record).get();
        long elapsedTime = System.currentTimeMillis() - time;
        System.out.printf("\n\nsent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n\n", record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
        producer.flush();
        producer.close();
    }
}