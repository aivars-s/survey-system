package aivars.survey.controller;

import aivars.survey.dto.AnswerDTO;
import aivars.survey.dto.CreateOrUpdateAnswerDTO;
import aivars.survey.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnswerController {

	private final AnswerService service;

	@PostMapping("/questions/{id}/answers")
	@ResponseStatus(HttpStatus.CREATED)
	public AnswerDTO create(@PathVariable UUID id, @RequestBody CreateOrUpdateAnswerDTO dto) {
		return service.create(id, dto);
	}

	@DeleteMapping("/answers/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/questions/{id}/answers")
	public ResponseEntity<Void> deleteAll(@PathVariable UUID id) {
		service.deleteAll(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/answers/{id}")
	public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CreateOrUpdateAnswerDTO dto) {
		service.update(id, dto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
