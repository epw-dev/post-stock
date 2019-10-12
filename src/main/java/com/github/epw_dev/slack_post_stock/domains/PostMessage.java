package com.github.epw_dev.slack_post_stock.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostMessage {
  private String id;
  private String text;
  private UserInfo user;
  private LocalDateTime createdAt;

  @Getter
  @AllArgsConstructor
  public static class Partial {
    private String text;
    private String user;
    private LocalDateTime createdAt;
  }
}
