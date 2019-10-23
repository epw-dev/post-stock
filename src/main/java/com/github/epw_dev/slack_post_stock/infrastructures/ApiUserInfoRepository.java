package com.github.epw_dev.slack_post_stock.infrastructures;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.epw_dev.slack_post_stock.config.SlackConfig;
import com.github.epw_dev.slack_post_stock.domains.UserInfo;
import com.github.epw_dev.slack_post_stock.domains.UserInfoRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ApiUserInfoRepository implements UserInfoRepository {

  @Lazy @Resource private UserInfoRepository self;

  @Autowired private SlackConfig config;

  @Autowired private RestTemplate restTemplate;

  @Cacheable("users.list")
  @Override
  public List<UserInfo> findAll() {
    val url =
        UriComponentsBuilder.fromUriString("https://slack.com/api/users.list")
            .queryParam("token", config.getToken())
            .toUriString();
    val res = restTemplate.getForEntity(url, UsersListResponse.class);
    val body = res.getBody();
    if (!body.ok) {
      throw new RuntimeException(
          String.format("url: %s, sc: %s, reason: %s", url, res.getStatusCode(), body.error));
    }
    log.info("find all");
    return body.members.stream()
        .map(UsersListResponse.UserInfoData::toModel)
        .collect(Collectors.toList());
  }

  @Cacheable("users.info")
  @Override
  public Optional<UserInfo> findBy(UserInfo.UserId id) {
    log.info(id.asText());
    for (val user : this.self.findAll()) {
      if (user.getId().equals(id)) {
        return Optional.of(user);
      }
    }
    return Optional.empty();
  }

  @Data
  public static class UsersListResponse {
    private boolean ok;
    private String error;
    private List<UserInfoData> members;

    @Data
    public static class UserInfoData {
      private String id;
      private UserInfoProfile profile;

      @Data
      public static class UserInfoProfile {
        @JsonProperty("display_name")
        private String displayName;

        @JsonProperty("image_original")
        private String imageOriginal;
      }

      UserInfo toModel() {
        val userId = new UserInfo.UserId(this.id);
        return new UserInfo(userId, this.profile.displayName, this.profile.imageOriginal);
      }
    }
  }
}
