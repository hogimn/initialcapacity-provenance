package io.collective.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.articles.ArticleDataGateway;
import io.collective.articles.ArticleRecord;
import io.collective.articles.ArticlesController;
import io.collective.endpoints.EndpointDataGateway;
import io.collective.endpoints.EndpointTask;
import io.collective.endpoints.EndpointWorkFinder;
import io.collective.endpoints.EndpointWorker;
import io.collective.restsupport.BasicApp;
import io.collective.restsupport.NoopController;
import io.collective.restsupport.RestTemplate;
import io.collective.workflow.WorkScheduler;
import io.collective.workflow.Worker;
import org.eclipse.jetty.server.handler.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.TimeZone;


/**
 * The `App` class is a specific implementation of the `BasicApp` class.
 * It represents the main application class and includes initialization of the `articleDataGateway` object,
 * which serves as a gateway for accessing and managing article data.
 */
public class App extends BasicApp {

    /**
     * The `articleDataGateway` object serves as a gateway for accessing and managing article data.
     * It is initialized with a list of predefined `ArticleRecord` objects.
     */
    private static ArticleDataGateway articleDataGateway = new ArticleDataGateway(List.of(
            new ArticleRecord(10101, "Programming Languages InfoQ Trends Report - October 2019 4", true),
            new ArticleRecord(10106, "Ryan Kitchens on Learning from Incidents at Netflix, the Role of SRE, and Sociotechnical Systems", true)
    ));

    /**
     * Starts the application.
     * It starts the endpoint worker to process incoming tasks from the endpoint.
     */
    @Override
    public void start() {
        super.start();

        { // todo - start the endpoint worker
            // Create an EndpointWorkFinder with an EndpointDataGateway
            EndpointWorkFinder endpointWorkFinder = new EndpointWorkFinder(new EndpointDataGateway());
            // Create an EndpointWorker with a RestTemplate and the articleDataGateway
            EndpointWorker endpointWorker = new EndpointWorker(new RestTemplate(), articleDataGateway);
            // Create a list of workers with the endpointWorker
            List<Worker<EndpointTask>> workers = Collections.singletonList(endpointWorker);
            // Create a WorkScheduler with the endpointWorkFinder, workers, and a delay of 300 seconds
            WorkScheduler<EndpointTask> workScheduler = new WorkScheduler<>(endpointWorkFinder, workers, 300);
            // Start the work scheduler
            workScheduler.start();
        }
    }

    /**
     * Constructs an instance of the App class with the specified port.
     *
     * @param port The port number on which the server should listen.
     */
    public App(int port) {
        super(port);
    }

    /**
     * Retrieves the list of handlers to be registered with the server.
     *
     * @return The HandlerList containing the registered handlers.
     */
    @NotNull
    @Override
    protected HandlerList handlerList() {
        HandlerList list = new HandlerList();
        // Add an instance of ArticlesController to the handler list, passing an ObjectMapper and the articleDataGateway
        list.addHandler(new ArticlesController(new ObjectMapper(), articleDataGateway));
        // Add an instance of NoopController to the handler list
        list.addHandler(new NoopController());
        return list;
    }

    /**
     * Start of the program
     * @param args Command-line arguments passed to the program
     */
    public static void main(String[] args) {
        // Set the default time zone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // Get the port from the environment variable or use the default port 8881
        String port = System.getenv("PORT") != null ? System.getenv("PORT") : "8881";
        // Create an instance of App with the specified port
        App app = new App(Integer.parseInt(port));
        // Start the application
        app.start();
    }
}
