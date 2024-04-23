package crosinfo.com.passin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crosinfo.com.passin.domain.attendee.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {

	List<Attendee> findByEventId(String eventId);

	Optional<Attendee> findByEmailAndEventId(String email, String eventId);

}
