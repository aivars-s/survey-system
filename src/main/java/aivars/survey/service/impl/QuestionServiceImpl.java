package aivars.survey.service.impl;

import aivars.survey.domain.*;
import aivars.survey.dto.CreateOrUpdateQuestionDTO;
import aivars.survey.dto.HierarchicalQuestionDTO;
import aivars.survey.dto.QuestionDTO;
import aivars.survey.exception.ForbiddenTypeChangeException;
import aivars.survey.exception.ResourceNotFoundException;
import aivars.survey.mapper.QuestionMapper;
import aivars.survey.repository.FormRepository;
import aivars.survey.repository.QuestionRepository;
import aivars.survey.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionMapper mapper;
	private final QuestionRepository repository;
	private final FormRepository formRepository;

	private final QuestionServiceImpl self;

	public QuestionServiceImpl(
		QuestionMapper mapper,
		QuestionRepository repository,
		FormRepository formRepository)
	{
		this.mapper = mapper;
		this.repository = repository;
		this.formRepository = formRepository;
		self = this;
	}

	@Override
	@Transactional
	public HierarchicalQuestionDTO create(UUID formId, CreateOrUpdateQuestionDTO dto) {
		var form = formRepository.findById(formId).orElseThrow();
		var question = mapper.toQuestion(dto);
		form.addQuestion(question);

		String title = question.getTitle();
		if (title == null || title.isBlank()) self.initQuestion(form, question);

		var order = question.getOrder();
		var size = form.getQuestions().size();

		if (order == null || order > size) question.setOrder(size);
		else self.reorderQuestionsAfterInsertOrUpdate(form, question);

		self.performCreateAction(question.getType().getCreateAction(), question);

		question = repository.save(question);
		formRepository.save(form);
		return mapper.toHierarchicalQuestionDto(question);
	}

	@Override
	@Transactional
	public QuestionDTO createSubQuestion(UUID questionId, CreateOrUpdateQuestionDTO dto) {
		var question = repository.findById(questionId).orElseThrow(ResourceNotFoundException::new);
		var subQuestion = mapper.toQuestion(dto);
		question.addSubQuestion(subQuestion);

		var title = subQuestion.getTitle();
		if (title == null || title.isBlank()) self.initSubQuestion(question, subQuestion);

		var order = subQuestion.getOrder();
		var size = question.getSubQuestions().size();

		if (order == null || order > size) subQuestion.setOrder(size);
		else self.reorderSubQuestionsAfterInsertOrUpdate(question, subQuestion);

		subQuestion = repository.save(subQuestion);
		repository.save(question);
		return mapper.toQuestionDto(subQuestion);
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		var question = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
		if (question.getType() == QuestionType.GRID_SUBQUESTION) {
			var parent = question.getParent();
			parent.removeSubQuestion(question);
			self.reorderSubQuestionsAfterDelete(parent, question);
			repository.save(parent);
		} else {
			var form = question.getForm();
			form.removeQuestion(question);
			self.reorderQuestionsAfterDelete(form, question);
			formRepository.save(form);
		}
	}

	@Override
	@Transactional
	public void deleteAll(UUID formId) {
		var form = formRepository.findById(formId).orElseThrow(ResourceNotFoundException::new);
		form.getQuestions().forEach(form::removeQuestion);
	}

	@Override
	@Transactional
	public void deleteAllSubQuestions(UUID questionId) {
		var question = repository.findById(questionId).orElseThrow(ResourceNotFoundException::new);
		question.getSubQuestions().forEach(question::removeSubQuestion);
	}

	@Override
	@Transactional
	public HierarchicalQuestionDTO update(UUID id, CreateOrUpdateQuestionDTO dto) {
		var question = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
		self.performTypeChangeAction(question.getType().getChangeAction(dto.getType()), question);

		var oldOrder =  question.getOrder();
		mapper.updateQuestion(dto, question);

		if (!question.getOrder().equals(oldOrder)) {
			if (question.getType() == QuestionType.GRID_SUBQUESTION) {
				var parent = question.getParent();
				self.reorderSubQuestionsAfterInsertOrUpdate(parent, question);
			}
			else {
				var form = question.getForm();
				self.reorderQuestionsAfterInsertOrUpdate(form, question);
				question = repository.save(question);
				formRepository.save(form);
			}
		}

		return mapper.toHierarchicalQuestionDto(question);
	}

	@Transactional
	public void initQuestion(Form form, Question question) {
		var size = form.getQuestions().size();
		question.setTitle("Question " + size);
	}

	@Transactional void initSubQuestion(Question question, Question subQuestion) {
		var size = question.getSubQuestions().size();
		subQuestion.setTitle("Sub-question" + size);
	}

	@Transactional
	public void initAnswers(Question question) {
		for (int i = 1; i <= 3; i++) {
			var answer = new Answer();
			answer.setOrder(i);
			answer.setTitle("Answer " + i);
			question.addAnswer(answer);
		}
	}

	@Transactional
	public void initSubQuestions(Question question) {
		for (int i = 1; i <= 3; i++) {
			var subQuestion = new Question();
			subQuestion.setOrder(i);
			subQuestion.setTitle("Sub-question " + i);
			subQuestion.setType(QuestionType.GRID_SUBQUESTION);
			question.addSubQuestion(subQuestion);
		}
	}

	@Transactional
	public void performCreateAction(QuestionCreateAction action, Question question) {
		switch (action) {
			case ADD_ANSWERS:
				self.initAnswers(question);
				break;
			case ADD_ANSWERS_AND_SUBQUESTIONS:
				self.initAnswers(question);
				self.initSubQuestions(question);
				break;
			default:
				break;
		}
	}

	@Transactional
	public void performTypeChangeAction(QuestionTypeChangeAction action, Question question) {
		switch (action) {
			case FORBID:
				throw new ForbiddenTypeChangeException();
			case NONE:
				break;
			case ADD_ANSWERS:
				self.initAnswers(question);
				break;
			case ADD_ANSWERS_AND_SUBQUESTIONS:
				self.initAnswers(question);
				self.initSubQuestions(question);
				break;
			case ADD_SUBQUESTIONS:
				self.initSubQuestions(question);
				break;
			case REMOVE_ANSWERS:
				question.getAnswers().clear();
				break;
			case REMOVE_ANSWERS_AND_SUBQUESTIONS:
				question.getAnswers().clear();
				question.getSubQuestions().clear();
				break;
			case REMOVE_SUBQUESTIONS:
				question.getSubQuestions().clear();
				break;
		}
	}

	@Transactional
	public void normalizeQuestionOrder(Form form) {
		var prevOrder = 0;
		for (var q : form.getQuestions()) {
			var order = q.getOrder();
			var diff = order - prevOrder;
			if (diff > 1) q.setOrder(order - diff + 1);
			prevOrder = q.getOrder();
		}
	}

	@Transactional
	public void normalizeSubQuestionOrder(Question question) {
		var prevOrder = 0;
		for (var sq : question.getSubQuestions()) {
			var order = sq.getOrder();
			var diff = order - prevOrder;
			if (diff > 1) sq.setOrder(order - diff + 1);
			prevOrder = sq.getOrder();
		}
	}

	@Transactional
	public void reorderQuestionsAfterInsertOrUpdate(Form form, Question question) {
		form.getQuestions().forEach(q -> {
			if (!q.equals(question) && q.getOrder() >= question.getOrder())
				q.setOrder(q.getOrder() + 1);
		});

		self.normalizeQuestionOrder(form);
	}

	@Transactional
	public void reorderSubQuestionsAfterInsertOrUpdate(Question question, Question subQuestion) {
		question.getSubQuestions().forEach(sq -> {
			if (!sq.equals(subQuestion) && sq.getOrder() >= subQuestion.getOrder())
				sq.setOrder(sq.getOrder() + 1);
		});

		self.normalizeSubQuestionOrder(question);
	}

	@Transactional
	public void reorderQuestionsAfterDelete(Form form, Question question) {
		form.getQuestions().forEach(q -> {
			if (!q.equals(question) && q.getOrder() > question.getOrder())
				q.setOrder(q.getOrder() - 1);
		});
	}

	@Transactional
	public void reorderSubQuestionsAfterDelete(Question question, Question subQuestion) {
		question.getSubQuestions().forEach(sq -> {
			if (!sq.equals(subQuestion) && sq.getOrder() > subQuestion.getOrder())
				sq.setOrder(sq.getOrder() - 1);
		});
	}

}
