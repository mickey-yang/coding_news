Feature: Article Repository crud operations

  Background:
    Given I have the following Source
      | sourceName      | sourceUrl                     | sourceImgUrl               |
      | Existing Source | www.existingsourcerssfeed.com | www.existingsource.com/img |
    And I have the following Articles
      | articleTitle       | authorName | articlePublishedDate | articleLink               | articleDescription             | articleSource   |
      | Existing Article 1 | John Smith | 2020-01-01           | www.existingarticle.com/1 | This article already exists, 1 | Existing Source |
      | Existing Article 2 | John Smith | 2020-01-02           | www.existingarticle.com/2 | This article already exists, 2 | Existing Source |

  Scenario: Should persist articles and source by reading an rss url

    Given The url "https://www.newsourcerssfeed.com/rss" responds with the xml "insidejavaTest.xml"

    When SourceService fetches new articles from the url "https://www.newsourcerssfeed.com/rss"
    Then There are articles with the following fields
      | articleTitle      | authorName | articlePublishedDate | articleLink    | articleDescription    | Source Name |
      | My Article        | John Smith | 2020-01-01           | www.test.com/1 | Test article number 1 | test Source |
      | My Second Article | John Smith | 2020-01-02           | www.test.com/2 | Test article number 2 | test Source |
    And There is an articleSource with the following fields
      | sourceName | sourceUrl            |
      | insidejava | https://inside.java/ |


  Scenario: Update articles

  Scenario: Delete articles

  Scenario: Find articles by ID

  Rule: Users should be able to query Articles

    Scenario: Search for articles by name or description
    Scenario: Find all articles by author name
    Scenario: Find all articles from a source
    Scenario: Filter articles by published date
    Scenario: Sort articles by name authorName and published date
