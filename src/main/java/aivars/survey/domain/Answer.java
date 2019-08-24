package aivars.survey.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Answers")
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "question" })
@ToString(callSuper = true, exclude = { "question" })
public class Answer extends BaseEntity {

	@Column(name = "Title", nullable = false)
	private String title;

	@Column(name = "AnswerOrder", nullable = false)
	private Integer order;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "QuestionId", nullable = false, updatable = false)
	private Question question;

}
