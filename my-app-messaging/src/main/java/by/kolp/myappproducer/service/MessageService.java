package by.kolp.myappproducer.service;

import by.kolp.myappproducer.market.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Page<String> findAllEmails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        return messageRepository.findAllEmails(pageable);
    }
}
