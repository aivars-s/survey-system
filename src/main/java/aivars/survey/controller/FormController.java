package aivars.survey.controller;

import aivars.survey.dto.CreateOrUpdateFormDTO;
import aivars.survey.dto.FormDTO;
import aivars.survey.dto.HierarchicalFormDTO;
import aivars.survey.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {

	private final FormService service;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public HierarchicalFormDTO create(@RequestBody CreateOrUpdateFormDTO dto) {
		return service.create(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("")
	public List<FormDTO> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public HierarchicalFormDTO findById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CreateOrUpdateFormDTO dto) {
		service.update(id, dto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
