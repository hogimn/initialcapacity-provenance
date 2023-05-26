package io.collective.rss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

/**
 * Represents the channel within the RSS feed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Item> item;

    /**
     * Retrieves the list of items within the channel.
     *
     * @return The list of items within the channel.
     */
    public List<Item> getItem() {
        return item;
    }
}
