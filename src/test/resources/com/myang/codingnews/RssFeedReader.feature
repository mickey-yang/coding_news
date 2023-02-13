Feature: Convert Rss feeds to Article objects

  Scenario Outline: Get feeds from Urls
    Given I have a <url>
    When I give the url to the RssReader
    Then I should get an article with the title <title>

    Examples:
      | url                                        | title                       |
      | "http://localhost:8080/insidejavaTest.xml" | "Java Modules in Real Life" |

  Scenario Outline: Feed url is incorrect
    Given I have a <url>
    When  I give a bad url to the RssReader
    Then I should get an exception with the message <message>

    Examples:
      | url                                                              | message       |
      | "https://javarevisited.blogspot.com/feeds/posts/default?alt=rss" | "Invalid XML" |
      | "https://spring.io/blog.atom"                                    | "is null"     |

