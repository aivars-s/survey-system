package aivars.survey.mapper;

import aivars.survey.domain.Question;
import aivars.survey.dto.CreateOrUpdateQuestionDTO;
import aivars.survey.dto.HierarchicalQuestionDTO;
import aivars.survey.dto.QuestionDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = AnswerMapper.class)
public interface QuestionMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createdAt", ignore = true),
		@Mapping(target = "updatedAt", ignore = true),
		@Mapping(target = "version", ignore = true),
		@Mapping(target = "form", ignore = true),
		@Mapping(target = "parent", ignore = true),
		@Mapping(target = "answers", ignore = true),
		@Mapping(target = "subQuestions", ignore = true)
	})
	Question toQuestion(CreateOrUpdateQuestionDTO dto);

	@Mappings({
		@Mapping(source = "source.form.id", target = "formId"),
		@Mapping(source = "source.parent.id", target = "parentId")
	})
	QuestionDTO toQuestionDto(Question source);

	List<QuestionDTO> toQuestionDto(Collection<Question> source);

	@Mappings({
		@Mapping(source = "source.form.id", target = "formId"),
		@Mapping(source = "source.parent.id", target = "parentId")
	})
	HierarchicalQuestionDTO toHierarchicalQuestionDto(Question source);

	@InheritConfiguration
	void updateQuestion(CreateOrUpdateQuestionDTO source, @MappingTarget Question target);

}
