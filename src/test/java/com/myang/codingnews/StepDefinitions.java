package com.myang.codingnews;

import com.myang.codingnews.model.Article;
import com.myang.codingnews.model.Source;
import com.myang.codingnews.rss.RssFeedReader;
import com.myang.codingnews.services.ArticleService;
import com.myang.codingnews.services.SourceService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    String url = null;
    List<Article> articleList = new ArrayList<>();

    DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ISO_DATE;

    @Autowired
    ArticleService articleService;
    @Autowired
    SourceService sourceService;

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
            System.out.println(actualMesasge);
            System.out.println(exceptionMessage);
            assertTrue(actualMesasge.contains(exceptionMessage));
        }
    }

    // Article Querying

    @Given("I have the following Source")
    public void iHaveTheFollowingSource(DataTable dataTable) throws MalformedURLException {

        // convert datatable to Maps. Each key:value pair is column header and column entry
        List<Map<String, String>> dataTableMaps = dataTable.asMaps();

        for (Map<String, String> dataTableMap : dataTableMaps) {
            Source source = new Source();
            source.setSourceName(dataTableMap.get("sourceName"));
            source.setSourceUrl(new URL(dataTableMap.get("sourceUrl")));
            sourceService.save(source);
        }

        for (Map<String, String> dataTableMap : dataTableMaps) {
            assertNotNull(sourceService.findBySourceName(dataTableMap.get("sourceName")));
        }

    }

    @And("I have the following Articles")
    public void iHaveTheFollowingArticles(DataTable dataTable) {

        List<Map<String, String>> dataTableMaps = dataTable.asMaps();

        for (Map<String, String> dataTableMap : dataTableMaps) {
            Article article = new Article();
            article.setArticleTitle(dataTableMap.get("articleTitle"));
            article.setAuthorName(dataTableMap.get("authorName"));
            article.setArticlePublishedDate(LocalDate.parse(dataTableMap.get("articlePublishedDate"),ISO_DATE_FORMAT));
            article.setArticleLink(dataTableMap.get("articleLink"));
            article.setArticleDescription(dataTableMap.get("articleDescription"));
            articleService.save(article);
        }

        for (Map<String, String> dataTableMap : dataTableMaps) {
            assertNotNull(articleService.findByArticleName(dataTableMap.get("articleName")));
        }

    }

    @Given("The url {string} responds with the xml {string}")
    public void theUrlRespondsWithTheXml(String url, String xmlFile) {
    }

    @When("SourceService fetches new articles from the url {string}")
    public void articleserviceFetchesNewArticlesFrom(String url) {
    }

    @Then("There are articles with the following fields")
    public void thereAreArticlesWithTheFollowingFields() {
    }

    @And("There is an articleSource with the following fields")
    public void thereIsAnArticleSourceWithTheFollowingFields() {
    }
}
