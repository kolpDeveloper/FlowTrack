package by.kolp.notificationservice.service;

import by.kolp.notificationservice.model.dto.UserEmailDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserClientService {

    private final RestTemplate restTemplate;
    private final static String user_url = "http://user-service:8082";

    @CircuitBreaker(name = "backendA")
    public Page<String> findAllEmails(Pageable pageable) {

        String url = UriComponentsBuilder.fromHttpUrl(user_url + "/api/internal/user/emails")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUriString();
        log.info("fetching emails from user-service");

        ResponseEntity<UserEmailDTO<String>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UserEmailDTO<String>>() {
                });

        if (response.getBody() == null
            || response.getBody().getContent() == null
            || response.getBody().getContent().isEmpty()) {
            log.info("Emails list from user-service is empty");
            return Page.empty(pageable);
        }

        UserEmailDTO<String> body = response.getBody();
        List<String> emailList = response.getBody().getContent();

        return new PageImpl<>(emailList,
                pageable,
                body.getTotalElements());
    }
}


