package aivars.survey.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Users")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity implements UserDetails {

	@Column(name = "Username", nullable = false, unique = true, updatable = false)
	private String username;

	@Column(name = "Password", nullable = false)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RoleName")
	private Role role;

	@ManyToMany(
		cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
		}
	)
	@JoinTable(
		name = "UserAdditionalAuthorities",
		joinColumns = @JoinColumn(name = "UserId", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "AuthorityName", nullable = false)
	)
	private Set<Authority> additionalAuthorities = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public void addAdditionalAuthority(Authority authority) {
		additionalAuthorities.add(authority);
		authority.getUsers().add(this);
	}

	public void removeAdditionalAuthority(Authority authority) {
		additionalAuthorities.remove(authority);
		authority.getUsers().remove(this);
	}

}
