package aivars.survey.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Forms")
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "questions" })
@ToString(callSuper = true, exclude = { "questions" })
public class Form extends BaseEntity {

	@Column(name = "Title", nullable = false)
	private String title;

	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

	public void addQuestion(Question question) {
		if (question == null) throw new IllegalArgumentException("Question cannot be null");
		if (question.getType() == QuestionType.GRID_SUBQUESTION) throw new IllegalArgumentException(
			"Question type cannot be " + QuestionType.GRID_SUBQUESTION
		);

		questions.add(question);
		question.setForm(this);
	}

	public void removeQuestion(Question question) {
		var removed = questions.remove(question);
		if (removed) question.setForm(null);
	}

}
