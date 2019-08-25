package aivars.survey.service;

import aivars.survey.dto.CreateOrUpdateQuestionDTO;
import aivars.survey.dto.HierarchicalQuestionDTO;
import aivars.survey.dto.QuestionDTO;

import java.util.UUID;

public interface QuestionService {

	HierarchicalQuestionDTO create(UUID formId, CreateOrUpdateQuestionDTO dto);
	QuestionDTO createSubQuestion(UUID questionId, CreateOrUpdateQuestionDTO dto);
	void delete(UUID id);
	void deleteAll(UUID formId);
	void deleteAllSubQuestions(UUID questionId);
	HierarchicalQuestionDTO update(UUID id, CreateOrUpdateQuestionDTO dto);

}
