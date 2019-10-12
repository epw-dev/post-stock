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

@Slf4j
@AllArgsConstructor
@Repository(UserInfoRepository.API_REPOS_KEY)
public class ApiUserInfoRepository implements UserInfoRepository {

  private SlackConfig config;

  private RestTemplate restTemplate;

  @Override
  public UserInfo getBy(UserInfo.UserId id) {
    val url =
        UriComponentsBuilder.fromUriString("https://slack.com/api/users.info")
            .queryParam("token", config.getToken())
            .queryParam("user", id.asText())
            .toUriString();
    val res = restTemplate.getForEntity(url, UsersInfoResponse.class);
    val body = res.getBody();
    log.info(body.toString());
    if (!body.ok) {
      throw new RuntimeException(
          String.format("url: %s, sc: %s, reason: %s", url, res.getStatusCode(), body.error));
    }
    val user = body.user;
    return new UserInfo(
        new UserInfo.UserId(user.id), user.profile.displayName, user.profile.imageOriginal);
  }

  @Override
  public void save(UserInfo user) {
    throw new UnsupportedOperationException();
  }

  @Data
  public static class UsersInfoResponse {
    private boolean ok;
    private String error;
    private UserInfoData user;

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
    }
  }
}
