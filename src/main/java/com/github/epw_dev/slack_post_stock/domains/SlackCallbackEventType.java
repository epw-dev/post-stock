package com.github.epw_dev.slack_post_stock.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SlackCallbackEventType {
  Message("message");

  private String value;
}
