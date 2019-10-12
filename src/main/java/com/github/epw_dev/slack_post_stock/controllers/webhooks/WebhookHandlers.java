package com.github.epw_dev.slack_post_stock.controllers.webhooks;

import com.github.epw_dev.slack_post_stock.domains.SlackWebhookRequest;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class WebhookHandlers {

  private List<WebhookHandler<? super SlackWebhookRequest>> handlers;

  public Optional<Function<? super SlackWebhookRequest, ResponseEntity>> getBy(
      SlackWebhookRequest req) {
    for (val hdl : this.handlers) {
      if (hdl.shouldApply(req)) {
        return Optional.of(hdl);
      }
    }
    return Optional.empty();
  }

  public interface WebhookHandler<T> extends Function<T, ResponseEntity> {
    boolean shouldApply(SlackWebhookRequest req);
  }
}
