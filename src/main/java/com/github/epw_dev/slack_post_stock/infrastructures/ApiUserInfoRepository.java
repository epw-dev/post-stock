package com.github.epw_dev.slack_post_stock.infrastructures;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.epw_dev.slack_post_stock.config.SlackConfig;
import com.github.epw_dev.slack_post_stock.domains.UserInfo;
import com.github.epw_dev.slack_post_stock.domains.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Repository
public class ApiUserInfoRepository implements UserInfoRepository {

  private SlackConfig config;

  private RestTemplate restTemplate;

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
    return body.members.stream()
        .map(UsersListResponse.UserInfoData::toModel)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<UserInfo> getBy(UserInfo.UserId id) {
    for (val user : this.findAll()) {
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
