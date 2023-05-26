package io.collective.endpoints;

import java.util.Collections;
import java.util.List;

import java.util.Collections;
import java.util.List;

/**
 * Gateway class for retrieving ready EndpointRecords based on a provided name.
 */
public class EndpointDataGateway {
    /**
     * Finds ready EndpointRecords based on the provided name.
     *
     * @param name The name to match for readiness.
     * @return A list of ready EndpointRecords.
     */
    public List<EndpointRecord> findReady(String name) {
        // Check if the name is not equal to "ready"
        if (!name.equals("ready")) {
            // If not ready, return an empty list
            return Collections.emptyList();
        }

        // If the name is "ready", return a singleton list with a predefined EndpointRecord
        return Collections.singletonList(
                new EndpointRecord(10101, "https://feed.infoq.com/") // always ready to collect data
        );
    }
}
