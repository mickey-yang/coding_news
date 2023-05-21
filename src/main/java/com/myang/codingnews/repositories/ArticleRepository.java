package com.myang.codingnews.repositories;

import com.myang.codingnews.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, String> {


}
