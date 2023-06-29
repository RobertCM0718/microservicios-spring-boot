package com.quetzalcode.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * pre tiempo transcurrido filter
 */
@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        LOG.info("Entrando a postFilter");

        Long tiempoInicio =(Long) request.getAttribute("tiempoInicio");
        Long tiempoFinal = System.currentTimeMillis();
        Long tiempoTranscurrido = tiempoFinal-tiempoInicio;
        LOG.info(String.format("Tiempo transcurrido en segundos: %s seg.",tiempoTranscurrido.doubleValue()/1000.00));
        LOG.info(String.format("Tiempo transcurrido en milisegundos: %s ms.",tiempoTranscurrido));
        return null;
    }
}
