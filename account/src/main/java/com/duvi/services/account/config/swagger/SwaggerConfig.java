package com.duvi.services.account.config.swagger;

import com.duvi.services.account.config.security.OAuth2Config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Autowired
    private OAuth2Config oAuth2Config;

    private String getAuthorizationUri() {
        return oAuth2Config.findAuthServiceUris().get("authorizationUri");
    }
    private String getTokenUri() {
        return oAuth2Config.findAuthServiceUris().get("tokenUri");
    }
    private Components apiComponents() {
        Components components = new Components();
        components.addSecuritySchemes("microservices_oauth2", microservicesOauth2());
        return components;
    }
    private SecurityScheme microservicesOauth2() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.type(SecurityScheme.Type.OAUTH2);
        securityScheme.flows(new OAuthFlows().clientCredentials(new OAuthFlow().authorizationUrl(this.getAuthorizationUri()).tokenUrl(this.getTokenUri())));
        securityScheme.description("OAuth2 Client Credentials Flow for Microservices");
        return securityScheme;
    }
    private List<SecurityRequirement> securityRequirements() {

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("microservices_oauth2");
        return List.of(securityRequirement);
    }


    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().info(
                    new Info().title("Account Docs")
                            .license(new License().name("The MIT License (MIT)").url("https://opensource.org/license/mit"))
                            .version("0.1.0")
                            .contact(new Contact().name("Durian Sosa").email("dmsosa21@outlook.com").url("https://dmsosa.github.io/dmblog/"))
                            .description("Account API for Microservices Architecture")
                    )
                .components(apiComponents())
                .security(securityRequirements());
    }
}

