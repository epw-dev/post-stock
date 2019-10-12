package com.github.epw_dev.slack_post_stock.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SlackWebhookType {
  UrlVerification("url_verification");

  private String value;
}
