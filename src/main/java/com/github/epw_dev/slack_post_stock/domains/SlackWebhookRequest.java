package com.github.epw_dev.slack_post_stock.domains;

import com.github.epw_dev.slack_post_stock.domains.requests.ChallengeRequest;
import com.github.epw_dev.slack_post_stock.domains.requests.EventCallRequest;
import lombok.Data;

@Data
public class SlackWebhookRequest implements ChallengeRequest, EventCallRequest {
  private String type;
  private String challenge;
  private Event event;

  public boolean typeIs(SlackWebhookType type) {
    return type.getValue().equals(this.type);
  }
}
