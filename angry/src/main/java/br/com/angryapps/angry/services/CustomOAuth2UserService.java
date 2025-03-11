package br.com.angryapps.angry.services;

import br.com.angryapps.angry.db.UserRepository;
import br.com.angryapps.angry.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

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