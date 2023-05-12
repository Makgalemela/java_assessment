package com.matome.ledger.account.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .contact(new Contact()
                        .url("http://localhost:19019")
                        .name("IMS development team"))
                .description("Accounting Ledger Demo Account")
                .summary("Demo Application for ledger Accounts")
                .title("Ledger Account")
                .version("1.0.0")
                .license(new License().name("Apache 2.0")
                        .url("https://springdoc.org"))
                .termsOfService(""));
    }
}
