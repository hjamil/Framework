package com.h.jamil.api.framework.interceptor;


import com.h.jamil.api.framework.utility.ELKLogger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

public class FeignRequestInterceptor implements RequestInterceptor {
    private static final ELKLogger log = new ELKLogger(FeignRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {

        //URL
        log.debug("Calling " + requestTemplate.method() + " " + requestTemplate.url() +requestTemplate.queryLine());

        //Header
        Map<String, Collection<String>> headerList = requestTemplate.headers();
        if (headerList != null) {
            StringBuilder strHeader = new StringBuilder();
            strHeader.append("Request Header");
            String headerName;
            String headerValue;
            for(Map.Entry<String, Collection<String> > entry  :headerList.entrySet()) {
                headerName = entry.getKey();
                Collection<String>  tmp = entry.getValue();
                String[] headerValues =tmp.toArray(new String[0]);
                if(headerValues != null && headerValues.length > 0) {
                    strHeader.append("\n" + headerName + ": " + headerValues[0]);
                }
            }
            log.debug(strHeader.toString());
        }

        //Body
        try {
            byte[] requestPayload = requestTemplate.body();
            if(requestPayload != null) {
                String requestBody = new String(requestPayload, "UTF-8");
                log.debug("Request payload: " + requestBody);
            }
        } catch (UnsupportedEncodingException e) {
            //Do nothing
        }
    }
}
