package com.sonatype.assignment.integertowords.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .paths(p -> !p.equals("/error") && !p.equals("/"))
                .apis(RequestHandlerSelectors.any())
                .paths(multipartPaths())
                .build();
    }

    @RestController
    public class SwaggerRedirect {
        @RequestMapping(value = "/", method = RequestMethod.GET)
        public void method(HttpServletResponse httpServletResponse) {
            httpServletResponse.setHeader("Location", "/swagger-ui.html");
            httpServletResponse.setStatus(302);
        }
    }

    private Predicate<String> multipartPaths() {
        return regex("/api.*");
    }
}