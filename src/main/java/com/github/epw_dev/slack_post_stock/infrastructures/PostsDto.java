package com.github.epw_dev.slack_post_stock.infrastructures;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class PostsDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private String text;

  private String userId;

  private LocalDateTime createdAt;
}
