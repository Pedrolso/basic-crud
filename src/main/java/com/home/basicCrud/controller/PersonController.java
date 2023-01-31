package com.home.basicCrud.controller;

import com.home.basicCrud.dto.PersonDto;
import com.home.basicCrud.model.Person;
import com.home.basicCrud.services.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/person-p") //sei la. Talvez busca nesse diretorio
public class PersonController {

    final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestBody PersonDto personDto) { //salve //metodo. Esta dentro do service
        if (personService.existByEmail(personDto.getEmail())) { //chega se tem email igual
            return ResponseEntity.status(HttpStatus.CONFLICT).body(("Existing email!"));
        }

        Person person = new Person();
        BeanUtils.copyProperties(personDto, person); //Converte DTO para person
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(person));
    }

    @GetMapping
    public ResponseEntity<Page<Person>> getAllPerson(@PageableDefault (page = 0, size = 10,sort = "id", direction = Sort.Direction.ASC) Pageable pageable) { //Buscar
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") UUID id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found by ID!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") UUID id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found by ID");
        }
        personService.delete(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }

    @PutMapping("/{id}") //atualiza por ID
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") UUID id, @RequestBody PersonDto personDto) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found by ID");
        }
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        person.setId(personOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(personService.save(person));
    }
}
