package crosinfo.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import crosinfo.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import crosinfo.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import crosinfo.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import crosinfo.com.passin.domain.event.exceptions.EventFullException;
import crosinfo.com.passin.domain.event.exceptions.EventNotFoundException;
import crosinfo.com.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity handleEventNotFound(EventNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(EventFullException.class)
	public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException ex) {
		return ResponseEntity.badRequest().body(new ErrorResponseDTO(ex.getMessage()));
	}
	
	@ExceptionHandler(AttendeeNotFoundException.class)
	public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(AttendeeAlreadyRegisteredException.class)
	public ResponseEntity handleAttendeeAlreadyRegistered(AttendeeAlreadyRegisteredException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@ExceptionHandler(CheckInAlreadyExistsException.class)
	public ResponseEntity handleCheckInAlreadyExists(CheckInAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
