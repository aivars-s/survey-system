package aivars.survey.repository;

import aivars.survey.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
