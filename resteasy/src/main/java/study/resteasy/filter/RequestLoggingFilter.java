package study.resteasy.filter;


import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ezhengx on 4/9/2018.
 */
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        logger.debug("---------------------------Receive request: URI={}, Method={}, Headers={}",
                          ctx.getUriInfo().getPath(), ctx.getMethod(), ctx.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("---------------------------Sending response: Status={}, Headers={}",
                              responseCtx.getStatus(), responseCtx.getHeaders());

        }
    }

}
