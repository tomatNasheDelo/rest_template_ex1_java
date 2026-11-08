package com.example.orchestrator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {
    private final RestTemplate restTemplate;
    private final String greetingServiceUrl;

    public MessageService(
            RestTemplate restTemplate,
            @Value("${clients.greeting-service.url}") String greetingServiceUrl) {
        this.restTemplate = restTemplate;
        this.greetingServiceUrl = greetingServiceUrl;
    }

    public MessageResponse getMessage(String name) {
        MessageResponse response = restTemplate.getForObject(
                greetingServiceUrl + "/api/greeting?name={name}",
                MessageResponse.class,
                name);
        if (response == null) {
            throw new IllegalStateException("greeting-service вернул пустой ответ");
        }
        return response;
    }
}
