package com.github.epw_dev.slack_post_stock.controllers.webhooks;

import com.github.epw_dev.slack_post_stock.domains.SlackWebhookRequest;
import com.github.epw_dev.slack_post_stock.domains.SlackWebhookType;
import com.github.epw_dev.slack_post_stock.domains.requests.ChallengeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UrlVerificationHandler implements WebhookHandlers.WebhookHandler<ChallengeRequest> {

  @Override
  public boolean shouldApply(SlackWebhookRequest req) {
    return req.typeIs(SlackWebhookType.UrlVerification);
  }

  @Override
  public ResponseEntity apply(ChallengeRequest req) {
    log.info(String.format("challenge token: %s", req.getChallenge()));
    return ResponseEntity.ok()
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .body(new Response(req.getChallenge()));
  }

  @Data
  @AllArgsConstructor
  static class Response {
    private String challenge;
  }
}
