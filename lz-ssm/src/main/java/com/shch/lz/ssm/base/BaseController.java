package com.shch.lz.ssm.base;

import com.shch.lz.ssm.util.PropertiesFileUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Link at 21:22 on 3/28/18.
 */
public class BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("Unified Exception ExceptionHandler:", exception);
        // Todo ex ?
        request.setAttribute("ex", exception);
        if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            // Ajax Page Header:  X-Requested-With=XMLHttpRequest
            // Todo requestHeader ?
            request.setAttribute("requestHeader", "ajax");
        }
        if (exception instanceof UnauthorizedException) {
            return "/403.jsp";
        }
        if (exception instanceof InvalidSessionException) {
            return "/expire.jsp";
        }
        return "/error.jsp";
    }

    public static String jsp(String path) {
        return path.concat(".jsp");
    }

    public static String thymeleaf(String path) {
        String folder = PropertiesFileUtil.getInstance().get("app.name");
        return "/".concat(folder).concat(path).concat(".html");
    }
}
