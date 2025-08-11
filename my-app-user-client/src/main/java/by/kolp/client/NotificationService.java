package by.kolp.client;

import by.kolp.myappdataapi.dto.UserRegistrationDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private UserClient userClient;

    @Autowired
    public NotificationService(UserClient userClient) {
        this.userClient = userClient;
    }

    public void notifyUser(Long id, String message) {
        Optional<UserRegistrationDTO> user = userClient.getUserById(id);

        //userClient.getUserById(id).ifPresent(user -> {

            //todo notification
        //});
    }


}
