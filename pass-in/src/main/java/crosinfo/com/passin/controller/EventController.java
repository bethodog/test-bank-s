package crosinfo.com.passin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import crosinfo.com.passin.dto.attendee.AttendeeIdDTO;
import crosinfo.com.passin.dto.attendee.AttendeesRequestDTO;
import crosinfo.com.passin.dto.attendee.AttendeesResponseListDTO;
import crosinfo.com.passin.dto.event.EventIdDTO;
import crosinfo.com.passin.dto.event.EventRequestDTO;
import crosinfo.com.passin.dto.event.EventResponseDTO;
import crosinfo.com.passin.service.AttendeeService;
import crosinfo.com.passin.service.EventService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
	
	private final EventService eventService;
	private final AttendeeService attendeeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
		EventResponseDTO event = eventService.getEventDetail(id); 
		return ResponseEntity.ok(event);
	}
	
	@GetMapping("/attendees/{id}")
	public ResponseEntity<AttendeesResponseListDTO> getEventAttendees(@PathVariable String id) {
		AttendeesResponseListDTO attendee = attendeeService.getEventsAttendee(id); 
		return ResponseEntity.ok(attendee);
	}
	
	@PostMapping
	public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
		EventIdDTO eventIdDTO = eventService.createEvent(body);
		
		var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
		
		return ResponseEntity.created(uri).body(eventIdDTO);
	}
	
	@PostMapping("/{eventId}/attendees")
	public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeesRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
		
		AttendeeIdDTO attendeeIdDTO = eventService.registerAttendeeOnEvent(eventId, body);
		
		var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
		
		return ResponseEntity.created(uri).body(attendeeIdDTO);
	}

}
