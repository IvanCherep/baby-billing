package ru.nexignbootcamp.babybilling.brtservice.mappers.impl;

import org.modelmapper.ModelMapper;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.DataPlanDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.DataPlanEntity;
import ru.nexignbootcamp.babybilling.brtservice.mappers.Mapper;

/**
 * Преобразует DataPlanEntity в DataPlanDto и обратно.
 */
public class DataPlanMapperImpl implements Mapper<DataPlanEntity, DataPlanDto> {

    private ModelMapper modelMapper;

    public DataPlanMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DataPlanDto mapTo(DataPlanEntity dataPlanEntity) {
        return modelMapper.map(dataPlanEntity, DataPlanDto.class);
    }

    @Override
    public DataPlanEntity mapFrom(DataPlanDto dataPlanDto) {
        return modelMapper.map(dataPlanDto, DataPlanEntity.class);
    }
}
