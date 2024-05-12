package ru.nexignbootcamp.babybilling.brtservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;

/**
 * Репозиторий для взаимодействия с таблицей телефонных номеров и их характеристик.
 */
public interface MsisdnRepository extends CrudRepository<MsisdnEntity, Long> {
}
