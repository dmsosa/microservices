package com.duvi.authservice.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Configuration
public class AuthConfig  {

    //default auth-server settings
    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    //In production, JdbcRegisteredClientRepository is used
    @Bean
    @Profile("prod")
    JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE oauth2_registered_client (\n" +
                "    id varchar(100) NOT NULL,\n" +
                "    client_id varchar(100) NOT NULL,\n" +
                "    client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,\n" +
                "    client_secret varchar(200) DEFAULT NULL,\n" +
                "    client_secret_expires_at timestamp DEFAULT NULL,\n" +
                "    client_name varchar(200) NOT NULL,\n" +
                "    client_authentication_methods varchar(1000) NOT NULL,\n" +
                "    authorization_grant_types varchar(1000) NOT NULL,\n" +
                "    redirect_uris varchar(1000) DEFAULT NULL,\n" +
                "    post_logout_redirect_uris varchar(1000) DEFAULT NULL,\n" +
                "    scopes varchar(1000) NOT NULL,\n" +
                "    client_settings varchar(2000) NOT NULL,\n" +
                "    token_settings varchar(2000) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ");");
        jdbcTemplate.execute("CREATE TABLE oauth2_authorization_consent (\n" +
                "    registered_client_id varchar(100) NOT NULL,\n" +
                "    principal_name varchar(200) NOT NULL,\n" +
                "    authorities varchar(1000) NOT NULL,\n" +
                "    PRIMARY KEY (registered_client_id, principal_name)\n" +
                ");");
        jdbcTemplate.execute("CREATE TABLE oauth2_authorization (\n" +
                "    id varchar(100) NOT NULL,\n" +
                "    registered_client_id varchar(100) NOT NULL,\n" +
                "    principal_name varchar(200) NOT NULL,\n" +
                "    authorization_grant_type varchar(100) NOT NULL,\n" +
                "    authorized_scopes varchar(1000) DEFAULT NULL,\n" +
                "    attributes TEXT DEFAULT NULL,\n" +
                "    state varchar(500) DEFAULT NULL,\n" +
                "    authorization_code_value TEXT DEFAULT NULL,\n" +
                "    authorization_code_issued_at timestamp DEFAULT NULL,\n" +
                "    authorization_code_expires_at timestamp DEFAULT NULL,\n" +
                "    authorization_code_metadata TEXT DEFAULT NULL,\n" +
                "    access_token_value TEXT DEFAULT NULL,\n" +
                "    access_token_issued_at timestamp DEFAULT NULL,\n" +
                "    access_token_expires_at timestamp DEFAULT NULL,\n" +
                "    access_token_metadata TEXT DEFAULT NULL,\n" +
                "    access_token_type varchar(100) DEFAULT NULL,\n" +
                "    access_token_scopes varchar(1000) DEFAULT NULL,\n" +
                "    oidc_id_token_value TEXT DEFAULT NULL,\n" +
                "    oidc_id_token_issued_at timestamp DEFAULT NULL,\n" +
                "    oidc_id_token_expires_at timestamp DEFAULT NULL,\n" +
                "    oidc_id_token_metadata TEXT DEFAULT NULL,\n" +
                "    refresh_token_value TEXT DEFAULT NULL,\n" +
                "    refresh_token_issued_at timestamp DEFAULT NULL,\n" +
                "    refresh_token_expires_at timestamp DEFAULT NULL,\n" +
                "    refresh_token_metadata TEXT DEFAULT NULL,\n" +
                "    user_code_value TEXT DEFAULT NULL,\n" +
                "    user_code_issued_at timestamp DEFAULT NULL,\n" +
                "    user_code_expires_at timestamp DEFAULT NULL,\n" +
                "    user_code_metadata TEXT DEFAULT NULL,\n" +
                "    device_code_value TEXT DEFAULT NULL,\n" +
                "    device_code_issued_at timestamp DEFAULT NULL,\n" +
                "    device_code_expires_at timestamp DEFAULT NULL,\n" +
                "    device_code_metadata TEXT DEFAULT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ");");
        return jdbcTemplate;
    }

    //jdbcClientRepository bean
    @Bean
    @Profile("prod")
    public RegisteredClientRepository jdbcClientRepository(JdbcTemplate jdbcTemplate) {

        RegisteredClient accountClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("accountClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$CqSgsr5xnY5HX7kbGukM7OqrAIaKnqov4Oj67DG19VclarOk9hRDG")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientName("account")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .build();
        RegisteredClient statsClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("statsClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$BcTIelAtug5ySTx5AaebwupC6GHUo7Sl5jthVY2IwWM4DGSUzWMEq")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientName("stats")
                .build();
        RegisteredClient notiClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("notiClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$YiQhiAkSKt.n6ponbf.JA.ai/wX0q9ouhYp.Ojr.E0f.yG.EEIx5i")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.EMAIL)
                .clientName("noti")
                .build();
        RegisteredClient browserClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gatewayClient")
                .clientSecret("$2a$10$PnrjC9wfF5WHipaczbRif..XO0Ydc5cjECFRrBJWWU5byrYPUNptK")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8061/login/oauth2/code/browserClient")
                .clientName("gatewayClient")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .build();
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        registeredClientRepository.save(accountClient);
        registeredClientRepository.save(statsClient);
        registeredClientRepository.save(notiClient);
        registeredClientRepository.save(browserClient);

        return registeredClientRepository;
    }


    //in memory ClientRepository for dev profile
    @Bean
    @Profile("dev")
    RegisteredClientRepository inMemoryRegisteredClientRepository() {

        RegisteredClient accountClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("accountClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$CqSgsr5xnY5HX7kbGukM7OqrAIaKnqov4Oj67DG19VclarOk9hRDG")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientName("accountService")
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(60)).build())
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .build();
        RegisteredClient statsClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("statsClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$BcTIelAtug5ySTx5AaebwupC6GHUo7Sl5jthVY2IwWM4DGSUzWMEq")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientName("statsService")
                .build();
        RegisteredClient notiClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("notiClient")
                //password encoded with BCrypt via Spring CLI, I do not include {bcrypt} since BCryptPasswordEncoder does not strip those characters.
                .clientSecret("$2a$10$YiQhiAkSKt.n6ponbf.JA.ai/wX0q9ouhYp.Ojr.E0f.yG.EEIx5i")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.EMAIL)
                .clientName("notiService")
                .build();
        RegisteredClient gatewayClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gatewayClient")
                .clientSecret("$2a$10$T4a55gT79PMGRPWdFxntSeapLSKjssYNXxi7/qXPXfteoaujy/sqy")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8061/login/oauth2/code/gatewayClient")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .build();
        return new InMemoryRegisteredClientRepository(List.of(accountClient, statsClient, notiClient, gatewayClient));
    }
    //token source
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


}
