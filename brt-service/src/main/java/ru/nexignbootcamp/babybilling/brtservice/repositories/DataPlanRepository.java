package ru.nexignbootcamp.babybilling.brtservice.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.DataPlanEntity;

public interface DataPlanRepository extends CrudRepository<DataPlanEntity, Integer> {
}
