package io.collective.articles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * The ArticleDataGateway class provides data access methods for managing articles.
 */
public class ArticleDataGateway {
    /**
     * List to store ArticleRecords
     */
    private final List<ArticleRecord> articles = new ArrayList<>();

    /**
     * Random object to generate sequence numbers
     */
    private final Random sequence = new Random();

    /**
     * Constructor without initial records.
     * Creates an empty ArticleDataGateway instance.
     */
    public ArticleDataGateway() {
        this(emptyList());
    }

    /**
     * Constructor with initial records.
     * Creates an ArticleDataGateway instance with the provided initial records.
     *
     * @param initialRecords The initial list of ArticleRecord objects.
     */
    public ArticleDataGateway(List<ArticleRecord> initialRecords) {
        // Add initial records to the articles list
        articles.addAll(initialRecords);
    }

    /**
     * Returns all articles.
     *
     * @return The list of all ArticleRecord objects.
     */
    public List<ArticleRecord> findAll() {
        return articles;
    }

    /**
     * Returns available articles (articles with isAvailable = true).
     *
     * @return The list of available ArticleRecord objects.
     */
    public List<ArticleRecord> findAvailable() {
        // Use stream and filter to get only available articles
        return articles.stream().filter(ArticleRecord::isAvailable).collect(Collectors.toList());
    }

    /**
     * Saves a new article with a title.
     * Generates a random sequence number, creates a new ArticleRecord, and adds it to the articles list.
     *
     * @param title The title of the new article.
     */
    public void save(String title) {
        // Generate a random sequence number, create a new ArticleRecord, and add it to the articles list
        articles.add(new ArticleRecord(sequence.nextInt(), title, true));
    }

    /**
     * Clears the articles list.
     * Removes all stored articles.
     */
    public void clear() {
        articles.clear();
    }
}
