package aivars.survey.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class FormDTO {

	private UUID id;
	private String title;

}
