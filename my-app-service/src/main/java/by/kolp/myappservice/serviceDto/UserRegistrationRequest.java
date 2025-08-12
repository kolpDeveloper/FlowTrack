package by.kolp.myappservice.serviceDto;

import lombok.*;

@Builder
@Data
public class UserRegistrationRequest {

    private   String username;
    private   String email;
    private   String password;

}
