package com.github.epw_dev.slack_post_stock.infrastructures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsDao extends JpaRepository<PostsDto, String> {
  @Override
  List<PostsDto> findAll();

  @Override
  <S extends PostsDto> S save(S entity);
}
