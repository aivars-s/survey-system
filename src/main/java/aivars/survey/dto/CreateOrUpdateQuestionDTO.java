package aivars.survey.dto;

import aivars.survey.domain.QuestionType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrUpdateQuestionDTO {

	private String title;

	@Min(1)
	private Integer order;

	@NotNull
	private QuestionType type;

}
