package io.collective.articles;

/**
 * Represents information about an article with its ID and title.
 */
public class ArticleInfo {
    private int id;
    private String title;

    /**
     * Default constructor (private) to prevent direct instantiation.
     */
    private ArticleInfo() {
    }

    /**
     * Constructs an ArticleInfo object with the specified ID and title.
     *
     * @param id    The ID of the article.
     * @param title The title of the article.
     */
    public ArticleInfo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Returns the ID of the article.
     *
     * @return The ID of the article.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the article.
     *
     * @return The title of the article.
     */
    public String getTitle() {
        return title;
    }
}
