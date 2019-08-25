package aivars.survey.mapper;

import aivars.survey.domain.Form;
import aivars.survey.dto.CreateOrUpdateFormDTO;
import aivars.survey.dto.FormDTO;
import aivars.survey.dto.HierarchicalFormDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface FormMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createdAt", ignore = true),
		@Mapping(target = "updatedAt", ignore = true),
		@Mapping(target = "version", ignore = true),
		@Mapping(target = "questions", ignore = true)
	})
	Form toForm(CreateOrUpdateFormDTO source);

	FormDTO toFormDto(Form source);
	List<FormDTO> toFormDto(Collection<Form> source);

	HierarchicalFormDTO toHierarchicalFormDto(Form source);

	@InheritConfiguration
	void updateForm(CreateOrUpdateFormDTO source, @MappingTarget Form target);

}
