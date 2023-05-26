package io.collective.rss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Represents an item in XML format.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @JacksonXmlProperty(isAttribute = true, localName = "title")
    private String title;

    /**
     * Gets the title of the item.
     *
     * @return The title of the item.
     */
    public String getTitle() {
        return title;
    }

    @JacksonXmlProperty(isAttribute = true, namespace = "dc", localName = "creator")
    private String creator;

    /**
     * Gets the creator of the item.
     *
     * @return The creator of the item.
     */
    public String getCreator() {
        return creator;
    }

    @JacksonXmlProperty(isAttribute = true, localName = "author")
    private String author;

    /**
     * Gets the author of the item.
     *
     * @return The author of the item.
     */
    public String getAuthor() {
        return creator;
    }
}
