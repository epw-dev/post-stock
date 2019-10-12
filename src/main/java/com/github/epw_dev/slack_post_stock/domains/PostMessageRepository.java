package com.github.epw_dev.slack_post_stock.domains;

import java.util.List;

public interface PostMessageRepository {

  List<PostMessage> findAll();

  PostMessage save(PostMessage.Partial message);
}
