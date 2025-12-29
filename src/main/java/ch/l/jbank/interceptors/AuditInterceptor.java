package ch.l.jbank.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {

        logger.info("Audit - Metodo: {}, URI: {}, StatusCode: {}, IpAddress: {} ",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                request.getAttribute("x-user-ip")
        );
    }
}
