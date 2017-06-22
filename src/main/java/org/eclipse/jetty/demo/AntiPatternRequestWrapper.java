package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AntiPatternRequestWrapper extends HttpServletRequestWrapper
{
    public AntiPatternRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return super.getInputStream();
    }
}
