package com.myang.codingnews.services;

import com.myang.codingnews.model.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    public Article save(Article article) {
        return article;
    }

    public Article findByArticleName(String articleName) {
        return null;
    }
}
