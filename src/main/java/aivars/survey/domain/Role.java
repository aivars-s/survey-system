package aivars.survey.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Roles")
@Data
public class Role {

	@Id
	@Column(name = "Name", nullable = false, updatable = false)
	private String name;

	@OneToMany(mappedBy = "role", cascade = {
		CascadeType.MERGE,
		CascadeType.PERSIST
	})
	private List<User> users = new ArrayList<>();

	@ManyToMany(
		cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
		}
	)
	@JoinTable(
		name = "RoleAuthorities",
		joinColumns = @JoinColumn(name = "RoleName"),
		inverseJoinColumns = @JoinColumn(name = "AuthorityName")
	)
	private List<Authority> authorities = new ArrayList<>();

	public void addAuthority(Authority authority) {
		authorities.add(authority);
		authority.getRoles().add(this);
	}

	public void removeAuthority(Authority authority) {
		authorities.remove(authority);
		authority.getRoles().remove(this);
	}

	public void addUser(User user) {
		users.add(user);
		user.setRole(this);
	}

	public void removeUser(User user) {
		users.remove(user);
		user.setRole(null);
	}

}
