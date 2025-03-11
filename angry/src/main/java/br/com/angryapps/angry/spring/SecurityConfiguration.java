package br.com.angryapps.angry.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/**"));
//    }

    @Autowired
    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/error/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                    //.loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error=true")
                    .tokenEndpoint(token -> token
                            .accessTokenResponseClient(accessTokenResponseClient)
                    )
            )
            .logout(l -> l.logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}
