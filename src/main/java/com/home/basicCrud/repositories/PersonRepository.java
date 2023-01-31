package com.home.basicCrud.repositories;

import com.home.basicCrud.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {//qual eh o model e o identificador

    boolean existsByEmail(String email);

}
