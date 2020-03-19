package project.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
public class CORSFilter implements Filter
{
    public void doFilter(ServletRequest req, ServletResponse res, @NotNull FilterChain fc) throws IOException, ServletException
    {
        HttpServletResponse rsp = (HttpServletResponse) res;
        rsp.setHeader("Access-Control-Allow-Origin", "*");
        rsp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        rsp.setHeader("Access-Control-Max-Age", "3600");
        rsp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        fc.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig)
    {
    }

    public void destroy()
    {
    }

    @Override
    public boolean isLoggable(LogRecord record)
    {
        return false;
    }

}