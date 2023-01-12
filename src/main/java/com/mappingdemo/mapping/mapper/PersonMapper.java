package com.mappingdemo.mapping.mapper;


import com.mappingdemo.mapping.entity.PersonEntity;
import com.mappingdemo.mapping.model.PersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper
{
    PersonMapper INSTANCE= Mappers.getMapper(PersonMapper.class);

  PersonRequest EntityToModel (PersonEntity personEntity);

  PersonEntity ModelToEntity (PersonRequest personRequest);

  List<PersonRequest> ModelToEntities(List<PersonEntity> personEntity);

}
