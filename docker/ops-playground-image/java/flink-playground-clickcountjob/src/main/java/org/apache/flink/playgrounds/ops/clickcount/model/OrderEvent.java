package org.apache.flink.playgrounds.ops.clickcount.model;

import org.apache.flink.calcite.shaded.com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

/**
 * @author jianyuan
 * @version : OrderEvent.java, v0.1 2019-12-01 11:38 下午 by jianyuan
 */
public class OrderEvent {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss:SSS")
    private Date timestamp;
    private String eventId;
    private String eventName;
    private String userId;
    private String merchantId;
    private String position;
    private Double amt;

    public OrderEvent() {
    }

    public OrderEvent(Date timestamp, String eventId, String eventName, String userId, String merchantId,
                      String position, Double amt) {
        this.timestamp = timestamp;
        this.eventId = eventId;
        this.eventName = eventName;
        this.userId = userId;
        this.merchantId = merchantId;
        this.position = position;
        this.amt = amt;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        OrderEvent that = (OrderEvent) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(eventName, that.eventName) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(position, that.position) &&
                Objects.equals(amt, that.amt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, eventId, eventName, userId, merchantId, position, amt);
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "timestamp=" + timestamp +
                ", eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", userId='" + userId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", position='" + position + '\'' +
                ", amt=" + amt +
                '}';
    }
}
