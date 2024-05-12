package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.UserEntity;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.CDRRepository;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.UserRepository;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * GeneratorService - генерирует действия пользователей за 1 год. Сюда относится:
 * - Генерация записей о звонках абонентов и их передача в FileGenerator.
 * - Ежемесячный вызов генераторов смены тарифа и пополненичй счета клиентов.
 */
@Service
@Log
public class GeneratorService {

    final private Long leftBoundary = 1704056400L;

    final private Long rightBoundary = 1735678799L;

    final private Long numberOfUsers = 20L;

    final private Long maxCallTime = 7200L;

    final private AccountRefillGenerationService accountRefillGenerationService;

    final private ChangeTariffGenerationService changeTariffGenerationService;

    private Long currentStartTime;

    private CDRRepository cdrRepository;

    private UserRepository userRepository;

    private Random random;

    private FileGenerator fileGenerator;

    private int currentMonthNumber;


    public GeneratorService(CDRRepository cdrRepository,
                            UserRepository userRepository,
                            FileGenerator fileGenerator,
                            AccountRefillGenerationService accountRefillGenerationService,
                            ChangeTariffGenerationService changeTariffGenerationService) {
        this.cdrRepository = cdrRepository;
        this.userRepository = userRepository;
        this.fileGenerator = fileGenerator;
        this.accountRefillGenerationService = accountRefillGenerationService;
        this.changeTariffGenerationService = changeTariffGenerationService;
        random = new Random();
        currentStartTime = leftBoundary;
        currentMonthNumber = 1;
    }

    public Long[] generateUserAndTargetMsisdn() {
        Long[] userAndTargetMsisdn = new Long[2];
        Long clientId = random.nextLong(numberOfUsers) + 1;
        Long targetId = random.nextLong(numberOfUsers) + 1;
        while(clientId.equals(targetId)) {
            targetId = random.nextLong(numberOfUsers) + 1;
        }
        UserEntity client = userRepository.findById(clientId).get();
        UserEntity target = userRepository.findById(targetId).get();
        userAndTargetMsisdn[0] = client.getMsisdn();
        userAndTargetMsisdn[1] = target.getMsisdn();
        return userAndTargetMsisdn;
    }

    public void run() {
        Long endTime = currentStartTime + 1;
        while (endTime <= rightBoundary) {
            Long callDuration = random.nextLong(maxCallTime) + 1;
            endTime = currentStartTime + callDuration;
            Long[] userAndTargetMsisdn = generateUserAndTargetMsisdn();

            CDREntity cdr1 = CDREntity.builder()
                    .callType("01")
                    .clientMsisdn(userAndTargetMsisdn[0])
                    .targetMsisdn(userAndTargetMsisdn[1])
                    .startTime(currentStartTime)
                    .endTime(endTime)
                    .build();

            CDREntity cdr2 = CDREntity.builder()
                    .callType("02")
                    .clientMsisdn(userAndTargetMsisdn[1])
                    .targetMsisdn(userAndTargetMsisdn[0])
                    .startTime(currentStartTime)
                    .endTime(endTime)
                    .build();

            cdrRepository.save(cdr1);
            cdrRepository.save(cdr2);

            currentStartTime += callDuration / 2;
            if (random.nextBoolean()) {
                currentStartTime += random.nextLong(86400) + 1;
            }

            CDREntity[] cdrPair = new CDREntity[2];
            cdrPair[0] = cdr1;
            cdrPair[1] = cdr2;

            fileGenerator.writeCdrToFile(cdrPair);

            if (isItNewMonth(currentStartTime)) {
                accountRefillGenerationService.refill();
                changeTariffGenerationService.changeTariff();

            }

        }
    }

    private boolean isItNewMonth(Long currentTime) {
        Calendar currentTimeCalendar = Calendar.getInstance(TimeZone.getTimeZone(("Europe/Moscow")));
        currentTimeCalendar.setTimeInMillis(currentTime * 1000L);
        if (currentTimeCalendar.get(Calendar.MONTH) + 1 != currentMonthNumber) {
            currentMonthNumber = currentTimeCalendar.get(Calendar.MONTH) + 1;
            return true;
        }
        return false;
    }
}
