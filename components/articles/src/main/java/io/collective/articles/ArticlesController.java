package io.collective.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.restsupport.BasicHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling article-related endpoints.
 */
public class ArticlesController extends BasicHandler {
    private final ArticleDataGateway gateway;

    /**
     * Constructs an ArticlesController with the specified ObjectMapper and ArticleDataGateway.
     *
     * @param mapper  The ObjectMapper for serialization.
     * @param gateway The ArticleDataGateway for retrieving article records.
     */
    public ArticlesController(ObjectMapper mapper, ArticleDataGateway gateway) {
        super(mapper);
        this.gateway = gateway;
    }

    /**
     * Handles the incoming HTTP request for the specified endpoint.
     *
     * @param target          The target of the request.
     * @param request         The Request object representing the incoming request.
     * @param servletRequest  The HttpServletRequest object representing the servlet request.
     * @param servletResponse The HttpServletResponse object representing the servlet response.
     */
    @Override
    public void handle(String target, Request request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // Handle the "/articles" endpoint
        get("/articles", List.of("application/json", "text/html"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *all* articles, map record to infos, and send back a collection of article infos
                // Create a list to store ArticleInfo objects
                List<ArticleInfo> articleInfos = new ArrayList<>();
                // Iterate over all article records obtained from the gateway
                gateway.findAll().forEach(record ->
                        // Map each record to an ArticleInfo object and add it to the articleInfos list
                        articleInfos.add(new ArticleInfo(record.getId(), record.getTitle())));
                // Write the articleInfos list as a JSON response body
                writeJsonBody(servletResponse, articleInfos);
            }
        });

        // Handle the "/available" endpoint
        get("/available", List.of("application/json"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *available* articles, map records to infos, and send back a collection of article infos
                // Create a list to store ArticleInfo objects
                List<ArticleInfo> articleInfos = new ArrayList<>();
                // Iterate over available article records obtained from the gateway
                gateway.findAvailable().forEach(record ->
                        // Map each record to an ArticleInfo object and add it to the articleInfos list
                        articleInfos.add(new ArticleInfo(record.getId(), record.getTitle())));
                // Write the articleInfos list as a JSON response body
                writeJsonBody(servletResponse, articleInfos);
            }
        });
    }
}
