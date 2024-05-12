package ru.nexignbootcamp.babybilling.cdrservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nexignbootcamp.babybilling.cdrservice.domain.AccountRefill;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.UserEntity;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.UserRepository;

import java.util.List;
import java.util.Random;

/**
 * Отправляет запросы на пополнение всех клиентских счетов на сумму от 1 до 1000 у.е.
 */
@Service
public class AccountRefillGenerationService {

    final private UserRepository userRepository;

    final private int numberOfClient = 10;

    final private RestTemplate restTemplate;

    final private Environment environment;

    private Random random;

    private String accountRefillUrl;


    public AccountRefillGenerationService(UserRepository userRepository, RestTemplate restTemplate, Environment environment) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.environment = environment;
        random = new Random();
        accountRefillUrl = environment.getProperty("BRT_SERVICE_ACCOUNT_REFILL_URL");
    }

    public void refill() {
        List<UserEntity> clients = userRepository.findAllByOrderByIdAsc();
        for (int i = 0; i < numberOfClient; i++) {
            AccountRefill accountRefill = AccountRefill.builder()
                    .msisdn(clients.get(i).getMsisdn())
                    .money(Float.valueOf(random.nextInt(1000) + 1))
                    .build();
            restTemplate.put(accountRefillUrl + String.valueOf(clients.get(i).getMsisdn()), accountRefill);
        }
    }
}
