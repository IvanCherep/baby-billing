package ru.nexignbootcamp.babybilling.hrsservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientBill;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientCallData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BillCalculationServiceTest {

    @Autowired
    private BillCalculationService billCalculationService;

    @Test
    void testThatBillForClientWithPerMinuteTariffIsCalculatedCorrectly() {
        ClientCallData clientCallData = ClientCallData.builder()
                .msisdn(79219999999L)
                .startTime(1706781600L) // Thu Feb 01 2024 13:00:00 GMT+03
                .endTime(1706784662L) // Thu Feb 01 2024 13:51:02 GMT+03
                .pricePerMinute((float) 1.5)
                .clientRemainingMinutes(0)
                .lastPaymentTimestamp(1704056400L) // Mon Jan 01 2024 00:00:00 GMT+03
                .tariffMonthlyFee((float) 0)
                .tariffMinutesPlan(0)
                .build();

        ClientBill clientBill = billCalculationService.calculateBill(clientCallData);
        ClientBill clientReferenceBill = ClientBill.builder()
                .msisdn(clientCallData.getMsisdn())
                .remainingMinutes(0)
                .moneyBill((float) 78) // 52 минуты * 1.5 у.е./минута = 78 у.е.
                .lastPaymentTimestamp(clientCallData.getEndTime())
                .build();

        assertThat(clientBill).isEqualTo(clientReferenceBill);

    }

    @Test
    void testThatBillForClientWithMonthlyTariffIsCalculatedCorrectly() {
        ClientCallData clientCallData = ClientCallData.builder()
                .msisdn(79219999999L)
                .startTime(1706781600L) // Thu Feb 01 2024 13:00:00 GMT+03
                .endTime(1706784662L) // Thu Feb 01 2024 13:51:02 GMT+03
                .pricePerMinute((float) 1.5)
                .clientRemainingMinutes(50)
                .lastPaymentTimestamp(1704056400L) // Mon Jan 01 2024 00:00:00 GMT+03
                .tariffMonthlyFee((float) 100)
                .tariffMinutesPlan(50)
                .build();

        ClientBill clientBill = billCalculationService.calculateBill(clientCallData);

        ClientBill clientReferenceBill = ClientBill.builder()
                .msisdn(clientCallData.getMsisdn())
                .remainingMinutes(0)
                // 50 минут из 52 покрывает тариф. За оставшиеся две минуты нужно заплатить 3 у.е.
                // Также, так как последняя оплата была месяц назад, нужно списать 100 у.е. в оплату тарифа
                .moneyBill((float) 103)
                .lastPaymentTimestamp(clientCallData.getEndTime())
                .build();

        assertThat(clientBill).isEqualTo(clientReferenceBill);

    }
}