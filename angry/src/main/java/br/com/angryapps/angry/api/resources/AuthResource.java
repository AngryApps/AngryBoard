package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.BadRequestResponseException;
import br.com.angryapps.angry.api.exceptions.InternalServerErrorResponseException;
import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.UserMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.UserVM;
import br.com.angryapps.angry.db.UserRepository;
import br.com.angryapps.angry.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class AuthResource {

    private UserMapper userMapper;
    private UserRepository userRepository;

    @Autowired
    public AuthResource(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @GetMapping("current")
    public SingleDataResponse<UserVM> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken o) {
            User user = switch (o.getAuthorizedClientRegistrationId()) {
                case "github" -> userRepository.findByGithubId("" + principal.getAttribute("id"))
                                               .orElseThrow(() -> new NotFoundResponseException("Column not found"));
                default ->
                        throw new BadRequestResponseException("Provider " + o.getAuthorizedClientRegistrationId() + " not supported");
            };

            return ApiResponses.single(userMapper.mapToUserVM(user));
        }

        throw new InternalServerErrorResponseException("Exception trying to return the logged user");
    }
}
