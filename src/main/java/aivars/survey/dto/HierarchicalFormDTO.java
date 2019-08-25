package aivars.survey.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class HierarchicalFormDTO {

	private UUID id;
	private String title;
	private List<HierarchicalQuestionDTO> questions;

}
