package io.collective.restsupport

import org.eclipse.jetty.server.Request
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * A controller class that handles the "Noop" command.
 */
class NoopController : BasicHandler() {

    /**
     * Handles the "Noop" command by setting the response content type, writing "Noop!" to the response output stream,
     * setting the response status, and marking the request as handled.
     *
     * @param s The path of the request.
     * @param request The request object.
     * @param httpServletRequest The HTTP servlet request object.
     * @param httpServletResponse The HTTP servlet response object.
     */
    override fun handle(s: String, request: Request, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse) {
        // Set the response content type to "text/html; charset=UTF-8"
        httpServletResponse.contentType = "text/html; charset=UTF-8"

        // Write "Noop!" as a byte array to the response output stream
        httpServletResponse.outputStream.write("Noop!".toByteArray())

        // Set the response status to HttpServletResponse.SC_OK (200)
        httpServletResponse.status = HttpServletResponse.SC_OK

        // Set the request as handled
        request.isHandled = true
    }
}
