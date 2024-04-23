package crosinfo.com.passin.service;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import crosinfo.com.passin.domain.attendee.Attendee;
import crosinfo.com.passin.domain.event.Event;
import crosinfo.com.passin.domain.event.exceptions.EventFullException;
import crosinfo.com.passin.domain.event.exceptions.EventNotFoundException;
import crosinfo.com.passin.dto.attendee.AttendeeIdDTO;
import crosinfo.com.passin.dto.attendee.AttendeesRequestDTO;
import crosinfo.com.passin.dto.event.EventIdDTO;
import crosinfo.com.passin.dto.event.EventRequestDTO;
import crosinfo.com.passin.dto.event.EventResponseDTO;
import crosinfo.com.passin.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	
	private final EventRepository eventRepository;
	private final AttendeeService attendeeService;
	
	public EventResponseDTO getEventDetail(String eventId) {
		
		Event event = getEventById(eventId);
		List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvents(eventId);
		
		return new EventResponseDTO(event, attendeeList.size());
		
	}
	
	public EventIdDTO createEvent(EventRequestDTO eventDTO) {
		
		Event event = new Event();
		event.setTitle(eventDTO.title());
		event.setDetails(eventDTO.details());
		event.setSlug(createSlug(eventDTO.title()));
		event.setMaximumAttendees(eventDTO.maximumAttendees());
		
		eventRepository.save(event);
		
		return new EventIdDTO(event.getId());
		
	}
	
	public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeesRequestDTO attendeesRequestDTO) {
		
		attendeeService.verifyAttendeeSubscription(attendeesRequestDTO.email(), eventId);
		
		Event event = getEventById(eventId);
		List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvents(eventId);
		
		if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");
		
		Attendee newAttendee = new Attendee();
		newAttendee.setName(attendeesRequestDTO.name());
		newAttendee.setEmail(attendeesRequestDTO.email());
		newAttendee.setEvent(event);
		newAttendee.setCreatedAt(LocalDateTime.now());
		attendeeService.registerAttendee(newAttendee);
		
		return new AttendeeIdDTO(newAttendee.getId());
	}
	
	private String createSlug(String text) {
		String normalized = Normalizer.normalize(text, Form.NFD);
		return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]" , "")
				.replaceAll("[^\\W\\S]", "")
				.replaceAll("\\s+" , "-").toLowerCase();
	}
	
	private Event getEventById(String eventId) {
		return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Evento não encontrado ID " + eventId));
	}

}
