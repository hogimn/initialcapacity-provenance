package io.collective.restsupport

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.slf4j.LoggerFactory

/**
 * Abstract class representing a basic application with an HTTP server.
 *
 * @param port The port on which the server should listen.
 */
abstract class BasicApp(port: Int) {
    /**
     * Logger to log messages
     */
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Server object to handle HTTP requests
     */
    private val server: Server

    init {
        // Get the HandlerList from the abstract method
        val list = this.handlerList()
        // Create a new Server instance with the specified port
        server = Server(port)
        // Set the handler for the server to the HandlerList
        server.handler = list
        // Set stopAtShutdown to true, ensuring graceful shutdown of the server
        server.stopAtShutdown = true
        // Add a shutdown hook to stop the server when the JVM shuts down
        Runtime.getRuntime().addShutdownHook(Thread {
            try {
                // Check if the server is running, and stop it if it is
                if (server.isRunning) {
                    server.stop()
                }
                // Log a message indicating successful shutdown
                logger.info("App shutdown.")
            } catch (e: Exception) {
                // Log an error message if an exception occurs during shutdown
                logger.info("Error shutting down app.", e)
            }
        })
    }

    /**
     * Abstract method to be implemented by subclasses to provide the HandlerList.
     *
     * @return The HandlerList for the application's server.
     */
    protected abstract fun handlerList(): HandlerList

    /**
     * Method to start the server.
     */
    open fun start() {
        // Log a message indicating that the app has started
        logger.info("App started.")
        // Start the server
        server.start()
    }

    /**
     * Method to stop the server.
     */
    fun stop() {
        // Log a message indicating that the app has stopped
        logger.info("App stopped.")
        // Stop the server
        server.stop()
    }
}
