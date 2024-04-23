package ru.nexignbootcamp.babybilling.brtservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.AccountRefillDto;
import ru.nexignbootcamp.babybilling.brtservice.services.ClientManagementService;

@RestController
public class ClientController {

    private ClientManagementService clientManagementService;

    public ClientController(ClientManagementService clientManagementService) {
        this.clientManagementService = clientManagementService;
    }

    @PutMapping("/pay/{msisdn}")
    public ResponseEntity<AccountRefillDto> pay(
            @PathVariable Long msisdn,
            @RequestBody AccountRefillDto accountRefillDto
    ) {
        if (!clientManagementService.isClientExist(msisdn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                clientManagementService.refill(msisdn, accountRefillDto),
                HttpStatus.OK
        );
    }

}
