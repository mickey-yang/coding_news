package com.myang.codingnews.rss;

import com.myang.codingnews.model.Article;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * RssFeedReader --- parse an rss page via URL and get a list of the entries as Article objects
 * @author Mickey Yang
 */

public class RssFeedReader {

    static Logger logger = LoggerFactory.getLogger(RssFeedReader.class);

    /**
     * Public endpoint - Takes String url of feed and returns list of Article objects
     * @param url rss page
     * @return List of Article objects
     * @throws IOException if there are errors parsing the rss url page
     */

    public static List<Article> getArticlesListFromRssUrl(String url) throws IOException {
        return getArticlesListFromFeed(getFeedFromUrl(url));
    }


    /**
     * Takes feed at a URL and converts it into a ROME SyndFeed object
     * This is a private method and should not be called directly
     *
     * @param url rss page
     * @return SyndFeed object that has iterable entries, from ROME package
     * @throws MalformedURLException when URL cannot be parsed
     * @throws IOException           when there is any other error in the url such as time out or bad xml
     */

    private static SyndFeed getFeedFromUrl(String url) throws IOException {
        logger.info("Entering getFeedFromUrl with url: " + url);

        SyndFeed feed = null;
        InputStream inputStream = null;

        try {
            URL feedUrl = new URL(url);
            logger.debug("Created URL from string " + url);

            URLConnection openConnection = feedUrl.openConnection();
            logger.debug("Connection open on type: " + openConnection.getContentType());

            inputStream = openConnection.getInputStream();
            if ("gzip".equals(openConnection.getContentEncoding())) {
                inputStream = new GZIPInputStream(inputStream);
            }
            InputSource source = new InputSource(inputStream);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);
            logger.debug("Created feed from feedUrl");
        } catch (MalformedURLException e) {
            logger.error("Url \"" + url + "\" is not a valid url");
            throw e;
        } catch (FeedException | IOException e) {
            logger.error("Error creating xml from feedUrl: " + url);
            throw new IOException(e);
        }

        logger.info("Leaving getFeedFromUrl");
        return feed;
    }

    /**
     * Turns a Syndfeed into an arrayList of Articles
     * This private method is step 2 - it takes the above method's output and returns the final list of Article objects
     * This method uses the method below in a loop to parse each entry into an article
     *
     * @param feed Syndfeed object from ROME package
     * @return List of Article objects
     * @throws NullPointerException when the input feed is null
     */
    private static List<Article> getArticlesListFromFeed(SyndFeed feed) throws NullPointerException {
        logger.info("Entering to get List of Articles from SyndFeed");

        List<Article> articleList = new ArrayList<>();
        try {
            logger.debug("SyndFeed title: " + feed.getTitle());
            for (Object o : feed.getEntries()) {
                SyndEntry syndEntry = (SyndEntry) o;
                articleList.add(syndEntryToArticle(syndEntry));
            }

            logger.info("Leaving getArticleListFromFeed");

        } catch (NullPointerException e) {
            logger.error("Error: A section of the feed is null");
            throw e;
        }
        return articleList;
    }

    /**
     * Convert a SyndEntry into a new Article
     * This private method is used in the method above in a loop to parse each entry into an article
     *
     * @param syndEntry From one of the iterated entries of a SyndFeed
     * @return One single new Article
     */
    private static Article syndEntryToArticle(SyndEntry syndEntry) throws NullPointerException {
        logger.debug("Entering syndEntryToArticle with entry " + syndEntry.getTitle());

        Article article = new Article();
        article.setArticleTitle(syndEntry.getTitle());
        article.setAuthorName(syndEntry.getAuthor());
        article.setPublished_date(syndEntry.getPublishedDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        article.setArticleLink(syndEntry.getLink());
        // truncate article description
        String description = syndEntry.getDescription().getValue();
        description = description.length() > 2000 ? description.substring(0, 2000) : description;
//        if (description.contains("img src")) {
//            int index = description.indexOf(">");
//            String link = description.substring(0, index + 1);
//            description = description.replace(link, "");
//        }
        article.setArticleDescription(description);

        logger.debug("Leaving syndEntryToArticle");
        return article;
    }

}
