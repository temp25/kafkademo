package com.example.kafkademo.main;

//import com.example.kafkademo.config.KafkaDemoProps;
import com.example.kafkademo.producer.KafkaDemoProducer;
import com.example.kafkademo.consumer.KafkaDemoConsumer;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedWriter;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

class KafkaDemoMain {
    public static void main(String[] args) {
        /*
        System.out.println("Encryption decryption test\n");
        String testStr = "Sample@123";
        System.out.println("Test str "+testStr);
        System.out.println("Encrypting string : "+testStr);
        System.out.println("");
        String encryptedStr = EncryptDecrypt.encryptString(testStr);
        System.out.println("Encrypted string : "+encryptedStr);
        String decryptedStr = EncryptDecrypt.decryptString(encryptedStr);
        System.out.println("Decrypted string : "+decryptedStr);
        System.out.println("Decrypted string equals original string : "+decryptedStr.equals(testStr));
        */
       
       /*
       String uname = "19uds2d2";
       String pwd = "48yZeR87btROThUIxvSzmooG4v7QZ3Pe";
       String brokers = "velomobile-01.srvs.cloudkafka.com:9094,velomobile-02.srvs.cloudkafka.com:9094,velomobile-03.srvs.cloudkafka.com:9094";
       
       StringBuilder sb = new StringBuilder();
       String eUname = EncryptDecrypt.encryptString(uname);
       sb.append("CLOUDKARAFKA_USERNAME="+eUname);
       sb.append(System.lineSeparator());
       String ePwd = EncryptDecrypt.encryptString(pwd);
       sb.append("CLOUDKARAFKA_PASSWORD="+ePwd);
       sb.append(System.lineSeparator());
       String eBrokers = EncryptDecrypt.encryptString(brokers);
       sb.append("CLOUDKARAFKA_BROKERS="+eBrokers);
       sb.append(System.lineSeparator());
       
       System.out.println("Encrypted properties : \n"+sb.toString());
       */
       
       /*String dUname = EncryptDecrypt.decryptString(eUname);
       String dPwd = EncryptDecrypt.decryptString(ePwd);
       String dBrokers = EncryptDecrypt.decryptString(eBrokers);
       
       System.out.println("Decrypted name equals original string : "+dUname.equals(uname));
       System.out.println("Decrypted password equals original string : "+dPwd.equals(pwd));
       System.out.println("Decrypted brokers equals original string : "+dBrokers.equals(brokers));*/
        /*
        try {
            Path newFilePath = Paths.get("src/main/resources/application.properties");
            Files.createFile(newFilePath);
            try (BufferedWriter writer = Files.newBufferedWriter(newFilePath)) {
                writer.write(sb.toString());
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        */
       
       /*
       Properties properties = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println("CLOUDKARAFKA_USERNAME : "+properties.getProperty("CLOUDKARAFKA_USERNAME"));
        System.out.println("CLOUDKARAFKA_PASSWORD : "+properties.getProperty("CLOUDKARAFKA_PASSWORD"));
        System.out.println("CLOUDKARAFKA_BROKERS : "+properties.getProperty("CLOUDKARAFKA_BROKERS"));
        */
        
        /*System.out.println("Username : "+KafkaDemoProps.USERNAME);
        System.out.println("Password : "+KafkaDemoProps.PASSWORD);
        System.out.println("Brokers : "+KafkaDemoProps.BROKERS);
        System.out.println("Topic Name : "+KafkaDemoProps.TOPIC);*/

        
        if(args.length > 0){
          String type = args[0];
          
          if ("producer".equals(type)){
            KafkaDemoProducer kafkaDemoProducer = new KafkaDemoProducer();
            Scanner sc = new Scanner(System.in);
            String message;
            try {
              while((message = sc.next()) != null && !message.equals("exit")) {
                kafkaDemoProducer.sendMessage(message);
              }
            }catch(InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
          
          } else if ("consumer".equals(type)) {
            
            KafkaDemoConsumer kafkaDemoConsumer = new KafkaDemoConsumer();
            kafkaDemoConsumer.receiveMessage();

          } else {
            System.out.println("Argument passed is of invalid type");
          }
        } else {
            System.out.println("Expected atleast one argument either producer/consumer");
        }


    }
}