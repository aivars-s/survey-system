package aivars.survey.bootstrap;

import aivars.survey.domain.QuestionType;
import aivars.survey.dto.CreateOrUpdateAnswerDTO;
import aivars.survey.dto.CreateOrUpdateFormDTO;
import aivars.survey.dto.CreateOrUpdateQuestionDTO;
import aivars.survey.dto.FormDTO;
import aivars.survey.service.AnswerService;
import aivars.survey.service.FormService;
import aivars.survey.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitSampleData implements CommandLineRunner {

	private final FormService formService;
	private final QuestionService questionService;
	private final AnswerService answerService;

	private void createData() {
		for (int i = 0; i < 2; i++) {
			formService.create(new CreateOrUpdateFormDTO());
		}

		var formDto = new CreateOrUpdateFormDTO();
		formDto.setTitle("Sample with all question types");
		var formId = formService.create(formDto).getId();

		var q1Dto = new CreateOrUpdateQuestionDTO();
		q1Dto.setType(QuestionType.MULTIPLE_LINE_TEXT);
		questionService.create(formId, q1Dto);

		var q2Dto = new CreateOrUpdateQuestionDTO();
		q2Dto.setType(QuestionType.SINGLE_CHOICE);
		questionService.create(formId, q2Dto);

		var q3Dto = new CreateOrUpdateQuestionDTO();
		q3Dto.setType(QuestionType.MULTIPLE_CHOICE);
		questionService.create(formId, q3Dto);

		var q4Dto = new CreateOrUpdateQuestionDTO();
		q4Dto.setType(QuestionType.GRID_SINGLE_CHOICE);
		questionService.create(formId, q4Dto);

		var q5Dto = new CreateOrUpdateQuestionDTO();
		q5Dto.setType(QuestionType.GRID_MULTIPLE_CHOICE);
		questionService.create(formId, q5Dto);
	}

	@Override
	public void run(String... args) throws Exception {
		createData();
	}

}
