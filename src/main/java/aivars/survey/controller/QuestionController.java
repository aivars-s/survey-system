package aivars.survey.controller;

import aivars.survey.dto.CreateOrUpdateQuestionDTO;
import aivars.survey.dto.HierarchicalQuestionDTO;
import aivars.survey.dto.QuestionDTO;
import aivars.survey.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService service;

	@PostMapping("/forms/{id}/questions")
	@ResponseStatus(HttpStatus.CREATED)
	public HierarchicalQuestionDTO create(@PathVariable UUID id, @RequestBody CreateOrUpdateQuestionDTO dto) {
		return service.create(id, dto);
	}

	@PostMapping("/questions/{id}/sub-questions")
	@ResponseStatus(HttpStatus.CREATED)
	public QuestionDTO createSubQuestion(@PathVariable UUID id, @RequestBody CreateOrUpdateQuestionDTO dto) {
		return service.createSubQuestion(id, dto);
	}

	@DeleteMapping("/questions/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/forms/{id}/questions")
	public ResponseEntity<Void> deleteAll(@PathVariable UUID id) {
		service.deleteAll(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/questions/{id}/sub-questions")
	public ResponseEntity<Void> deleteAllSubQuestions(@PathVariable UUID id) {
		service.deleteAllSubQuestions(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/questions/{id}")
	public HierarchicalQuestionDTO updateQuestion(@PathVariable UUID id, @RequestBody CreateOrUpdateQuestionDTO dto) {
		return service.update(id, dto);
	}

}
