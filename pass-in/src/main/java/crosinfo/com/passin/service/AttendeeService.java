package crosinfo.com.passin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import crosinfo.com.passin.domain.attendee.Attendee;
import crosinfo.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import crosinfo.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import crosinfo.com.passin.domain.checkin.CheckIn;
import crosinfo.com.passin.dto.attendee.AttendeeBadgeDTO;
import crosinfo.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import crosinfo.com.passin.dto.attendee.AttendeeDetails;
import crosinfo.com.passin.dto.attendee.AttendeesResponseListDTO;
import crosinfo.com.passin.repository.AttendeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendeeService {
	
	private final CheckInService checkInService;
	private final AttendeeRepository attendeeRepository;
	
	public AttendeesResponseListDTO getEventsAttendee(String eventId) {
		
		List<Attendee> attendeeList = getAllAttendeesFromEvents(eventId);
		
		List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
			Optional<CheckIn> checkIn = checkInService.getCheckIn(attendee.getId());
			LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
			return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
		}).toList();
		return new AttendeesResponseListDTO(attendeeDetailsList);
	}
	
	public Attendee registerAttendee(Attendee attendee) {
		return attendeeRepository.save(attendee);
	}
	
	public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
		Attendee attendee = getAttendee(attendeeId);
		
		var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();
		
		AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri.toString(), attendee.getEvent().getId());
		
		return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
	}
		
	List<Attendee> getAllAttendeesFromEvents(String eventId) {
		 return attendeeRepository.findByEventId(eventId);
	}
	
	void verifyAttendeeSubscription(String email, String eventId) {
		 Optional<Attendee> isAttendeeRegister = attendeeRepository.findByEmailAndEventId(email, eventId);
		 if(isAttendeeRegister.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee is already registered: " + email);
	}

	public void checkInAttendee(String attendeeId) {
		Attendee attendee = getAttendee(attendeeId);
		checkInService.registerCheckIn(attendee);
	}
	
	private Attendee getAttendee(String attendeeId) {
		return attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
	}

}
