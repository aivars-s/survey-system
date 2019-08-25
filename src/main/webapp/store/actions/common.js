import {
  createAnswerSuccess,
  createFormSuccess,
  createQuestionSuccess,
  createSubQuestionSuccess,
  deleteAllAnswersSuccess,
  deleteAllQuestionsSuccess,
  deleteAllSubQuestionsSuccess,
} from './actionTypes';

export const addSubQuestion = sub => dispatch => {
  dispatch(
    createSubQuestionSuccess({
      id: sub.id,
      title: sub.title,
      order: sub.order,
      type: sub.type,
      parentId: sub.parentId,
    }),
  );
};

export const addSubQuestions = question => dispatch => {
  question.subQuestions.forEach(sq => dispatch(addSubQuestion(sq)));
};

export const addAnswer = answer => dispatch => {
  dispatch(
    createAnswerSuccess({
      id: answer.id,
      title: answer.title,
      order: answer.order,
      questionId: answer.questionId,
    }),
  );
};

export const addAnswers = question => dispatch => {
  question.answers.forEach(a => dispatch(addAnswer(a)));
};

export const addQuestion = question => dispatch => {
  dispatch(
    createQuestionSuccess({
      id: question.id,
      title: question.title,
      order: question.order,
      type: question.type,
      formId: question.formId,
    }),
  );
};

export const addQuestions = form => dispatch => {
  form.questions.forEach(q => {
    dispatch(addQuestion(q));
    if (q.subQuestions) dispatch(addSubQuestions(q));
    if (q.answers) dispatch(addAnswers(q));
  });
};

export const addForm = form => dispatch => {
  dispatch(createFormSuccess({ id: form.id, title: form.title }));
  if (form.questions) dispatch(addQuestions(form));
};

export const removeSubQuestions = questionId => (dispatch, getState) => {
  const subQuestionIds = getState().questions.byParentId[questionId];
  if (subQuestionIds) dispatch(deleteAllSubQuestionsSuccess(subQuestionIds));
};

export const removeAnswers = questionId => (dispatch, getState) => {
  const answerIds = getState().answers.byQuestionId[questionId];
  if (answerIds) dispatch(deleteAllAnswersSuccess(answerIds));
};

export const removeQuestions = formId => (dispatch, getState) => {
  const questionIds = getState().questions.byFormId[formId];
  if (questionIds) {
    questionIds.forEach(id => {
      dispatch(removeSubQuestions(id));
      dispatch(removeAnswers(id));
    });
  }
  dispatch(deleteAllQuestionsSuccess(questionIds));
};
