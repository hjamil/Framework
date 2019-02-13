package com.h.jamil.api.framework.interceptor;

import com.h.jamil.api.framework.utility.ELKLogger;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.ArrayList;

public class FeignErrorDecoderInterceptor implements ErrorDecoder {

    private static final ELKLogger log = new ELKLogger(FeignErrorDecoderInterceptor.class);
    private ErrorDecoder delegate = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().entrySet().stream()
                .forEach(entry -> responseHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue())));

        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        String statusText = statusCode.getReasonPhrase();

        byte[] responseBody;
        try {
            if(response.body() != null) {
                responseBody = IOUtils.toByteArray(response.body().asInputStream());
                log.error("Error response payload: " + new String(responseBody, "UTF-8"));
            }
            else
            {
                responseBody = new byte[0];
                log.error("Error response payload: *** empty response ***");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process response body.", e);
        }

        if (response.status() >= 400 && response.status() <= 499) {
            //log.error("Found error within 4xx range");
            return new HttpClientErrorException(statusCode, statusText, responseHeaders, responseBody, null);
        }

        if (response.status() >= 500 && response.status() <= 599) {
            //log.error("Found error within 5xx range");
            return new HttpServerErrorException(statusCode, statusText, responseHeaders, responseBody, null);
        }
        return delegate.decode(methodKey, response);
    }
}