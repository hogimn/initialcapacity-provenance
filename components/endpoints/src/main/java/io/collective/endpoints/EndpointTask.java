package io.collective.endpoints;

/**
 * Represents an endpoint task containing an endpoint URL.
 */
public class EndpointTask {
    private String endpoint;

    /**
     * Constructs an EndpointTask with the specified endpoint URL.
     *
     * @param endpoint The endpoint URL.
     */
    public EndpointTask(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Retrieves the endpoint URL.
     *
     * @return The endpoint URL.
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Retrieves the value of the "Accept" header.
     *
     * @return The "Accept" header value.
     */
    public String getAccept() {
        return "application/xml";
    }
}
