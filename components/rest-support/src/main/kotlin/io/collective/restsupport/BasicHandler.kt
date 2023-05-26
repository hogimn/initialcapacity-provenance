package io.collective.restsupport

import com.fasterxml.jackson.databind.ObjectMapper
import org.eclipse.jetty.http.HttpMethod
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler
import java.io.IOException
import javax.servlet.http.HttpServletResponse

/**
 * An abstract class representing a basic handler for HTTP requests.
 *
 * @property mapper The object mapper used for JSON serialization and deserialization.
 */
abstract class BasicHandler(private val mapper: ObjectMapper = ObjectMapper()) : AbstractHandler() {

    /**
     * Handles HTTP POST requests for the specified URI and supported media types.
     *
     * @param uri The URI to handle the request for.
     * @param supportedMediaTypes The list of supported media types.
     * @param request The request object.
     * @param httpServletResponse The HTTP servlet response object.
     * @param block The block of code to execute for the request handling.
     */
    fun post(uri: String, supportedMediaTypes: List<String>, request: Request, httpServletResponse: HttpServletResponse, block: Runnable) {
        // Check if the request method is POST
        if (request.method == HttpMethod.POST.toString()) {
            // Check if the requested URI matches the provided URI
            if (uri == request.requestURI) {
                // Get the accepted media type from the request header
                val acceptedMediaType = request.getHeader("Accept")

                // Iterate over the supported media types
                for (supportedMediaType in supportedMediaTypes) {
                    // Check if the accepted media type contains the supported media type
                    if (acceptedMediaType.contains(supportedMediaType)) {
                        // Set the response content type to the supported media type
                        httpServletResponse.contentType = supportedMediaType
                        try {
                            // Set the response status to 201 (Created)
                            httpServletResponse.status = HttpServletResponse.SC_CREATED
                            // Execute the provided block of code
                            block.run()
                        } catch (e: IOException) {
                            // Set the response status to 500 (Internal Server Error) if an exception occurs
                            httpServletResponse.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                        }
                        // Set the request as handled
                        request.isHandled = true
                    }
                }
            }
        }
    }

    /**
     * Handles HTTP GET requests for the specified URI and supported media types.
     *
     * @param uri The URI to handle the request for.
     * @param supportedMediaTypes The list of supported media types.
     * @param request The request object.
     * @param httpServletResponse The HTTP servlet response object.
     * @param block The block of code to execute for the request handling.
     */
    fun get(uri: String, supportedMediaTypes: List<String>, request: Request, httpServletResponse: HttpServletResponse, block: Runnable) {
        // Check if the request method is GET
        if (request.method == HttpMethod.GET.toString()) {
            // Check if the requested URI matches the provided URI
            if (uri == request.requestURI) {
                // Get the accepted media type from the request header
                val acceptedMediaType = request.getHeader("Accept")

                // Iterate over the supported media types
                for (supportedMediaType in supportedMediaTypes) {
                    // Check if the accepted media type contains the supported media type
                    if (acceptedMediaType.contains(supportedMediaType)) {
                        // Set the response content type to the supported media type
                        httpServletResponse.contentType = supportedMediaType
                        try {
                            // Execute the provided block of code
                            block.run()
                            // Set the response status to 200 (OK)
                            httpServletResponse.status = HttpServletResponse.SC_OK
                        } catch (e: IOException) {
                            // Set the response status to 500 (Internal Server Error) if an exception occurs
                            httpServletResponse.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                        }
                        // Set the request as handled
                        request.isHandled = true
                    }
                }
            }
        }
    }

    /**
     * Writes the given subject as a JSON response body to the provided HttpServletResponse.
     *
     * @param servletResponse The HttpServletResponse to write the JSON response to.
     * @param subject The object to be serialized as JSON and written to the response.
     */
    protected fun writeJsonBody(servletResponse: HttpServletResponse, subject: Any?) {
        // Use the ObjectMapper to serialize the subject as JSON and write it to the response output stream
        mapper.writeValue(servletResponse.outputStream, subject);
    }
}