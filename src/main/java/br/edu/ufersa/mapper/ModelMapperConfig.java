package br.edu.ufersa.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ufersa.data.vo.v1.BookVO;
import br.edu.ufersa.data.vo.v1.PersonVO;
import br.edu.ufersa.model.Book;
import br.edu.ufersa.model.Person;

@Configuration
public class ModelMapperConfig {

    @Bean(name = "modelMapperPerson")
    ModelMapper modelMapperPerson() {
        ModelMapper modelMapperPerson = new ModelMapper();
        
        modelMapperPerson.addMappings(new PropertyMap<Person, PersonVO>() {
            @Override
            protected void configure() {
                map().setKey(source.getId());
            }
        });

        return modelMapperPerson;
    }
    
    @Bean(name = "modelMapperBook")
    ModelMapper modelMapperBook() {
        ModelMapper modelMapperBook = new ModelMapper();
        
        modelMapperBook.addMappings(new PropertyMap<Book, BookVO>() {
            @Override
            protected void configure() {
                map().setKey(source.getId());
            }
        });

        return modelMapperBook;
    }

}
