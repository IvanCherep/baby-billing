package ru.nexignbootcamp.babybilling.cdrservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;

@Repository
public interface CDRRepository extends CrudRepository<CDREntity, Long> {
}
