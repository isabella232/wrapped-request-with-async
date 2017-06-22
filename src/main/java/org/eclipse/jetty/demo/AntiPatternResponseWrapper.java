package org.eclipse.jetty.demo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class AntiPatternResponseWrapper extends HttpServletResponseWrapper
{
    private static final Logger LOG = Logger.getLogger(AntiPatternResponseWrapper.class.getName());
    private PrintWriter w;

    public AntiPatternResponseWrapper(HttpServletResponse response)
    {
        super(response);
    }

    /**
     * This is a common anti-pattern and very often leads to garbled output.
     * <p>
     *     An attempt is made to allow access to getWriter() and getOutputStream() interchangeably
     *     from different parts of the codebase.
     *
     *     This is a hack, and ignores the fact that many libraries wrap the getWriter() and getOutputStream()
     *     returned with various buffered versions on a frequent basis.  Allowing writes from either
     *     place would mean bypassing the buffers in place on one implementation or the other.
     *
     *     This is reason that the servlet spec does not allow you to use both at the same time.
     * </p>
     * @return PrintWriter based on {@link HttpServletResponse#getOutputStream()}
     * @throws IOException
     */
    @Override
    public PrintWriter getWriter() throws IOException
    {
        if (w == null)
        {
            LOG.info("getWriter() - new");
            String encoding = super.getCharacterEncoding();
            w = new PrintWriter(new OutputStreamWriter(getOutputStream(), encoding));
        }
        else
        {
            LOG.info("getWriter() - existing");
        }
        return w;
    }
}
