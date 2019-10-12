package com.github.epw_dev.slack_post_stock.infrastructures;

import com.github.epw_dev.slack_post_stock.domains.PostMessage;
import com.github.epw_dev.slack_post_stock.domains.PostMessageRepository;
import com.github.epw_dev.slack_post_stock.domains.UserInfo;
import com.github.epw_dev.slack_post_stock.domains.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Repository
public class DBPostMessageRepository implements PostMessageRepository {

  private PostsDao dao;

  private UserInfoRepository userRepos;

  @Override
  public List<PostMessage> findAll() {
    return dao.findAll().stream()
        .map(this::dto2model)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public PostMessage save(PostMessage.Partial src) {
    val dto = new PostsDto();
    dto.setText(src.getText());
    dto.setUserId(src.getUser());
    dto.setCreatedAt(src.getCreatedAt());
    return dto2model(dao.save(dto));
  }

  PostMessage dto2model(PostsDto dto) {
    val user = userRepos.getBy(new UserInfo.UserId(dto.getUserId()));
    return new PostMessage(dto.getId(), dto.getText(), user, dto.getCreatedAt());
  }
}
