package com.gp.beershop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build().apiInfo(apiEndPointsInfo());
//    }
//
//    private ApiInfo apiEndPointsInfo() {
//        return new ApiInfoBuilder().title("BeerShop Spring Boot REST API")
//            .description("Training project for GP Solutions")
//            .contact(new Contact("Igor Sitdikov", "https://github.com/igorsitdikov", "ihar.sitdzikau@yandex.ru"))
//            .license("Apache 2.0")
//            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//            .version("1.0.0")
//            .build();
//    }
    @Bean
    public OpenAPI beerShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BeerShop Spring Boot REST API")
                        .description("Training project for GP Solutions")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Igor Sitdikov")
                                .url("https://github.com/igorsitdikov")
                                .email("ihar.sitdzikau@yandex.ru"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
