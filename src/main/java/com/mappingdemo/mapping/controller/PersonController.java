package com.mappingdemo.mapping.controller;


import com.mappingdemo.mapping.mapper.PersonMapper;
import com.mappingdemo.mapping.model.PersonRequest;
import com.mappingdemo.mapping.model.PersonResponse;
import com.mappingdemo.mapping.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.Date;
import java.util.List;

@RestController

@Log4j2
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // schedule a job to add object in DB ( Every 5 sec. )
   // @Scheduled(fixedRate = 5000)
    @PostMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonRequest personRequest) {
       log.info("added new person" + new Date().toString());
        PersonResponse personResponse = personService.createPerson(personRequest);
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/persons/{personId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonRequest> getPersonById(@PathVariable Long personId)
    {
        PersonRequest personRequest= personService.getPersonById(personId);
        return new ResponseEntity<>(personRequest, HttpStatus.OK);
    }

   // @Scheduled(cron="0/15 * * * * *")
    @GetMapping(value = "/persons" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonRequest>> getAllPersons()
    {
        List<PersonRequest> personRequest = personService.getAllPersons();
        log.info("Total no of persons" + personRequest.size());
        return new ResponseEntity<>(personRequest , HttpStatus.OK);
    }

    @DeleteMapping(value = "/persons/{personId}")
    public  void DeleteById(@PathVariable Long personId )
    {
        personService.DeleteById(personId);
    }

    @PutMapping(value = "/persons/{personId}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonRequest> updatePerson(@PathVariable Long personId , @RequestBody PersonRequest personRequest)
    {
        PersonRequest personRequest1= personService.updatePerson(personId, personRequest);
        return new ResponseEntity<>(personRequest1, HttpStatus.OK);
    }
}



