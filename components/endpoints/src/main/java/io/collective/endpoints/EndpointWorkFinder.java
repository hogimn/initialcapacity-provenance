package io.collective.endpoints;

import io.collective.workflow.WorkFinder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the WorkFinder interface for finding and marking endpoint tasks.
 */
public class EndpointWorkFinder implements WorkFinder<EndpointTask> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private EndpointDataGateway gateway;

    /**
     * Constructor that takes an EndpointDataGateway as a parameter.
     *
     * @param gateway The EndpointDataGateway used to retrieve endpoint records.
     */
    public EndpointWorkFinder(EndpointDataGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Implementation of the findRequested() method from the WorkFinder interface.
     * Retrieves a list of requested endpoint tasks.
     *
     * @param name The name of the requested tasks.
     * @return The list of requested endpoint tasks.
     */
    @NotNull
    @Override
    public List<EndpointTask> findRequested(@NotNull String name) {
        // Call the findReady() method on the gateway to retrieve a list of ready EndpointRecords
        return gateway.findReady(name).stream()
                // Map each EndpointRecord to an EndpointTask with the name extracted from the record
                .map(record -> new EndpointTask(record.getName())).collect(Collectors.toList());
    }

    /**
     * Implementation of the markCompleted() method from the WorkFinder interface.
     * Marks the specified endpoint task as completed.
     *
     * @param info The endpoint task to mark as completed.
     */
    @Override
    public void markCompleted(EndpointTask info) {
        // Log a message indicating that the work is marked complete
        logger.info("marking work complete.");
    }
}
