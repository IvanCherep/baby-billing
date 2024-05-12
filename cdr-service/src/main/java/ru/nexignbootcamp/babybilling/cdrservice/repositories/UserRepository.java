package ru.nexignbootcamp.babybilling.cdrservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAllByOrderByIdAsc();

}
