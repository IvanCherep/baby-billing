package ru.nexignbootcamp.babybilling.brtservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nexignbootcamp.babybilling.brtservice.domain.ManagerWill;
import ru.nexignbootcamp.babybilling.brtservice.services.ClientManagementService;

@RestController
public class ManagerController {

    private ClientManagementService clientManagementService;

    public ManagerController(ClientManagementService clientManagementService) {
        this.clientManagementService = clientManagementService;
    }

    @PostMapping("/changeTariff")
    public ResponseEntity<ManagerWill> changeTariff(@RequestBody ManagerWill managerWill) {
        if (!managerWill.getLogin().equals("admin") || !managerWill.getPassword().equals("admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!clientManagementService.isClientExist(managerWill.getMsisdn())
                && !clientManagementService.isDataPlanExist(managerWill.getTariffId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientManagementService.changeTariff(managerWill);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
