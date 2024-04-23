package crosinfo.com.passin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import crosinfo.com.passin.domain.attendee.Attendee;
import crosinfo.com.passin.domain.checkin.CheckIn;
import crosinfo.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import crosinfo.com.passin.domain.event.Event;
import crosinfo.com.passin.repository.CheckInRepository;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {
	
	@Mock
	private CheckInRepository checkInRepository;
	
	private Event event;
	
	private Attendee attendee;
	
	private CheckInService service;
	
	private LocalDateTime dateTime;
	
	@BeforeEach
	public void setUpTest() {
		service = new CheckInService(checkInRepository);
		dateTime = LocalDateTime.now();
		event = new Event("321", "Title", "details", "slug", 10);
		attendee = new Attendee("123", "teste", "teste@teste", event, dateTime);
	}
	
	@Test
	void testRegisterCheckInSuccess() {
		CheckIn newCheckIn = new CheckIn();
		
		when(checkInRepository.findByAttendeeId(anyString())).thenReturn(Optional.empty());
		when(checkInRepository.save(any(CheckIn.class))).thenReturn(newCheckIn);
		
		service.registerCheckIn(attendee);
				
		InOrder inOrder = Mockito.inOrder(checkInRepository);
		inOrder.verify(checkInRepository).findByAttendeeId(anyString());
		inOrder.verify(checkInRepository).save(any());
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	void testRegisterCheckInAlreadyExistsException() {
		CheckIn newCheckIn = new CheckIn(1, dateTime, attendee);
		
		when(checkInRepository.findByAttendeeId(anyString())).thenReturn(Optional.of(newCheckIn));
		
		assertThrows(CheckInAlreadyExistsException.class, () -> service.registerCheckIn(attendee));
				
		InOrder inOrder = Mockito.inOrder(checkInRepository);
		inOrder.verify(checkInRepository).findByAttendeeId(anyString());
		inOrder.verify(checkInRepository, never()).save(any());
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	void testGetCheckIsPresent() {
		Optional<CheckIn> newCheckIn = Optional.of(new CheckIn(1, dateTime, attendee));
		
		when(checkInRepository.findByAttendeeId(attendee.getId())).thenReturn(newCheckIn);
		
		assertEquals(service.getCheckIn(attendee.getId()), newCheckIn);
		
		InOrder inOrder = Mockito.inOrder(checkInRepository);
		inOrder.verify(checkInRepository).findByAttendeeId(attendee.getId());
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	void testGetCheckIsEmpty() {
		when(checkInRepository.findByAttendeeId(anyString())).thenReturn(Optional.empty());
		
		assertEquals(service.getCheckIn(anyString()), Optional.empty());
		
		InOrder inOrder = Mockito.inOrder(checkInRepository);
		inOrder.verify(checkInRepository).findByAttendeeId(anyString());
		inOrder.verifyNoMoreInteractions();
	}

}
