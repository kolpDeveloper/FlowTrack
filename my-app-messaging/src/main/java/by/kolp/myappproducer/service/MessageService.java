package by.kolp.myappproducer.service;

import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;

    public List<String> findAllEmails() {
        return userRepository.findAllEmails();
    }
}
