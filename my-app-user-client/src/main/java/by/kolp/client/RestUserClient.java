package by.kolp.client;

import by.kolp.myappdataapi.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Component
public class RestUserClient implements UserClient {

    private final WebClient webClient;

    public RestUserClient(@Value(value = "${user-service.base-url}") String baseURL) {
        this.webClient = WebClient
                .builder()
                .baseUrl(baseURL)
                .build();
    }

    @Override
    public Optional<UserRegistrationDTO> getUserById(Long id) {
        try {

            UserRegistrationDTO userDTO = webClient.get()
                    .uri("/api/users/{id}", id)
                    .retrieve()
                    .bodyToMono(UserRegistrationDTO.class)
                    .block();
            return Optional.ofNullable(userDTO);
        }catch (WebClientResponseException ex){
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserRegistrationDTO> findUserByUsername(String username) {
        try {

            UserRegistrationDTO userDTO = webClient.get()
                    .uri("???", username)
                    .retrieve()
                    .bodyToMono(UserRegistrationDTO.class)
                    .block();
            return Optional.ofNullable(userDTO);
        }catch (WebClientResponseException ex){
            return Optional.empty();
        }
    }
}

//todo rest client