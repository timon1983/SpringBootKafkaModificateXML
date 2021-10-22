package com.example.KafkaModificateXML.configuration;

import springfox.documentation.service.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private static final String SEARCH = "KafkaModificateXML";
@Bean
public Docket myApp() {
    return new Docket(DocumentationType.SWAGGER_2).groupName(SEARCH)
            .apiInfo(apiInfo()).tags(new Tag(SEARCH, "KafkaModificateXMLAPI"));
}

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(SEARCH).build();
    }

}
