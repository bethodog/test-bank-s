package crosinfo.com.passin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crosinfo.com.passin.domain.checkin.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

	Optional<CheckIn> findByAttendeeId(String attendeeId);
	
}
