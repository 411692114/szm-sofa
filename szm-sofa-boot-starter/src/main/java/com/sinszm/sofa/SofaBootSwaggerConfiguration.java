package com.sinszm.sofa;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;

/**
 * 接口文档配置
 * @author sinszm
 */
@Configuration
@EnableOpenApi
@EnableConfigurationProperties(Swagger3Properties.class)
@AutoConfigureAfter(SofaBootStarterConfiguration.class)
public class SofaBootSwaggerConfiguration {

    @Resource
    private Swagger3Properties swagger3Properties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(swagger3Properties.isEnable())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swagger3Properties.getTitle())
                .description(swagger3Properties.getDescription())
                .contact(new Contact(
                        swagger3Properties.getAuthor(),
                        swagger3Properties.getSite(),
                        swagger3Properties.getEmail()
                ))
                .version(swagger3Properties.getVersion())
                .build();
    }

}

