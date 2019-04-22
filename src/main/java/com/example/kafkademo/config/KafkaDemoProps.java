package com.example.kafkademo.config;

import com.example.kafkademo.helper.EncryptDecrypt;

import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class KafkaDemoProps {

    private static Properties properties;
    
    static {
    	properties = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static final String SERIALIZER = StringSerializer.class.getName();
    public static final String DESERIALIZER = StringDeserializer.class.getName();
    public static final String USERNAME = EncryptDecrypt.decryptString(properties.getProperty("CLOUDKARAFKA_USERNAME"));
    public static final String PASSWORD = EncryptDecrypt.decryptString(properties.getProperty("CLOUDKARAFKA_PASSWORD"));
    public static final String BROKERS = EncryptDecrypt.decryptString(properties.getProperty("CLOUDKARAFKA_BROKERS"));
    public static final String TOPIC = USERNAME + "-default";

    public static Properties getProperties() {
    	String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, USERNAME, PASSWORD);

    	Properties props = new Properties();
        props.put("bootstrap.servers", BROKERS);
        props.put("group.id", USERNAME + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", DESERIALIZER);
        props.put("value.deserializer", DESERIALIZER);
        props.put("key.serializer", SERIALIZER);
        props.put("value.serializer", SERIALIZER);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);
        
        return props;
    }
    
}