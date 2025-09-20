package by.kolp.myappweb.dto;

import lombok.*;

@Builder
@Data
public class UserRegistrationRequestDTO {

    private   String username;
    private   String email;
    private   String password;

}
