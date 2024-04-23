package crosinfo.com.passin.domain.checkin;

import java.time.LocalDateTime;

import crosinfo.com.passin.domain.attendee.Attendee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "check_ins")
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@OneToOne
	@JoinColumn(name = "attendee_id", nullable = false)
	private Attendee attendee;
}
