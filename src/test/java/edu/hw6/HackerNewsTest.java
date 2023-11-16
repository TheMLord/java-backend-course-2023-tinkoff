package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HackerNewsTest {
    @Test
    @DisplayName(
        "Test that a request to get a list of the most discussed articles on the site is successfully executed will returned a non-empty array of identifiers")
    void testThatARequestToGetAListOfTheMostDiscussedArticlesOnTheSiteIsSuccessfullyExecutedWillReturnedANonEmptyArrayOfIdentifiers() {
        var idArticleArray = HackerNews.hackerNewsTopStories();

        assertThat(idArticleArray).isNotEmpty();
    }

    @Test
    @DisplayName(
        "Test that the request to get the title of the article by the transmitted identifier is successfully executed and returned the correct title for an already known article")
    void testThatTheRequestToGetTheTitleOfTheArticleByTheTransmittedIdentifierIsSuccessfullyExecutedAndReturnedTheCorrectTitleForAnAlreadyKnownArticle() {
        long knownArticle = 37570037L;
        var exceptedNameTitle = "JDK 21 Release Notes";

        var actualNameTitle = HackerNews.news(knownArticle);

        assertThat(actualNameTitle).isEqualTo(exceptedNameTitle);
    }

    @Test
    @DisplayName(
        "Test that the request to get the title of the article by the passed id is successfully executed and returned an empty string as the title for a non-existent article")
    void testThatTheRequestToGetTheTitleOfTheArticleByThePassedIdIsSuccessfullyExecutedAndReturnedAnEmptyStringAsTheTitleForANonExistentArticle() {
        long notExistTitleId = 0L;

        var actualNameTitle = HackerNews.news(notExistTitleId);

        assertThat(actualNameTitle).isEmpty();

    }

}
