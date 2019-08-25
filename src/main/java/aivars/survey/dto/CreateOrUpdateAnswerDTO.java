package aivars.survey.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CreateOrUpdateAnswerDTO {

	private String title;

	@Min(1)
	private Integer order;

}
