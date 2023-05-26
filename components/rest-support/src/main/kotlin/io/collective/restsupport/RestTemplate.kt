package io.collective.restsupport

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair

open class RestTemplate {
    /**
     * Perform a GET request to the specified endpoint with optional query parameters.
     *
     * @param endpoint The URL of the endpoint to send the GET request to.
     * @param accept The desired response format, specified using the "Accept" header.
     * @param queryParams Optional query parameters to be appended to the endpoint URL.
     * @return The response from the server as a String.
     */
    open fun get(endpoint: String, accept: String, vararg queryParams: BasicNameValuePair) = execute {
        // Create a URIBuilder with the endpoint URL
        val builder = URIBuilder(endpoint)
        // Add query parameters to the builder
        queryParams.forEach { pair -> builder.addParameter(pair.name, pair.value) }
        // Create an HttpGet request with the built URI
        HttpGet(builder.build()).apply {
            // Add the "Accept" header to specify the desired response format
            addHeader("Accept", accept)
        }
    }

    /**
     * Perform a GET request to the specified endpoint with optional query parameters.
     *
     * @param endpoint The URL of the endpoint to send the GET request to.
     * @param accept The desired response format, specified using the "Accept" header.
     * @param queryParams Optional query parameters to be appended to the endpoint URL.
     * @return The response from the server as a String.
     */
    fun post(endpoint: String, accept: String, data: String) = execute {
        // Create an HttpPost request with the endpoint URL
        HttpPost(endpoint).apply {
            // Add headers for the request, including "Accept" and "Content-type"
            addHeader("Accept", accept)
            addHeader("Content-type", "application/json")
            // Set the request entity to the provided data
            entity = StringEntity(data)
        }
    }

    // Execute an HTTP request using the provided block to build the request
    open fun execute(block: () -> HttpUriRequest): String {
        // Create an HttpClients instance and execute the request, returning the response as a String
        return HttpClients.createDefault().execute(block(), BasicResponseHandler())
    }
}
