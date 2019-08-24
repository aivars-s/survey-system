package aivars.survey.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "Id", nullable = false, updatable = false)
	private UUID id;

	@CreationTimestamp
	@Column(name = "CreatedAt", nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "UpdatedAt", nullable = false)
	private Instant updatedAt;

	@Version
	@Column(name = "Version", nullable = false)
	private Long version;

}
