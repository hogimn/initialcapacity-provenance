package io.collective.rss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Represents the RSS feed.
 */
@JacksonXmlRootElement(localName = "rss")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RSS {
    @JacksonXmlProperty(isAttribute = true)
    private Channel channel;

    /**
     * Retrieves the channel of the RSS feed.
     *
     * @return The channel of the RSS feed.
     */
    public Channel getChannel() {
        return channel;
    }
}
