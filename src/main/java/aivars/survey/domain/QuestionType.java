package aivars.survey.domain;

public enum QuestionType {

	SINGLE_CHOICE(true, false),
	MULTIPLE_CHOICE(true, false),
	GRID_SINGLE_CHOICE(true, true),
	GRID_MULTIPLE_CHOICE(true, true),
	GRID_SUBQUESTION(false, false, true),
	SINGLE_LINE_TEXT(false, false),
	MULTIPLE_LINE_TEXT(false, false);

	private boolean canHaveAnswers;
	private boolean canHaveSubQuestions;
	private boolean unchangeable;

	QuestionType(boolean canHaveAnswers, boolean canHaveSubQuestions) {
		this.canHaveAnswers = canHaveAnswers;
		this.canHaveSubQuestions = canHaveSubQuestions;
	}

	QuestionType(boolean canHaveAnswers, boolean canHaveSubQuestions, boolean unchangeable) {
		this(canHaveAnswers, canHaveSubQuestions);
		this.unchangeable = unchangeable;
	}

	public boolean canHaveAnswers() {
		return canHaveAnswers;
	}

	public boolean canHaveSubQuestions() {
		return canHaveSubQuestions;
	}

	public boolean isUnchangeable() {
		return unchangeable;
	}

	public QuestionCreateAction getCreateAction() {
		if (canHaveAnswers) {
			if (canHaveSubQuestions) {
				return QuestionCreateAction.ADD_ANSWERS_AND_SUBQUESTIONS;
			}
			return QuestionCreateAction.ADD_ANSWERS;
		}
		return QuestionCreateAction.NONE;
	}

	public QuestionTypeChangeAction getChangeAction(QuestionType to) {
		if (this.unchangeable) return QuestionTypeChangeAction.FORBID;
		if (this == to) return QuestionTypeChangeAction.NONE;

		byte coeff = 0;

		if (canHaveAnswers && !to.canHaveAnswers) coeff = -1;
		else if (!canHaveAnswers && to.canHaveAnswers) coeff = 1;

		if (canHaveSubQuestions && !to.canHaveSubQuestions) coeff += 3;
		else if (!canHaveSubQuestions && to.canHaveSubQuestions) coeff -=3;

		switch (coeff) {
			case -4: return QuestionTypeChangeAction.REMOVE_ANSWERS_AND_SUBQUESTIONS;
			case -2: return QuestionTypeChangeAction.REMOVE_SUBQUESTIONS;
			case -1: return QuestionTypeChangeAction.REMOVE_ANSWERS;
			case 1: return QuestionTypeChangeAction.ADD_ANSWERS;
			case 2: return QuestionTypeChangeAction.ADD_SUBQUESTIONS;
			case 4: return QuestionTypeChangeAction.ADD_ANSWERS_AND_SUBQUESTIONS;
			default: return QuestionTypeChangeAction.NONE;
		}
	}

}
