package com.myang.codingnews.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Entity
@Table(name = "ARTICLE")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SOURCE_ID")
    @JsonManagedReference
    private Source source;

    @Column(name = "ARTICLE_TITLE")
    private String articleTitle;

    @Column(name = "AUTHOR_NAME")
    private String authorName;

    @Column(name = "ARTICLE_PUBLISHED_DATE")
    private LocalDate published_date;

    @Column(name = "ARTICLE_LINK")
    private String articleLink;

    @Column(name = "ARTICLE_DESCRIPTION", length = 2000)
    private String articleDescription;


    public Article() {
    }

    public Article(Long articleId, Source source, String articleTitle, String authorName, LocalDate published_date, String articleLink, String articleDescription) {
        this.articleId = articleId;
        this.source = source;
        this.articleTitle = articleTitle;
        this.authorName = authorName;
        this.published_date = published_date;
        this.articleLink = articleLink;
        this.articleDescription = articleDescription;
    }

    // Constructor for copying

    public Article(Article a) {
        this(a.articleId, a.source, a.articleTitle, a.authorName, a.published_date, a.articleLink, a.articleDescription);
    }

    // Getters and Setters

    public Long getArticleId() {
        return articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getPublished_date() {
        return published_date;
    }

    public void setPublished_date(LocalDate published_date) {
        this.published_date = published_date;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }
}
