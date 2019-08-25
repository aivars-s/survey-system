package aivars.survey.service.impl;

import aivars.survey.domain.Form;
import aivars.survey.domain.Question;
import aivars.survey.domain.QuestionType;
import aivars.survey.dto.CreateOrUpdateFormDTO;
import aivars.survey.dto.FormDTO;
import aivars.survey.dto.HierarchicalFormDTO;
import aivars.survey.mapper.FormMapper;
import aivars.survey.repository.FormRepository;
import aivars.survey.service.FormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FormServiceImpl implements FormService {

	private final FormMapper mapper;
	private final FormRepository repository;
	private final FormServiceImpl self;

	public FormServiceImpl(FormMapper mapper, FormRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
		self = this;
	}

	@Override
	@Transactional
	public HierarchicalFormDTO create(CreateOrUpdateFormDTO dto) {
		var form = mapper.toForm(dto);
		self.initForm(form);
		return mapper.toHierarchicalFormDto(repository.save(form));
	}

	@Override
	public void delete(UUID id) {
		repository.deleteById(id);
	}

	@Override
	public List<FormDTO> findAll() {
		return mapper.toFormDto(repository.findAll());
	}

	@Override
	@Transactional
	public HierarchicalFormDTO findById(UUID id) {
		return mapper.toHierarchicalFormDto(repository.findById(id).orElseThrow());
	}

	@Override
	public void update(UUID id, CreateOrUpdateFormDTO dto) {
		var form = repository.findById(id).orElseThrow();
		mapper.updateForm(dto, form);
		repository.save(form);
	}

	@Transactional
	public void initForm(Form form) {
		if (form.getTitle() == null || form.getTitle().isBlank()) {
			var count = repository.count();
			form.setTitle("Form " + (count + 1));
		}

		var question = new Question();
		question.setTitle("Question 1");
		question.setOrder(1);
		question.setType(QuestionType.SINGLE_LINE_TEXT);

		form.addQuestion(question);
	}

}
