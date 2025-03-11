package br.com.angryapps.angry.services;

import br.com.angryapps.angry.db.UserRepository;
import br.com.angryapps.angry.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient =
                new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseConverter));

        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        tokenResponseClient.setRestOperations(restTemplate);

        return tokenResponseClient;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Extract attributes based on provider
        switch (registrationId.toLowerCase()) {
            case "github":
                String providerId = attributes.get("id").toString();
                String email = (String) attributes.get("email");

                User user = userRepository.findByGithubId(providerId)
                                          .or(() -> userRepository.findByEmail(email))
                                          .orElse(null);

                if (user == null) {
                    user = new User();
                    user.setName((String) attributes.get("name"));
                    user.setEmail(email);
                    user.setAvatarUrl((String) attributes.get("avatar_url"));
                } else {
                    user.setGithubId(providerId);
                }

                userRepository.save(user);
                break;
            default:
                throw new OAuth2AuthenticationException("Unsupported OAuth provider: " + registrationId);
        }

        return oAuth2User;
    }
}