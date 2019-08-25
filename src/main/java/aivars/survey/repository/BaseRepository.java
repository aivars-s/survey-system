package aivars.survey.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {

	List<T> findAll();
	<U> Optional<U> findById(ID id, Class<U> type);

}
