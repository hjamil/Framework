package com.h.jamil.api.framework.config;

import com.h.jamil.api.framework.interceptor.FeignErrorDecoderInterceptor;
import com.h.jamil.api.framework.interceptor.FeignRequestInterceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public FeignErrorDecoderInterceptor getFeignErrorDecoder() throws Exception {
        return new FeignErrorDecoderInterceptor();
    }

    @Bean
    public FeignRequestInterceptor getFeignRequestInterceptor()
    {
        return new FeignRequestInterceptor();
    }

}
