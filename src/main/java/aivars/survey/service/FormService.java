package aivars.survey.service;

import aivars.survey.dto.CreateOrUpdateFormDTO;
import aivars.survey.dto.FormDTO;
import aivars.survey.dto.HierarchicalFormDTO;

import java.util.List;
import java.util.UUID;

public interface FormService {

	HierarchicalFormDTO create(CreateOrUpdateFormDTO dto);
	void delete(UUID id);
	List<FormDTO> findAll();
	HierarchicalFormDTO findById(UUID id);
	void update(UUID id, CreateOrUpdateFormDTO dto);

}
