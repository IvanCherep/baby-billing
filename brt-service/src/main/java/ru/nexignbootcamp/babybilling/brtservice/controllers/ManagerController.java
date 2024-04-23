package ru.nexignbootcamp.babybilling.brtservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.ManagerWillDto;
import ru.nexignbootcamp.babybilling.brtservice.services.ClientManagementService;

@RestController
public class ManagerController {

    private ClientManagementService clientManagementService;

    public ManagerController(ClientManagementService clientManagementService) {
        this.clientManagementService = clientManagementService;
    }

    @PostMapping("/changeTariff")
    public ResponseEntity<ManagerWillDto> changeTariff(@RequestBody ManagerWillDto managerWillDto) {
        if (!managerWillDto.getLogin().equals("admin") || !managerWillDto.getPassword().equals("admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!clientManagementService.isClientExist(managerWillDto.getMsisdn())
                && !clientManagementService.isDataPlanExist(managerWillDto.getTariffId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientManagementService.changeTariff(managerWillDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
