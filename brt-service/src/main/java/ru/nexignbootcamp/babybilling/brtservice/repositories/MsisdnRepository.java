package ru.nexignbootcamp.babybilling.brtservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;

public interface MsisdnRepository extends CrudRepository<MsisdnEntity, Long> {
}
