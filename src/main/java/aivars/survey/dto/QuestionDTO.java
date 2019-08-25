package aivars.survey.dto;

import aivars.survey.domain.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class QuestionDTO {

	private UUID id;
	private String title;
	private Integer order;
	private QuestionType type;
	private UUID formId;
	private UUID parentId;

}
