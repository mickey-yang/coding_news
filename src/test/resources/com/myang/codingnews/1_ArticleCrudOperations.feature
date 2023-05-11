Feature: Article Repository crud operations

  Background:
    Given I have an Article repository
    And I have the following Source
      | sourceName  | sourceUrl    | sourceImgUrl     |
      | test Source | www.java.com | www.java.com/img |
    And I have the following Articles
      | articleTitle      | authorName | articlePublishedDate | articleLink    | articleDescription    |
      | My Article        | John Smith | 2020-01-01           | www.test.com/1 | Test article number 1 |
      | My Second Article | John Smith | 2020-01-02           | www.test.com/2 | Test article number 2 |

  Scenario: Create new articles and persist to Article Repository

    Given I persist the articles
    When I query all articles from the repository
    Then I should get articles with the following fields
      | articleTitle      | authorName | articlePublishedDate | articleLink    | articleDescription    | Source Name |
      | My Article        | John Smith | 2020-01-01           | www.test.com/1 | Test article number 1 | test Source |
      | My Second Article | John Smith | 2020-01-02           | www.test.com/2 | Test article number 2 | test Source |

  Scenario: Update articles

  Scenario: Delete articles

  Scenario: Find articles by ID

  Rule: Users should be able to query Articles

    Scenario: Search for articles by name or description
    Scenario: Find all articles by author name
    Scenario: Find all articles from a source
    Scenario: Filter articles by published date
    Scenario: Sort articles by name authorName and published date
