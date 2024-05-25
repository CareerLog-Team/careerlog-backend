package careerlog.server.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("=========================================================");
            log.debug("===================       START       ===================");
            log.debug("Request time: {}, IP: {}", new java.util.Date(), request.getRemoteAddr());
            log.debug("Request {} {}", request.getMethod(), request.getRequestURI());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("===================        END        ===================");
            log.debug("=========================================================");
        }
    }
}