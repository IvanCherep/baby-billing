package ru.nexignbootcamp.babybilling.brtservice.mappers.impl;

import org.modelmapper.ModelMapper;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.MsisdnDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;
import ru.nexignbootcamp.babybilling.brtservice.mappers.Mapper;

public class MsisdnMapperImpl implements Mapper<MsisdnEntity, MsisdnDto> {

    private ModelMapper modelMapper;

    public MsisdnMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MsisdnDto mapTo(MsisdnEntity msisdnEntity) {
        return modelMapper.map(msisdnEntity, MsisdnDto.class);
    }

    @Override
    public MsisdnEntity mapFrom(MsisdnDto msisdnDto) {
        return modelMapper.map(msisdnDto, MsisdnEntity.class);
    }
}
