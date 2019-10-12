package com.github.epw_dev.slack_post_stock.domains.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public interface EventCallRequest {
  Event getEvent();

  @Data
  class Event {
    private String type;
    private String channel;
    private String user;
    private String text;
    private String ts;

    @JsonProperty("event_ts")
    private String eventTs;

    @JsonProperty("channel_type")
    private String channelType;
  }
}
