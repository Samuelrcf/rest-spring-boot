package br.edu.ufersa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufersa.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
