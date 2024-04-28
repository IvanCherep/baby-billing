package ru.nexignbootcamp.babybilling.cdrservice.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.UserEntity;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.CDRRepository;
import ru.nexignbootcamp.babybilling.cdrservice.repositories.UserRepository;

import java.util.Optional;
import java.util.Random;

@Service
@Log
public class GeneratorService {

    final private Long leftBoundary = 1704056400L;

    // 1 Month
    final private Long rightBoundary = 1706734800L; //1 year - 1735678799L;

    final private Long numberOfUsers = 20L;

    final private Long maxCallTime = 7200L;

    private Long currentStartTime;

    private CDRRepository cdrRepository;

    private UserRepository userRepository;

    private Random random;

    private FileGenerator fileGenerator;

    public GeneratorService(CDRRepository cdrRepository, UserRepository userRepository, FileGenerator fileGenerator) {
        this.cdrRepository = cdrRepository;
        this.userRepository = userRepository;
        this.fileGenerator = fileGenerator;
        random = new Random();
        currentStartTime = leftBoundary;
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
            endTime = currentStartTime + random.nextLong(maxCallTime) + 1;
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

            currentStartTime += (currentStartTime - endTime) / 2;
            if (random.nextBoolean()) {
                currentStartTime += random.nextLong(86400) + 1;
            }

            CDREntity[] cdrPair = new CDREntity[2];
            cdrPair[0] = cdr1;
            cdrPair[1] = cdr2;

            fileGenerator.writeCdrToFile(cdrPair);

        }
    }
}
