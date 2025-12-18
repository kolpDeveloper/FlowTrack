package by.kolp.notificationservice.service;

import by.kolp.notificationservice.model.dto.UserEmailDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserClientService {

    private final RestTemplate restTemplate;
    private final static String user_url = "http://user-service:8081";

    public Page<String> findAllEmails(Pageable pageable) {

        String url = UriComponentsBuilder.fromHttpUrl(user_url + "/api/internal/user/emails")
                .path("/api/internal/user/emails")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUriString();
        log.info("fetching emails from user-service");

        ResponseEntity<RestPageResponse<UserEmailDTO>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RestPageResponse<UserEmailDTO>>() {
                });

        if (response.getBody() == null || response.getBody().getContent() == null || response.getBody().getContent().isEmpty()) {
            log.info("Emails list from user-service is empty");
            return Page.empty(pageable);
        }

        RestPageResponse<UserEmailDTO> body = response.getBody();
        List<String> emailList = body.content.stream()
                .map(UserEmailDTO::toString)
                .toList();

        return new PageImpl<>(emailList,
                pageable,
                body.totalElements);
    }

    @Getter
    @Setter
    public static class RestPageResponse<T extends Serializable> {

        private List<T> content;

        private int size;

        private int totalElements;

    }
}


