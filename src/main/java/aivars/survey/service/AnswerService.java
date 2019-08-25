package aivars.survey.service;

import aivars.survey.dto.AnswerDTO;
import aivars.survey.dto.CreateOrUpdateAnswerDTO;

import java.util.UUID;

public interface AnswerService {

	AnswerDTO create(UUID questionId, CreateOrUpdateAnswerDTO dto);
	void delete(UUID answerId);
	void deleteAll(UUID questionId);
	void update(UUID answerId, CreateOrUpdateAnswerDTO dto);

}
