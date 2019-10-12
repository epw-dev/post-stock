package com.github.epw_dev.slack_post_stock.controllers;

import com.github.epw_dev.slack_post_stock.config.SlackConfig;
import com.github.epw_dev.slack_post_stock.domains.PostMessageRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainController {

  private SlackConfig config;

  private PostMessageRepository postMessageRepository;

  @GetMapping("target")
  public String targetChannelId() {
    return config.getTargetChannelId();
  }

  @GetMapping(path = "posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity posts() {
    val list = postMessageRepository.findAll();
    return ResponseEntity.ok(new ListResponse(list));
  }

  @Value
  public static class ListResponse<T> {
    private List<T> records;
  }
}
