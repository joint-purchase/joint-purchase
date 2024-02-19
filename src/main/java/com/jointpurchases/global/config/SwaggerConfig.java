package com.jointpurchases.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String API_TITLE = "joint-purchase";
    public static final String API_DESCRIPTION = "공동구매 사이트입니다.";
    public static final String API_VERSION = "v2.0";


    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION)
                );
    }
}