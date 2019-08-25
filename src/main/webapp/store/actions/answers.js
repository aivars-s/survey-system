import { Answer } from '../../api';

import {
  createAnswerError,
  createAnswerStart,
  deleteAnswerError,
  deleteAnswerStart,
  deleteAnswerSuccess,
  deleteAllAnswersError,
  deleteAllAnswersStart,
  updateAnswerError,
  updateAnswerStart,
} from './actionTypes';

import { addAnswer, removeAnswers } from './common';

export const createAnswer = (questionId, answer) => dispatch => {
  dispatch(createAnswerStart());
  Answer.create(questionId, answer)
    .then(res => {
      const { data } = res;
      dispatch(addAnswer(data));
    })
    .catch(err => dispatch(createAnswerError(err)));
};

export const deleteAnswer = id => dispatch => {
  dispatch(deleteAnswerStart());
  Answer.remove(id)
    .then(() => dispatch(deleteAnswerSuccess(id)))
    .catch(err => dispatch(deleteAnswerError(err)));
};

export const deleteAllAnswers = questionId => dispatch => {
  dispatch(deleteAllAnswersStart());
  Answer.removeAll(questionId)
    .then(() => dispatch(removeAnswers(questionId)))
    .catch(err => dispatch(deleteAllAnswersError(err)));
};

export const updateAnswer = (id, answer) => dispatch => {
  dispatch(updateAnswerStart());
  Answer.update(id, answer)
    .then(() => dispatch(addAnswer(answer)))
    .catch(err => dispatch(updateAnswerError(err)));
};
