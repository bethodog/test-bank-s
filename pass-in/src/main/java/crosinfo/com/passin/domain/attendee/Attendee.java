package crosinfo.com.passin.domain.attendee;

import java.time.LocalDateTime;

import crosinfo.com.passin.domain.event.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendees")
public class Attendee {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
}
