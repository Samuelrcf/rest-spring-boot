package br.edu.ufersa.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ufersa.exceptions.ExceptionResponse;
import br.edu.ufersa.exceptions.InvalidJwtAuthenticationException;
import br.edu.ufersa.exceptions.ResourceNotFoundException;

@ControllerAdvice //concentrar tratamentos genéricos
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	 public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request){
		 ExceptionResponse er = new ExceptionResponse(
				 new Date(), ex.getMessage(), request.getDescription(false));
		 return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request){
		ExceptionResponse er = new ExceptionResponse(
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(Exception ex, WebRequest request){
		ExceptionResponse er = new ExceptionResponse(
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.FORBIDDEN);
	}
}
