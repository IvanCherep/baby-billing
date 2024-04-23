package ru.nexignbootcamp.babybilling.hrsservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientBill;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientCallData;
import ru.nexignbootcamp.babybilling.hrsservice.services.BillCalculationService;

@RestController
public class CustomersCallDataController {

    private BillCalculationService billCalculationService;

    public CustomersCallDataController(BillCalculationService billCalculationService) {
        this.billCalculationService = billCalculationService;
    }

    @PostMapping(path = "/calculate")
    public ClientBill getBill(@RequestBody ClientCallData clientCallData) {
        return billCalculationService.calculateBill(clientCallData);
    }
}
