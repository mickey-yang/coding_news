package com.myang.codingnews.model;

import jakarta.persistence.*;

import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "SOURCE")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SOURCE_ID")
    private Long sourceId;

    @Column(name = "SOURCE_NAME")
    private String sourceName;

    @Column(name = "SOURCE_URL")
    private URL sourceUrl;

    @OneToMany(mappedBy = "source")
    private Set<Article> articleSet;


    // Article list modifiers
    public Article addArticle(Article article) {
        articleSet.add(article);
        return article;
    }

    public Article removeArticle(Article article){
        articleSet.remove(article);
        return article;
    }

    // Getters and Setters

    public Long getSourceId() {
        return sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(URL sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Set<Article> getArticleSet() {
        return articleSet.stream()
                .map(Article::new)
                .collect(Collectors.toSet());
    }

}
