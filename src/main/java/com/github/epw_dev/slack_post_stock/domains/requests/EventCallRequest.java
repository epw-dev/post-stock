package com.github.epw_dev.slack_post_stock.domains.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.epw_dev.slack_post_stock.domains.SlackCallbackEventType;
import lombok.Data;
import lombok.val;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface EventCallRequest {
  CallBackEvent getEvent();

  @Data
  class CallBackEvent {
    private String type;
    private String channel;
    private String user;
    private String text;

    @JsonProperty("event_ts")
    private String eventTs;

    @JsonProperty("channel_type")
    private String channelType;

    public LocalDateTime getEventTimeStamp() {
      val num = new BigDecimal(eventTs);
      val instant = Instant.ofEpochSecond(num.longValue());
      return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Tokyo"));
    }

    public boolean typeIs(SlackCallbackEventType targetType) {
      return targetType.getValue().equals(type);
    }
  }
}
