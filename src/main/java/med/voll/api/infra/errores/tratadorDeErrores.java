package med.voll.api.infra.errores;

import java.io.ObjectInputStream.GetField;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class tratadorDeErrores {
	
	@ExceptionHandler(EntityNotFoundException.class)    //le dice a spring que esta funcion se debe lanzar cuando detecta un error 
	public ResponseEntity tratarError404() {   //el que genera el error y tiene todo listo es es el RE 
		return ResponseEntity.notFound().build(); //
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)    //le dice a spring que esta funcion se debe lanzar cuando detecta un error 
	public ResponseEntity tratarError400(MethodArgumentNotValidException e) {   //el que genera el error y tiene todo listo es es el RE 
		var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList(); // get al errors da un objeto, get fielderrors con s da una lista 
		return ResponseEntity.badRequest().body(errores); 
	}
	
	@ExceptionHandler(ValidacionDeIntegridad.class)    //le dice a spring que esta funcion se debe lanzar cuando detecta un error 
	public ResponseEntity errorhandlerValidacionesNegecio(Exception e) {   //el que genera el error y tiene todo listo es es el RE 
		
		return ResponseEntity.badRequest().body(e.getMessage()); 
	}
	
	private record DatosErrorValidacion(String campo, String error) {
		
		public DatosErrorValidacion(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
	
	
	
	
}
