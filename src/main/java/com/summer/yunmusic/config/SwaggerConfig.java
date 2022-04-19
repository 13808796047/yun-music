package com.summer.yunmusic.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @Author Summer
 * @Since 2022/4/20 12:13 AM
 * @Version 1.0
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {


    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("前台API")
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("后台API")
                .apiInfo(adminApiInfo())
                .select()
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("前台-API文档")
                .description("本文档描述了前台微服务接口定义")
                .version("1.0")
                .contact(new Contact("Summer", "http://summer.com",
                        "511391805@qq.com"))
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台-API文档")
                .description("本文档描述了后台微服务接口定义")
                .version("1.0")
                .contact(new Contact("Summer", "http://summer.com",
                        "511391805@qq.com"))
                .build();
    }
}