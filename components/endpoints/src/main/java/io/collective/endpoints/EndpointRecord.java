package io.collective.endpoints;

/**
 * Represents an endpoint record with an ID and a name.
 */
public class EndpointRecord {
    private int id;
    private String name;

    /**
     * Constructs an EndpointRecord with the specified ID and name.
     *
     * @param id   The ID of the endpoint record.
     * @param name The name of the endpoint record.
     */
    public EndpointRecord(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the ID of the endpoint record.
     *
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the endpoint record.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
}
