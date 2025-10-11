package by.kolp.myappproducer.service;

import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;

    public Page<String> findAllEmails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        return userRepository.findAllEmails(pageable);
    }
}
