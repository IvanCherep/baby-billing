package ru.nexignbootcamp.babybilling.brtservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nexignbootcamp.babybilling.brtservice.domain.AccountRefill;
import ru.nexignbootcamp.babybilling.brtservice.services.ClientManagementService;

/**
 * Контроллер принимающий запросы от клиентов.
 * На данный момент принимает только запрос на пополнение счета.
 */
@RestController
public class ClientController {

    private ClientManagementService clientManagementService;

    public ClientController(ClientManagementService clientManagementService) {
        this.clientManagementService = clientManagementService;
    }

    @PutMapping("/pay/{msisdn}")
    public ResponseEntity<AccountRefill> pay(
            @PathVariable Long msisdn,
            @RequestBody AccountRefill accountRefill
    ) {
        if (!clientManagementService.isClientExist(msisdn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                clientManagementService.refill(msisdn, accountRefill),
                HttpStatus.OK
        );
    }

}
