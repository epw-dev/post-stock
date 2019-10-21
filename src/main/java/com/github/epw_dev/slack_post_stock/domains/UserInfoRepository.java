package com.github.epw_dev.slack_post_stock.domains;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository {

  List<UserInfo> findAll();

  Optional<UserInfo> getBy(UserInfo.UserId id);
}
