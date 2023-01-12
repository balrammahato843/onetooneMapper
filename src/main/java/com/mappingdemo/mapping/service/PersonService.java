package com.mappingdemo.mapping.service;

import com.mappingdemo.mapping.entity.AddressEntity;
import com.mappingdemo.mapping.entity.PersonEntity;
import com.mappingdemo.mapping.mapper.PersonMapper;
import com.mappingdemo.mapping.model.PersonRequest;
import com.mappingdemo.mapping.model.PersonResponse;

import com.mappingdemo.mapping.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PersonResponse createPerson(PersonRequest personRequest) {
        PersonEntity personEntity = personMapper.ModelToEntity(personRequest);
        personRepository.save(personEntity);
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(personEntity.getPersonId());
        return personResponse;
    }

    @Cacheable(cacheNames = "persons" , key = "#personId")
    public PersonRequest getPersonById(Long personId) {
        log.info("fetching person from Database");
        PersonEntity personEntity = personRepository.getOne(personId);
        PersonRequest personRequest = personMapper.EntityToModel(personEntity);

        return personRequest;
    }

    public List<PersonRequest> getAllPersons() {
        List<PersonEntity> personEntity = personRepository.findAll();
        List<PersonRequest> personRequest = personMapper.ModelToEntities(personEntity);
        return personRequest;
    }

    @CacheEvict(cacheNames = "persons" , key = "#personId")
    public void DeleteById(Long personId) {
        personRepository.deleteById(personId);
    }


    @CachePut(cacheNames = "persons" , key = "#personId")
    public PersonRequest updatePerson(Long personId, PersonRequest personRequest) {

        log.info("Updated with new person");
        PersonEntity personEntity1=personRepository.findById(personId).get();
        PersonEntity personEntity = personMapper.ModelToEntity(personRequest);

        Long addressId = personEntity1.getAddress().getAddressId();
        personEntity.getAddress().setAddressId(addressId);
        personEntity.setPersonId(personId);
        personRepository.save(personEntity);

        return  personMapper.EntityToModel(personEntity);
    }













//    public PersonResponse createPerson(PersonRequest personRequest) {
//
//        PersonEntity personEntity = new PersonEntity();
//        personEntity.setFirstName(personRequest.getFirstName());
//        personEntity.setLastName(personRequest.getLastName());
//
//        AddressEntity addressEntity = new AddressEntity();
//        addressEntity.setAddress1(personRequest.getAddress().getAddress1());
//        addressEntity.setAddress2(personRequest.getAddress().getAddress2());
//        addressEntity.setCity(personRequest.getAddress().getCity());
//        addressEntity.setState(personRequest.getAddress().getState());
//        addressEntity.setZipCode(personRequest.getAddress().getZipCode());
//
//        personEntity.setAddress(addressEntity);
//
//        personRepository.save(personEntity);
//
//        PersonResponse personResponse = new PersonResponse();
//        personResponse.setId(personEntity.getPersonId());
//
//        return personResponse;
//    }
//
//
//    public PersonRequest getPersonById(Long personId) {
//
//        AddressRequest addressRequest = new AddressRequest();
//        PersonRequest personRequest1 = new PersonRequest();
//        Optional<PersonEntity> personEntity = personRepository.findById(personId);
//
//        if (personEntity.isPresent()) {
//            log.info("person " + personEntity.get().getPersonId() + " is found into the database...............");
//            addressRequest.setAddress1(personEntity.get().getAddress().getAddress1());
//            addressRequest.setAddress2(personEntity.get().getAddress().getAddress2());
//            addressRequest.setCity(personEntity.get().getAddress().getCity());
//            addressRequest.setState(personEntity.get().getAddress().getState());
//            addressRequest.setZipCode(personEntity.get().getAddress().getZipCode());
//
//            personRequest1.setFirstName(personEntity.get().getFirstName());
//            personRequest1.setLastName(personEntity.get().getLastName());
//            personRequest1.setAddress(addressRequest);
//
//            personRepository.save(personEntity.get());
//        } else {
//            log.info("person " + personEntity.get().getPersonId() + " is not found into the database...............");
//        }
//        return personRequest1;
//    }
//
//    public void deleteById(Long personId) {
//        personRepository.deleteById(personId);
//    }
//
//    public PersonRequest updatePerson(Long personId, PersonRequest personRequest) {
//
//        PersonEntity personEntity = personRepository.findById(personId).get();
//        AddressEntity addressEntity = personEntity.getAddress();
//
//        addressEntity.setAddress1(personRequest.getAddress().getAddress1());
//        addressEntity.setAddress2(personRequest.getAddress().getAddress2());
//        addressEntity.setState(personRequest.getAddress().getState());
//        addressEntity.setCity(personRequest.getAddress().getCity());
//        addressEntity.setZipCode(personRequest.getAddress().getZipCode());
//
//        personEntity.setFirstName(personRequest.getFirstName());
//        personEntity.setLastName(personRequest.getLastName());
//        personEntity.setAddress(addressEntity);
//
//        personRepository.save(personEntity);
//
//        return personRequest;
//    }
//
//
//    public List<PersonRequest> getAllPerson() {
//        List<PersonEntity> personEntities = personRepository.findAll();
//        List<PersonRequest> personRequests = new ArrayList<>(personEntities.size());
//
//        for (PersonEntity personEntity : personEntities) {
//            AddressRequest addressRequest = new AddressRequest();
//            addressRequest.setAddress1(personEntity.getAddress().getAddress1());
//            addressRequest.setAddress2(personEntity.getAddress().getAddress2());
//            addressRequest.setCity(personEntity.getAddress().getCity());
//            addressRequest.setState(personEntity.getAddress().getState());
//            addressRequest.setZipCode(personEntity.getAddress().getZipCode());
//
//            PersonRequest personRequest = new PersonRequest();
//            personRequest.setFirstName(personEntity.getFirstName());
//            personRequest.setLastName(personEntity.getLastName());
//            personRequest.setAddress(addressRequest);
//            personRequests.add(personRequest);
//        }
//
//        return personRequests;
//    }


}
