package aivars.survey.repository;

import aivars.survey.domain.Answer;
import aivars.survey.domain.Form;
import aivars.survey.domain.Question;
import aivars.survey.domain.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Disabled
public class FormRepositoryIT {

	@Autowired
	private FormRepository repository;

	@BeforeEach
	@Rollback(false)
	public void init() {
		// given
		var form = new Form();
		form.setTitle("Test Form");

		for (int i = 1; i <= 10; i++) {
			var question = new Question();
			question.setTitle("Question " + i);
			question.setOrder(i);
			question.setType(QuestionType.GRID_SINGLE_CHOICE);

			for (int j = 1; j <= 10; j++) {
				var subquestion = new Question();
				subquestion.setTitle("Subquestion 1");
				subquestion.setOrder(j);
				subquestion.setType(QuestionType.GRID_SUBQUESTION);
				question.addSubQuestion(subquestion);
			}

			for (int j = 1; j <= 10; j++) {
				var answer = new Answer();
				answer.setTitle("Answer " + j);
				answer.setOrder(j);
				question.addAnswer(answer);
			}

			form.addQuestion(question);
		}

		repository.save(form);
	}

	@Test
	public void formIsSavedCorrectly() {
		// when
		var form = repository.findAll().get(0);

		// then
		assertEquals(10, form.getQuestions().size());
		for (var question : form.getQuestions()) {
			assertEquals(form, question.getForm());
			assertEquals(10, question.getAnswers().size());
			assertEquals(10, question.getSubQuestions().size());

			for (var answer : question.getAnswers()) {
				assertEquals(question, answer.getQuestion());
			}

			for (var subquestion : question.getSubQuestions()) {
				assertEquals(question, subquestion.getParent());
			}
		}
	}

}
