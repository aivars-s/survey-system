import { Question } from '../../api';

import {
  createQuestionError,
  createQuestionStart,
  deleteQuestionError,
  deleteQuestionStart,
  deleteQuestionSuccess,
  deleteAllQuestionsError,
  deleteAllQuestionsStart,
  updateQuestionError,
  updateQuestionStart,
  createSubQuestionError,
  createSubQuestionStart,
  deleteAllSubQuestionsError,
  deleteAllSubQuestionsStart,
} from './actionTypes';

import {
  addQuestion,
  removeQuestions,
  removeSubQuestions,
  removeAnswers,
  addSubQuestion,
} from './common';

export const createQuestion = (formId, question) => dispatch => {
  dispatch(createQuestionStart());
  Question.create(formId, question)
    .then(res => {
      const { data } = res;
      dispatch(addQuestion(data));
    })
    .catch(err => dispatch(createQuestionError(err)));
};

export const deleteQuestion = id => (dispatch, getState) => {
  dispatch(deleteQuestionStart());
  Question.remove(id)
    .then(() => {
      if (getState().questions.byParentId[id]) dispatch(removeSubQuestions(id));
      if (getState().answers.byQuestionId[id]) dispatch(removeAnswers(id));
      dispatch(deleteQuestionSuccess(id));
    })
    .catch(err => dispatch(deleteQuestionError(err)));
};

export const deleteAllQuestions = formId => dispatch => {
  dispatch(deleteAllQuestionsStart());
  Question.removeAll(formId)
    .then(() => dispatch(removeQuestions(formId)))
    .catch(err => dispatch(deleteAllQuestionsError(err)));
};

export const updateQuestion = (id, question) => dispatch => {
  dispatch(updateQuestionStart());
  Question.update(id, question)
    .then(res => {
      const { data } = res;
      dispatch(addQuestion(data));
    })
    .catch(err => dispatch(updateQuestionError(err)));
};

export const createSubQuestion = (questionId, subQuestion) => dispatch => {
  dispatch(createSubQuestionStart());
  Question.createSubQuestion(questionId, subQuestion)
    .then(res => {
      const { data } = res;
      dispatch(addSubQuestion(data));
    })
    .catch(err => dispatch(createSubQuestionError(err)));
};

export const deleteAllSubQuestions = questionId => dispatch => {
  dispatch(deleteAllSubQuestionsStart());
  Question.removeAllSubQuestions(questionId)
    .then(() => dispatch(removeSubQuestions(questionId)))
    .catch(err => dispatch(deleteAllSubQuestionsError(err)));
};
