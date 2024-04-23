package ru.nexignbootcamp.babybilling.brtservice.mappers.impl;

import org.modelmapper.ModelMapper;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.ClientDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.ClientEntity;
import ru.nexignbootcamp.babybilling.brtservice.mappers.Mapper;

public class ClientMapperImpl implements Mapper<ClientEntity, ClientDto> {

    private ModelMapper modelMapper;

    public ClientMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDto mapTo(ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, ClientDto.class);
    }

    @Override
    public ClientEntity mapFrom(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }
}
