package by.kolp.myappweb.util;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappservice.service.UsersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UsersDetailService userDetailService;

    @Autowired
    public UserValidator(UsersDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        try {
            userDetailService.loadUserByUsername(user.getUsername());
        }catch(UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Username not found");
    }

    
}
