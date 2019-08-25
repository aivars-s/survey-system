package aivars.survey.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class AnswerDTO {

	private UUID id;
	private String title;
	private Integer order;
	private UUID questionId;

}
