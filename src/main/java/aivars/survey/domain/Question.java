package aivars.survey.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Questions")
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "form", "parent", "answers", "subQuestions" })
@ToString(callSuper = true, exclude = { "form", "parent", "answers", "subQuestions" })
public class Question extends BaseEntity {

	@Column(name = "Title", nullable = false)
	private String title;

	@Column(name = "QuestionOrder", nullable = false)
	private Integer order;

	@Enumerated(EnumType.STRING)
	@Column(name = "Type", nullable = false)
	private QuestionType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FormId", updatable = false)
	private Form form;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ParentId", updatable = false)
	private Question parent;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> subQuestions = new ArrayList<>();

	public void addAnswer(Answer answer) {
		if (answer == null) throw new IllegalArgumentException("Answer must not be null");
		if (!getType().canHaveAnswers()) throw new IllegalStateException(
			String.format("Question type %s cannot have answers", getType()));

		answers.add(answer);
		answer.setQuestion(this);
	}

	public void addSubQuestion(Question subQuestion) {
		if (subQuestion == null) throw new IllegalArgumentException("Sub-question must not be null");

		if (subQuestion.getType() != QuestionType.GRID_SUBQUESTION) throw new IllegalArgumentException(
			String.format("Question type %s is not a sub-question", subQuestion.getType()));

		if (!getType().canHaveSubQuestions()) throw new IllegalStateException(
			String.format("Question type %s cannot have sub-questions", getType()));

		subQuestions.add(subQuestion);
		subQuestion.setParent(this);
	}

	public void removeAnswer(Answer answer) {
		var removed = answers.remove(answer);
		if (removed) answer.setQuestion(this);
	}

	public void removeSubQuestion(Question subQuestion) {
		var removed = subQuestions.remove(subQuestion);
		if (removed) subQuestion.setParent(this);
	}

}
