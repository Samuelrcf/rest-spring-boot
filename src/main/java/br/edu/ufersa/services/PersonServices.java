package br.edu.ufersa.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.edu.ufersa.controllers.PersonController;
import br.edu.ufersa.data.vo.v1.PersonVO;
import br.edu.ufersa.exceptions.ResourceNotFoundException;
import br.edu.ufersa.model.Person;
import br.edu.ufersa.repositories.PersonRepository;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repo;
    
    @Autowired
    @Qualifier("modelMapperPerson")
    ModelMapper mapper;
 
    public List<PersonVO> findAll() {
        logger.info("Finding all people");
        List<Person> people = repo.findAll();
        
        List<PersonVO> vos = new ArrayList<>(people.size());
        for (Person person : people) {
            PersonVO vo = mapper.map(person, PersonVO.class);
            try {
                vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vos.add(vo);
        }

        return vos;
    }


    public PersonVO findById(Long id) {
        logger.info("Finding one person");
        var person = repo.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));
        var vo = mapper.map(person, PersonVO.class);
       try {
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
	} catch (Exception e) {
		e.printStackTrace();
	}
       return vo;
    }

    public PersonVO post(PersonVO personVO) {
        logger.info("Creating one person");
        Person person = mapper.map(personVO, Person.class);
        PersonVO vo = mapper.map(repo.save(person), PersonVO.class);
        try {
    		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
       return vo;
    }

    public PersonVO put(PersonVO personVO) {
        logger.info("Updating one person");

        var person = repo.findById(personVO.getKey())
        		.orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + personVO.getKey()));
        person.setFirstName(personVO.getFirstName());
        person.setLastName(personVO.getLastName());
        person.setAddress(personVO.getAddress());
        person.setGender(personVO.getGender());

        var vo = mapper.map(repo.save(person), PersonVO.class);
        
        try {
			vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
    }



    public void delete(Long id) {
        repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));

        repo.deleteById(id);
    }
}
