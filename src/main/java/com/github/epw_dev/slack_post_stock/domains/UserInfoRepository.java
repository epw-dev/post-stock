package com.github.epw_dev.slack_post_stock.domains;

public interface UserInfoRepository {
  UserInfo getBy(UserInfo.UserId id);

  void save(UserInfo user);

  String DB_REPOS_KEY = "dbUserInfoRepository";
  String API_REPOS_KEY = "apiUserInfoRepository";
}
