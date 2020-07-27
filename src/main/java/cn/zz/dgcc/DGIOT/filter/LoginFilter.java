package cn.zz.dgcc.DGIOT.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/7/15 15:04
 * ClassExplain : 拦截器
 * ->
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.err.println("init--------------filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (!uri.equals("/index.html")) {
            String forceLogOut = (String) request.getSession().getAttribute("forceLogOut");
            if(null!=forceLogOut&&!"".equals(forceLogOut)){
                if(forceLogOut.equals("yes")){
//                    System.err.println("单点登陆触发");
                    request.getSession().invalidate();
                    response.sendRedirect("../index.html");
                }
            }

        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
