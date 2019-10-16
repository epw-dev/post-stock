package com.github.epw_dev.slack_post_stock.domains;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class UserInfo {
  private UserId id;
  private String displayName;
  private String avatarUrl;

  @AllArgsConstructor
  @JsonSerialize(using = ToTextSerializer.class)
  public static class UserId implements HasIdValue {
    private String id;

    public String asText() {
      return id;
    }
  }
}
