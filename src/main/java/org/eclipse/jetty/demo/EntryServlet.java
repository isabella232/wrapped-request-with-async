package org.eclipse.jetty.demo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class EntryServlet extends HttpServlet
{
    private static final Logger LOG = Logger.getLogger(EntryServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.info(String.format("doGet() : dispatcherType=%s : requestURI=%s : request.class=%s : response.class=%s",
                req.getDispatcherType(), req.getRequestURI(),
                req.getClass().getName(),
                resp.getClass().getName()));
        if (req.getDispatcherType() == DispatcherType.REQUEST)
        {
            AsyncContext async = req.startAsync(req,resp);
            async.dispatch();
        }
        else
        {
            resp.setContentType("text/plain");
            resp.getWriter().println("Check your logs too");
            resp.getWriter().printf("method = %s%n", req.getMethod());
            resp.getWriter().printf("URI = %s%n", req.getRequestURI());
            resp.getWriter().printf("dispatcherType = %s%n", req.getDispatcherType());
            resp.getWriter().printf("request.class = (%s)%n", req.getClass().getName());
            resp.getWriter().printf("response.class = (%s)%n", resp.getClass().getName());

            /* Since we have a wrapped Response with a custom PrintWriter we have to
             * flush the content in its buffer, otherwise we'll get no output to the
             * http client if it does not write enough to flow out of the PrintWriter
             * buffer.
             *
             * Not using a wrapped Response/PrintWriter means you are using Servlet
             * behavior with regards to exiting the dispatch to your Servlet.
             * Which will flush any unsent content found in its internal buffers.
             */
            resp.getWriter().flush();
        }
    }
}
