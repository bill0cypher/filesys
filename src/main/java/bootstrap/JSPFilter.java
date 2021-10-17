package bootstrap;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebFilter(servletNames = {"UserServlet"}, urlPatterns = "/user/*")
public class JSPFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().length() > request.getContextPath().length()) {
            String[] parts = ((HttpServletRequest) req).getRequestURI().split("/");
            req.getRequestDispatcher(parts[parts.length - 1]+".jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
