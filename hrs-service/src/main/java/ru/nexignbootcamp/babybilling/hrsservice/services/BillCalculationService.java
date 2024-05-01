package ru.nexignbootcamp.babybilling.hrsservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientBill;
import ru.nexignbootcamp.babybilling.hrsservice.domain.ClientCallData;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        // Округление счета до 1 знака после запятой. Округление ведется в менью сторону.
        Float moneyBillRoundedToOneDecimalPlace = new BigDecimal(moneyBill).setScale(1, RoundingMode.DOWN).floatValue();

        return ClientBill.builder()
                .msisdn(clientCallData.getMsisdn())
                .remainingMinutes(remainingMinutes)
                .moneyBill(moneyBillRoundedToOneDecimalPlace)
                .lastPaymentTimestamp(clientCallData.getEndTime())
                .build();
    }

    // Подсчет количества месяцев прошедших с момента последней оплаты.
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

    // Вычисление длительности разговора в минутах.
    // На выходе получается целое число минут.
    // Каждая дополнительная секунда разговора засчитывается как минута.
    private Integer calculateCallDurationInMinutes(Long startTime, Long endTime) {
        return Integer.valueOf((int) (Math.ceil((endTime - startTime) / 60.0)));
    }

}