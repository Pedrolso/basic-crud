package com.home.basicCrud.services;

import com.home.basicCrud.model.Person;
import com.home.basicCrud.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

//Pode apagar AUTOWIRED
@Service
public class PersonService { //Regra de negocio.  Controller aciona o service que aciona o repository
    final PersonRepository personRepository; //criar em construtor a baixo.

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional //Garante rollback
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public boolean existByEmail(String email) { //Precisa declarar esse metodo dentro do repository
        return personRepository.existsByEmail(email);
    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public Optional<Person> findById(UUID id) {
        return personRepository.findById(id);
    }

    @Transactional
    public void delete(Person person) {
        personRepository.delete(person);
    }
}
