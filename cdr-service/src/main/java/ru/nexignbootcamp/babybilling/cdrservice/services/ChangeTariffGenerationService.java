package ru.nexignbootcamp.babybilling.cdrservice.services;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nexignbootcamp.babybilling.cdrservice.domain.ManagerWill;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.UserEntity;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Сервис гегенарции действий менеджера по смене тарифоф пользователей.
 * Выбирает от 1 до 3 случайных номеров и менят им тарифы.
 */
@Service
public class ChangeTariffGenerationService {

    final private UserRepository userRepository;

    final private int numberOfClients = 10;

    final private RestTemplate restTemplate;

    final private Environment environment;

    private Random random;

    private String changeTariffUrl;

    public ChangeTariffGenerationService(UserRepository userRepository, RestTemplate restTemplate, Environment environment) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.environment = environment;
        random = new Random();
        changeTariffUrl = environment.getProperty("BRT_SERVICE_CHANGE_TARIFF_URL");
    }

    /**
     * Метод посылает запрос на изменение тарифа у случайного количества клиентов.
     * Количество клиентов - от 1 до 3.
     */
    public void changeTariff() {
        List<UserEntity> clients = userRepository.findAllByOrderByIdAsc();
        Set<Integer> clientsWithChangedTariffs = new HashSet<>();
        int numberOfClientsToChangeTariff = 1 + random.nextInt(3 - 1);

        for (int i = 0; i < numberOfClientsToChangeTariff; i++) {
            int client = random.nextInt(numberOfClients);
            while (clientsWithChangedTariffs.contains(client)) {
                client = random.nextInt(numberOfClients);
            }
            clientsWithChangedTariffs.add(client);
            ManagerWill managerWill = ManagerWill.builder()
                    .login("admin")
                    .password("admin")
                    .msisdn(clients.get(client).getMsisdn())
                    .tariffId(11 + random.nextInt(12 - 11))
                    .build();
            HttpEntity<ManagerWill> request = new HttpEntity<>(managerWill);
            restTemplate.postForLocation(changeTariffUrl, request);
        }

    }
}
