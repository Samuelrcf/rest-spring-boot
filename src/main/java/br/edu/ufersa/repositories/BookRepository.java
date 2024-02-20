package br.edu.ufersa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufersa.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
