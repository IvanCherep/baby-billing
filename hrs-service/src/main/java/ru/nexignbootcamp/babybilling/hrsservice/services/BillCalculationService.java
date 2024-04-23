package ru.nexignbootcamp.babybilling.hrsservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientBill;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientCallData;

import java.util.Calendar;
import java.util.TimeZone;

@Service
@Log
public class BillCalculationService {

    public ClientBill calculateBill(ClientCallData clientCallData) {
        Float moneyBill = (float) 0;
        Integer remainingMinutes = clientCallData.getClientRemainingMinutes();

        int monthsHavePassedSinceLastPayment = calculateHowManyMonthsHavePassed(
                clientCallData.getLastPaymentTimestamp(),
                clientCallData.getEndTime()
        );

        if (monthsHavePassedSinceLastPayment > 0) {
            remainingMinutes = clientCallData.getTariffMinutesPlan();
            moneyBill += monthsHavePassedSinceLastPayment * clientCallData.getTariffMonthlyFee();
        }

        Integer callDurationInMinutes = calculateCallDurationInMinutes(clientCallData.getStartTime(), clientCallData.getEndTime());
        if (callDurationInMinutes <= remainingMinutes) {
            remainingMinutes -= callDurationInMinutes;
        } else {
            moneyBill += (callDurationInMinutes - remainingMinutes) * clientCallData.getPricePerMinute();
            remainingMinutes = 0;
        }

        return ClientBill.builder()
                .msisdn(clientCallData.getMsisdn())
                .remainingMinutes(remainingMinutes)
                .moneyBill(moneyBill)
                .lastPaymentTimestamp(clientCallData.getEndTime())
                .build();
    }

    private int calculateHowManyMonthsHavePassed(Long lastPaymentTimestamp, Long endOfCallTimestamp) {
        Calendar lastPaymentCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        lastPaymentCalendar.setTimeInMillis(lastPaymentTimestamp * 1000L);
        int lastPaymentMonthNumber = lastPaymentCalendar.get(Calendar.MONTH) + 1;

        Calendar endTimeCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        endTimeCalendar.setTimeInMillis(endOfCallTimestamp * 1000L);
        int endOfCallMonthNumber = endTimeCalendar.get(Calendar.MONTH) + 1;

        int yearsHavePassed = endTimeCalendar.get(Calendar.YEAR) - lastPaymentCalendar.get(Calendar.YEAR);
        if (yearsHavePassed == 0) {
            return (endOfCallMonthNumber - lastPaymentMonthNumber);
        } else if (yearsHavePassed == 1) {
            return (12 - lastPaymentMonthNumber) + endOfCallMonthNumber;
        } else {
            return (12 - lastPaymentMonthNumber) + endOfCallMonthNumber + (yearsHavePassed - 1) * 12;
        }
    }

    private Integer calculateCallDurationInMinutes(Long startTime, Long endTime) {
        return Integer.valueOf((int) (Math.ceil((endTime - startTime) / 60.0)));
    }

}