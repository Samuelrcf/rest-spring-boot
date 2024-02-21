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

import br.edu.ufersa.controllers.BookController;
import br.edu.ufersa.data.vo.v1.BookVO;
import br.edu.ufersa.exceptions.ResourceNotFoundException;
import br.edu.ufersa.model.Book;
import br.edu.ufersa.repositories.BookRepository;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repo;

    @Autowired
    @Qualifier("modelMapperBook")
    ModelMapper mapper;

    public List<BookVO> findAll() {
        logger.info("Finding all books");
        List<Book> books = repo.findAll();

        List<BookVO> vos = new ArrayList<>(books.size());
        for (Book book : books) {
            BookVO vo = mapper.map(book, BookVO.class);
            try {
                vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vos.add(vo);
        }

        return vos;
    }

    public BookVO findById(Long id) {
        logger.info("Finding one book");
        var book = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        var vo = mapper.map(book, BookVO.class);
        try {
            vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public BookVO post(BookVO bookVO) {
        logger.info("Creating one book");
        Book book = mapper.map(bookVO, Book.class);
        BookVO vo = mapper.map(repo.save(book), BookVO.class);
        try {
            vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public BookVO put(BookVO bookVO) {
        logger.info("Updating one book");

        var book = repo.findById(bookVO.getKey())
        		.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookVO.getKey()));
        book.setAuthor(bookVO.getAuthor());
        book.setLaunchDate(bookVO.getLaunchDate());
        book.setPrice(bookVO.getPrice());
        book.setTitle(bookVO.getTitle());
        
        var vo = mapper.map(repo.save(book), BookVO.class);

        try {
            vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public void delete(Long id) {
        repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        repo.deleteById(id);
    }
}
