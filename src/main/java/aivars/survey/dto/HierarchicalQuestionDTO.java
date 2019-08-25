package aivars.survey.dto;

import aivars.survey.domain.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class HierarchicalQuestionDTO {

	private UUID id;
	private String title;
	private Integer order;
	private QuestionType type;
	private UUID formId;
	private UUID parentId;
	private List<QuestionDTO> subQuestions;
	private List<AnswerDTO> answers;

}
