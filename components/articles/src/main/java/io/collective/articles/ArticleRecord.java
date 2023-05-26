package io.collective.articles;

/**
 * Represents an article record with its ID, title, and availability status.
 */
public class ArticleRecord {
    private int id;
    private String title;
    private boolean available;

    /**
     * Constructs an ArticleRecord object with the specified ID, title, and availability status.
     *
     * @param id        The ID of the article.
     * @param title     The title of the article.
     * @param available The availability status of the article.
     */
    public ArticleRecord(int id, String title, boolean available) {
        this.id = id;
        this.title = title;
        this.available = available;
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

    /**
     * Returns the availability status of the article.
     *
     * @return The availability status of the article.
     */
    public boolean isAvailable() {
        return available;
    }
}
