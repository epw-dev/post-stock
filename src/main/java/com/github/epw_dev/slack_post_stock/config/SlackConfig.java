package com.github.epw_dev.slack_post_stock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.slack")
public class SlackConfig {
  private String token;
}
