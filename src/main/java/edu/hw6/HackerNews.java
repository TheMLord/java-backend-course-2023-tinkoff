package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Formatter;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static java.net.http.HttpClient.newHttpClient;

/**
 * Class for working with the HackerNews website API
 */
public final class HackerNews {
    private static final Logger HACKER_NEWS_LOGGER = Logger.getLogger(HackerNews.class.getName());

    private static final URI GET_ALL_NEWS_REQUEST = URI.create("https://hacker-news.firebaseio.com/v0/topstories.json");
    private static final String GET_NEWS_TITLE_REQUEST_TEMPLATE = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private static final Formatter REQUEST_FORMATTER = new Formatter();
    private static final int OK_CODE = 200;

    private static final Pattern NEWS_TITLE_PATTERN = Pattern.compile("\"title\":\"([^\"]*)\"");
    private static final int INDEX_START_SUBSTRING_TITLE = 9;
    private static final int INDEX_START_NEWS_ARRAY_SUBSTRING = 1;
    private static final int MINUS_LAST_INDEX_SUBSTRING = 1;
    private static final String JSON_NEWS_SEPARATOR = ",";

    private static final String EMPTY_STRING = "";

    /**
     * Class constructor.
     */
    private HackerNews() {

    }

    /**
     * Method that allows you to get an array with the ids of the most discussed articles on the Hacker News website.
     *
     * @return if the request is successful, it will return an array of ids of the most discussed articles.
     *     But if an exception occurs, it will return an empty array
     */
    public static long[] hackerNewsTopStories() {
        try {
            var getHackerNewsResponse = newHttpClient().send(
                HttpRequest.newBuilder(GET_ALL_NEWS_REQUEST).GET().build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );
            if (getHackerNewsResponse.statusCode() == OK_CODE) {
                return prepareNewsArrayLong(getHackerNewsResponse.body());
            }
            return returnEmptyLongArray();
        } catch (IOException | InterruptedException e) {
            HACKER_NEWS_LOGGER.info(e.getMessage());
            return returnEmptyLongArray();
        }
    }

    /**
     * Method allows you to get the title of the article by the transmitted article ID.
     *
     * @param newsId article ID.
     * @return If successful, it will return the title of the article as a string.
     *     If the ID turns out to be invalid or the article does not have a title, it will return an empty string
     */
    public static String news(Long newsId) {
        try {
            URI requestGetTitleNewsURI =
                URI.create(String.valueOf(REQUEST_FORMATTER.format(GET_NEWS_TITLE_REQUEST_TEMPLATE, newsId)));
            var getHackerNewsResponse = newHttpClient().send(
                HttpRequest.newBuilder(requestGetTitleNewsURI).GET().build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );
            if (getHackerNewsResponse.statusCode() == OK_CODE) {
                var match = NEWS_TITLE_PATTERN.matcher(getHackerNewsResponse.body());
                if (match.find()) {
                    var titlePart = match.group();
                    return titlePart.substring(
                        INDEX_START_SUBSTRING_TITLE,
                        titlePart.length() - MINUS_LAST_INDEX_SUBSTRING
                    );
                }
            }
            return EMPTY_STRING;
        } catch (IOException | InterruptedException e) {
            HACKER_NEWS_LOGGER.info(e.getMessage());
            return EMPTY_STRING;
        }
    }

    /**
     * Method of processing the response from the server to get an array of article IDs.
     *
     * @param responseString response from the web server on request for a list of the most discussed
     *                       articles on the site.
     * @return generated array of article ids.
     */
    private static long[] prepareNewsArrayLong(String responseString) {
        return Arrays.stream(responseString.substring(
                INDEX_START_NEWS_ARRAY_SUBSTRING,
                responseString.length() - MINUS_LAST_INDEX_SUBSTRING
            ).split(JSON_NEWS_SEPARATOR))
            .mapToLong(Long::parseLong)
            .toArray();
    }

    /**
     * Method for returning an empty array of identifiers.
     *
     * @return empty long array.
     */
    private static long[] returnEmptyLongArray() {
        return new long[] {};
    }
}
