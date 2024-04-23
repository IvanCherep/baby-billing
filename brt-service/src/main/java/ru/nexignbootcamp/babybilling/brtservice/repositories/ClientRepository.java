package ru.nexignbootcamp.babybilling.brtservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.ClientEntity;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
}
