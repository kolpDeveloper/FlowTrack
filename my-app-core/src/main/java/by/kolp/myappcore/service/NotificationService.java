package by.kolp.myappcore.service;

import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

   // private final UserRepository userRepository;

    public void notifyUser(Long id, String message) {

            //todo notification
    }


}
