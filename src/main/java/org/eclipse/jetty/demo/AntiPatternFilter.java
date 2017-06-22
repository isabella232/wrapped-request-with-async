package org.eclipse.jetty.demo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", dispatcherTypes = DispatcherType.REQUEST, asyncSupported = true)
public class AntiPatternFilter implements Filter
{
    private static final Logger LOG = Logger.getLogger(AntiPatternFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse))
        {
            // http path
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            LOG.info(String.format("doFilter() : dispatcherType=%s : uri=%s", httpRequest.getDispatcherType(), httpRequest.getRequestURI()));

            httpRequest = new AntiPatternRequestWrapper(httpRequest);
            httpResponse = new AntiPatternResponseWrapper(httpResponse);
            chain.doFilter(httpRequest, httpResponse);
        }
        else
        {
            // internal path
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy()
    {
    }
}
