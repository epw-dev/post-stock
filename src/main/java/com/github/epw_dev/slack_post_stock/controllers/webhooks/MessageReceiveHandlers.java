package com.github.epw_dev.slack_post_stock.controllers.webhooks;

import com.github.epw_dev.slack_post_stock.config.SlackConfig;
import com.github.epw_dev.slack_post_stock.domains.*;
import com.github.epw_dev.slack_post_stock.domains.requests.EventCallRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class MessageReceiveHandlers implements WebhookHandlers.WebhookHandler<EventCallRequest> {

  private SlackConfig config;

  private PostMessageRepository repository;

  @Override
  public boolean shouldApply(SlackWebhookRequest req) {
    return (req.typeIs(SlackWebhookType.EventCallback)
        && req.getEvent().typeIs(SlackCallbackEventType.Message)
        && req.getEvent().getChannel().equals(config.getTargetChannelId()));
  }

  @Override
  public ResponseEntity apply(EventCallRequest req) {
    val ev = req.getEvent();
    log.info(ev.toString());
    val msg = new PostMessage.Partial(ev.getText(), ev.getUser(), ev.getEventTimeStamp());
    val ret = repository.save(msg);
    log.info("created: " + ret.toString());
    return ResponseEntity.ok(null);
  }
}
