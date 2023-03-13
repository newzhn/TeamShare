package com.zhn.teamsharebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 自定义Swagger的配置
 */
@Configuration
@EnableSwagger2WebMvc
/**
 * 指定swagger接口文档只在开发和测试环境下生效
 */
@Profile({"dev", "test"})
public class SwaggerConfig {
    @Bean(value = "defaultApi2")
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("1.0版本")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhn.teamsharebackend.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TeamShare项目接口文档")
                .contact(new Contact("zhn", "www.zhnblog.icu", "zhanhaonan1@163.com"))
                .description("这是用Swagger动态生成的接口文档")
                .termsOfServiceUrl("NO terms of service")
                .version("1.0")
                .build();
    }
}
