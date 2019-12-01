package org.apache.flink.playgrounds.ops.clickcount.model;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;

/**
 * @author jianyuan
 * @version : OrderEventSerializationSchema.java, v0.1 2019-12-02 12:18 上午 by jianyuan
 */
public class OrderEventSerializationSchema implements KafkaSerializationSchema<OrderEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String topic;

    public OrderEventSerializationSchema() {
    }

    public OrderEventSerializationSchema(String topic) {
        this.topic = topic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(OrderEvent orderEvent, @Nullable Long timestamp) {
        try {
            return new ProducerRecord<>(topic, objectMapper.writeValueAsBytes(orderEvent));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not serialize record: " + orderEvent, e);
        }
    }
}
