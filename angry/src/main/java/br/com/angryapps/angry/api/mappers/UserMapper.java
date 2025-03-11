package br.com.angryapps.angry.api.mappers;

import br.com.angryapps.angry.api.vm.UserVM;
import br.com.angryapps.angry.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserVM mapToUserVM(User user) {
        UserVM userVM = new UserVM();

        userVM.setId(user.getId());
        userVM.setName(user.getName());
        userVM.setEmail(user.getEmail());
        userVM.setAvatarUrl(user.getAvatarUrl());

        return userVM;
    }
}
