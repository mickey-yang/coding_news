package com.myang.codingnews;

import com.myang.codingnews.model.Article;
import com.myang.codingnews.rss.RssFeedReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    String url = null;
    List<Article> articleList = new ArrayList<>();


    // Scenario 1: Get Article from Feed Url

    @Given("I have a {string}")
    public void i_have_a(String url) {
        this.url = url;
    }

    @When("I give the url to the RssReader")
    public void i_give_the_url_to_the_rss_reader() throws IOException {
        articleList = RssFeedReader.getArticlesListFromRssUrl(url);
    }

    @Then("I should get an article with the title {string}")
    public void i_should_get_an_article_with_the_title(String title) {
        Article article = articleList.get(0);
        assertEquals(article.getArticleTitle(), title);
    }

    // Scenario 2: Exceptions when parsing url

    @When("I give a bad url to the RssReader")
    public void i_give_a_bad_url_to_the_rss_reader() {
    }

    @Then("I should get an exception with the message {string}")
    public void i_should_get_an_exception_with_the(String exceptionMessage) {
        try {
            articleList = RssFeedReader.getArticlesListFromRssUrl(url);
        } catch (Exception e) {
            String actualMesasge = e.getMessage();
            assertTrue(actualMesasge.contains(exceptionMessage));
        }
    }


}
