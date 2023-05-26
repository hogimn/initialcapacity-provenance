package io.collective.endpoints;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.collective.articles.ArticleDataGateway;
import io.collective.restsupport.RestTemplate;
import io.collective.rss.RSS;
import io.collective.workflow.Worker;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Implementation of the Worker interface for executing endpoint tasks.
 */
public class EndpointWorker implements Worker<EndpointTask> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate template;
    private final ArticleDataGateway gateway;

    /**
     * Constructor that takes a RestTemplate and an ArticleDataGateway as parameters.
     *
     * @param template The RestTemplate used to perform HTTP requests.
     * @param gateway  The ArticleDataGateway used to save article information.
     */
    public EndpointWorker(RestTemplate template, ArticleDataGateway gateway) {
        this.template = template;
        this.gateway = gateway;
    }

    /**
     * Returns the name of the worker.
     *
     * @return The name of the worker.
     */
    @NotNull
    @Override
    public String getName() {
        return "ready";
    }

    /**
     * Executes the endpoint task.
     *
     * @param task The endpoint task to execute.
     */
    @Override
    public void execute(EndpointTask task) throws IOException {
        // Perform a GET request to the endpoint specified in the task using the RestTemplate
        String response = template.get(task.getEndpoint(), task.getAccept());
        // Clear the article gateway before processing the response
        gateway.clear();

        { // todo - map rss results to an article infos collection and save articles infos to the article gateway
            // Map RSS results to an article infos collection and save article infos to the article gateway
            RSS rss = new XmlMapper().readValue(response, RSS.class);
            rss.getChannel()
                    .getItem()
                    .forEach(item -> gateway.save(item.getTitle()));
        }
    }
}
