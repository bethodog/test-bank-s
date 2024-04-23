package crosinfo.com.passin.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import crosinfo.com.passin.domain.attendee.Attendee;
import crosinfo.com.passin.domain.checkin.CheckIn;
import crosinfo.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import crosinfo.com.passin.repository.CheckInRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckInService {
	
	private final CheckInRepository checkInRepository;
	
	public void registerCheckIn(Attendee attendee) {
		
		verifyCheckInExists(attendee.getId());
		
		CheckIn newCheckIn = new CheckIn();
		newCheckIn.setAttendee(attendee);
		newCheckIn.setCreatedAt(LocalDateTime.now());
		
		checkInRepository.save(newCheckIn);
	}
	
	public Optional<CheckIn> getCheckIn(String attendeeId){
		return checkInRepository.findByAttendeeId(attendeeId);
	}
	
	private void verifyCheckInExists(String attendeeId) {
		Optional<CheckIn> isCheckIn = getCheckIn(attendeeId);
		if(isCheckIn.isPresent()) throw new CheckInAlreadyExistsException("Já existe um check-in para o participante: " + attendeeId);
	}

}
