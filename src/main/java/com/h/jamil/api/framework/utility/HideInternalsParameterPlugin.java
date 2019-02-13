package com.h.jamil.api.framework.utility;

import org.springframework.core.annotation.Order;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.function.BiPredicate;

@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER+500)
public class HideInternalsParameterPlugin implements ParameterBuilderPlugin {


    private BiPredicate<String,String> selector;

    public HideInternalsParameterPlugin(BiPredicate<String,String> selector) {
        this.selector = selector;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        // support all media types
        return true;
    }

    @Override
    public void apply(ParameterContext parameterContext) {
        if ( parameterContext.resolvedMethodParameter().defaultName().isPresent()&&
                selector.test(parameterContext.getDocumentationContext().getGroupName(),
                parameterContext.resolvedMethodParameter().defaultName().get()))
            parameterContext.parameterBuilder().hidden(true);
    }
}
