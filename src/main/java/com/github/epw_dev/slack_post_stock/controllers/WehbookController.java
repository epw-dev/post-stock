package com.github.epw_dev.slack_post_stock.controllers;

import com.github.epw_dev.slack_post_stock.controllers.webhooks.WebhookHandlers;
import com.github.epw_dev.slack_post_stock.domains.SlackWebhookRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@Slf4j
@RestController
@RequestMapping("/webhook")
@AllArgsConstructor
public class WehbookController {

  private WebhookHandlers handlers;

  @PostMapping
  public ResponseEntity index(@RequestBody SlackWebhookRequest req) {
    try {
      val hdl = handlers.getBy(req).orElseGet(EmptyHandler::new);
      return hdl.apply(req);
    } catch (Exception e) {
      log.error("error occurred", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  static class EmptyHandler implements Function<SlackWebhookRequest, ResponseEntity> {
    @Override
    public ResponseEntity apply(SlackWebhookRequest aVoid) {
      return ResponseEntity.ok(null);
    }
  }
}
