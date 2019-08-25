package aivars.survey.service.impl;

import aivars.survey.domain.Answer;
import aivars.survey.domain.Question;
import aivars.survey.dto.AnswerDTO;
import aivars.survey.dto.CreateOrUpdateAnswerDTO;
import aivars.survey.exception.ResourceNotFoundException;
import aivars.survey.mapper.AnswerMapper;
import aivars.survey.repository.AnswerRepository;
import aivars.survey.repository.QuestionRepository;
import aivars.survey.service.AnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {

	private final AnswerMapper mapper;
	private final AnswerRepository repository;
	private final QuestionRepository questionRepository;
	private final AnswerServiceImpl self;

	public AnswerServiceImpl(
		AnswerMapper mapper,
		AnswerRepository repository,
		QuestionRepository questionRepository
	) {
		this.mapper = mapper;
		this.repository = repository;
		this.questionRepository = questionRepository;
		self = this;
	}

	@Override
	@Transactional
	public AnswerDTO create(UUID questionId, CreateOrUpdateAnswerDTO dto) {
		var question = questionRepository.findById(questionId).orElseThrow(ResourceNotFoundException::new);
		var answer = mapper.toAnswer(dto);
		question.addAnswer(answer);

		var title = answer.getTitle();
		if (title == null || title.isBlank()) self.initAnswer(question, answer);

		var order = answer.getOrder();
		var size = question.getAnswers().size();

		if (order == null || order > size) answer.setOrder(size);
		else self.reorderAnswersAfterInsertOrUpdate(question, answer);

		answer = repository.save(answer);
		questionRepository.save(question);

		return mapper.toAnswerDto(answer);
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		var answer = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
		var question = answer.getQuestion();
		question.removeAnswer(answer);
		self.reorderAnswersAfterDelete(question, answer);
		questionRepository.save(question);
	}

	@Override
	@Transactional
	public void deleteAll(UUID questionId) {
		var question = questionRepository.findById(questionId).orElseThrow(ResourceNotFoundException::new);
		question.getAnswers().clear();
	}

	@Override
	@Transactional
	public void update(UUID id, CreateOrUpdateAnswerDTO dto) {
		var answer = repository.findById(id).orElseThrow(ResourceNotFoundException::new);

		var oldOrder = dto.getOrder();
		mapper.updateAnswer(dto, answer);

		if (!answer.getOrder().equals(oldOrder)) {
			var question = answer.getQuestion();
			self.reorderAnswersAfterInsertOrUpdate(question, answer);
			questionRepository.save(question);
		}
	}

	@Transactional
	public void initAnswer(Question question, Answer answer) {
		var size = question.getAnswers().size();
		answer.setTitle("Answer " + size);
	}

	@Transactional
	public void reorderAnswersAfterInsertOrUpdate(Question question, Answer answer) {
		question.getAnswers().forEach(a -> {
			var order = a.getOrder();
			if (!a.equals(answer) && order >= answer.getOrder()) a.setOrder(order + 1);
		});

		self.normalizeAnswerOrder(question);
	}

	@Transactional void reorderAnswersAfterDelete(Question question, Answer answer) {
		question.getAnswers().forEach(a -> {
			var order = a.getOrder();
			if (!a.equals(answer) && order > answer.getOrder()) a.setOrder(order - 1);
		});
	}

	@Transactional
	public void normalizeAnswerOrder(Question question) {
		var prevOrder = 0;
		for (var a : question.getAnswers()) {
			var order = a.getOrder();
			var diff = order - prevOrder;
			if (diff > 1) a.setOrder(order - diff + 1);
			prevOrder = a.getOrder();
		}
	}

}
