package com.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.ConsumerNetworkThread;
import org.apache.kafka.clients.consumer.internals.HeartbeatRequestManager;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.InternetProtocol;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class MyTest {

    @Test
    public void should_success_because_consumer_try_to_poll_after_background_thread_get_valid_coordinator() throws InterruptedException, ExecutionException {
        setUpBroker();
        final KafkaProducer<Byte[], String> producer = getProducer();
        // Ensure that producer send mssage completely.
        producer.send(new ProducerRecord("hello", null, "hello-value")).get();

        final KafkaConsumer<Object, String> consumer = getConsumer();

        // Should wait that consumer background thread get valid coordinator.
        Thread.sleep( 1000);
        consumer.subscribe(Collections.singletonList("hello"));

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            System.out.println("i :" + i);
            ConsumerRecords<Object, String> poll = consumer.poll(Duration.ofSeconds(1));
            if (!poll.isEmpty()) {
                countDownLatch.countDown();
                break;
            }
        }

        Thread.sleep(1000000);

        assertThat(countDownLatch.await(10, TimeUnit.SECONDS)).isTrue();
    }


    @Test
    public void should_fail_because_consumer_try_to_poll_before_background_thread_get_valid_coordinator() throws InterruptedException, ExecutionException {
        setUpBroker();
        final KafkaProducer<Byte[], String> producer = getProducer();
        // Ensure that producer send message completely.
        producer.send(new ProducerRecord("hello", null, "hello-value")).get();
        final KafkaConsumer<Object, String> consumer = getConsumer();
        consumer.subscribe(Collections.singletonList("hello"));

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            if (i == 20) {
                System.out.println("retry consume");
                consumer.subscribe(Collections.singletonList("hello"));
                Thread.sleep(5000);
            }
            System.out.println("i :" + i);
            ConsumerRecords<Object, String> poll = consumer.poll(Duration.ofSeconds(1));
            if (!poll.isEmpty()) {
                countDownLatch.countDown();
                break;
            }
        }

        assertThat(countDownLatch.await(10, TimeUnit.SECONDS)).isFalse();
    }

    KafkaProducer<Byte[], String> getProducer() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:12000");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, BytesSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(props);
    }

    KafkaConsumer<Object, String> getConsumer() {
        Properties propsConsumer = new Properties();
        propsConsumer.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, BytesDeserializer.class.getName());
        propsConsumer.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propsConsumer.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:12000");
        propsConsumer.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "hello");
        propsConsumer.setProperty(ConsumerConfig.GROUP_PROTOCOL_CONFIG, "consumer");
        propsConsumer.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(propsConsumer);
    }

    void setUpBroker() {
        final DockerImageName imageName = DockerImageName.parse("bitnami/kafka:3.7.0");
        final GenericContainer broker = new GenericContainer(imageName);

        broker.addEnv("KAFKA_CFG_NODE_ID", "0");
        broker.addEnv("KAFKA_CFG_KRAFT_CLUSTER_ID", "HsDBs9l6UUmQq7Y5E6bNlw");
        broker.addEnv("KAFKA_CFG_CONTROLLER_QUORUM_VOTERS", "0@localhost:9093");
        broker.addEnv("KAFKA_CFG_PROCESS_ROLES", "controller,broker");

        broker.addEnv("KAFKA_CFG_LISTENERS", "INTERNAL://localhost:29092, PLAINTEXT://0.0.0.0:9092, EXTERNAL://:9094, CONTROLLER://:9093");
        broker.addEnv("KAFKA_CFG_ADVERTISED_LISTENERS", "INTERNAL://localhost:29092, PLAINTEXT://localhost:9092, EXTERNAL://127.0.0.1:12000");
        broker.addEnv("KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP", "CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT,INTERNAL:PLAINTEXT");
        broker.addEnv("KAFKA_CFG_CONTROLLER_LISTENER_NAMES", "CONTROLLER");
        broker.addEnv("KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE", "true");
        broker.addEnv("KAFKA_CFG_INTER_BROKER_LISTENER_NAME", "INTERNAL");
        broker.addEnv("KAFKA_CFG_GROUP_INITIAL_REBALANCE_DELAY_MS", "0");

        broker.addEnv("KAFKA_CFG_TRANSACTION_STATE_LOG_MIN_ISR", "1");
        broker.addEnv("KAFKA_CFG_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", "1");
        broker.addEnv("KAFKA_CFG_OFFSETS_TOPIC_REPLICATION_FACTOR", "1");
        broker.addEnv("KAFKA_CFG_DEFAULT_REPLICATION_FACTOR", "1");

        broker.addEnv("KAFKA_CFG_GROUP_COORDINATOR_REBALANCE_PROTOCOLS", "classic,consumer");
        broker.addEnv("KAFKA_CFG_TRANSACTION_PARTITION_VERIFICATION_ENABLE", "false");

        final List<String> portsBinding = Collections.singletonList(
                String.format("%d:%d/%s", 12000, 9094, InternetProtocol.TCP.toDockerNotation()));
        broker.setPortBindings(portsBinding);
        broker.start();
    }

}
