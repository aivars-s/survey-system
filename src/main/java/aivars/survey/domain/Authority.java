package aivars.survey.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Authorities")
@Data
public class Authority {

	@Id
	@Column(name = "Name", nullable = false, updatable = false)
	private String name;

	@ManyToMany(mappedBy = "authorities")
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(mappedBy = "additionalAuthorities")
	private Set<User> users = new HashSet<>();

}
