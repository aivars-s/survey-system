package aivars.survey.mapper;

import aivars.survey.domain.Answer;
import aivars.survey.dto.AnswerDTO;
import aivars.survey.dto.CreateOrUpdateAnswerDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createdAt", ignore = true),
		@Mapping(target = "updatedAt", ignore = true),
		@Mapping(target = "version", ignore = true),
		@Mapping(target = "question", ignore = true)
	})
	Answer toAnswer(CreateOrUpdateAnswerDTO source);

	@Mapping(source = "source.question.id", target = "questionId")
	AnswerDTO toAnswerDto(Answer source);

	List<AnswerDTO> toAnswerDto(Collection<Answer> source);

	@InheritConfiguration
	void updateAnswer(CreateOrUpdateAnswerDTO source, @MappingTarget Answer target);

}
