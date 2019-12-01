package org.apache.flink.playgrounds.ops.clickcount;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.playgrounds.ops.clickcount.model.OrderEvent;
import org.apache.flink.playgrounds.ops.clickcount.model.OrderEventSerializationSchema;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jianyuan
 * @version : OrderEventGenerator.java, v0.1 2019-12-01 11:42 下午 by jianyuan
 */
public class OrderEventGenerator {
    public static final int EVENT_PER_WINDOW = 100;

    public static final Time WINDOW_SIZE = Time.of(30, TimeUnit.SECONDS);

    private static final long DELAY = WINDOW_SIZE.toMilliseconds() / EVENT_PER_WINDOW;

    private static final List<String> eventNames = Arrays.asList("addToCart", "payment", "recallOrder", "transfer");
    private static final List<String> userIds = Arrays.asList("1001", "2001", "3001", "4001", "5001", "6001");
    private static final List<String> merchantIds = Arrays.asList("M001", "M002", "M003", "M004");
    private static final List<String> positions = Arrays.asList("beijing", "shanghai", "hangzhou", "nanjin", "chengdu", "rugao");

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        String topic = params.get("topic", "order-input");
        Properties kafkaProps = createKafkaProperties(params);

        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<byte[], byte[]>(kafkaProps);
        OrderIterator orderIterator = new OrderIterator();
        while (true) {
            ProducerRecord<byte[], byte[]> record = new OrderEventSerializationSchema(topic).serialize(
                    orderIterator.next(), System.currentTimeMillis()
            );
            producer.send(record);
            Thread.sleep(DELAY);
        }
    }

    private static Properties createKafkaProperties(final ParameterTool params) {
        String brokers = params.get("bootstrap.servers", "localhost:9092");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
        return properties;
    }

    static class OrderIterator {
        private final Random random;
        private final AtomicLong eventIdGen;

        OrderIterator() {
            random = new Random(System.currentTimeMillis());
            eventIdGen = new AtomicLong(0);
        }

        OrderEvent next() {
            return new OrderEvent(new Date(),  nextEventId(), nextEventName(), nextUserId(), nextMerchantId(), nextPosition(), nextDouble());
        }

        private String nextEventId() {
            long eventId = eventIdGen.getAndIncrement();
            if (eventId == 10000000000L) {
                eventIdGen.set(0L);
            }
           return StringUtils.leftPad("" + eventId, 10, "0");
        }

        private String nextUserId() {
            int userIndex = Math.abs(random.nextInt() % userIds.size());
            return userIds.get(userIndex);
        }

        private String nextEventName() {
            int eventNameIndex = Math.abs( random.nextInt() % eventNames.size());
            return eventNames.get(eventNameIndex);
        }

        private String nextMerchantId() {
            return merchantIds.get(Math.abs(random.nextInt() % merchantIds.size()));
        }

        private String nextPosition() {
            return positions.get(Math.abs(random.nextInt() % positions.size()));
        }

        private Double nextDouble() {
            return Math.abs(random.nextDouble());
        }
    }
}
