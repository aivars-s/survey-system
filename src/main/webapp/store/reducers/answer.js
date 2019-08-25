import { createReducer } from 'redux-starter-kit';

import {
  createAnswerError,
  createAnswerStart,
  createAnswerSuccess,
  deleteAnswerError,
  deleteAnswerStart,
  deleteAnswerSuccess,
  deleteAllAnswersError,
  deleteAllAnswersStart,
  deleteAllAnswersSuccess,
  updateAnswerError,
  updateAnswerStart,
  updateAnswerSuccess,
} from '../actions/actionTypes';

const initialState = {
  data: {},
  errors: {
    create: null,
    delete: null,
    deleteAll: null,
    update: null,
  },
  loading: {
    create: false,
    delete: false,
    deleteAll: false,
    update: false,
  },
  allIds: [],
  byQuestionId: {},
};

/* eslint-disable no-param-reassign */
/* eslint no-unused-vars: 1 */
const addOrUpdateAnswer = (state, answer) => {
  if (!answer) return;
  const { id, questionId } = answer;
  let index = state.allIds.findIndex(answerId => answerId === id);
  if (index === -1) state.allIds.push(id);

  if (questionId) {
    if (!state.byQuestionId[questionId]) state.byQuestionId[questionId] = [];
    index = state.byQuestionId[questionId].findIndex(answerId => answerId === id);
    if (index === -1) state.byQuestionId[questionId].push(id);
  }

  state.data[id] = answer;
};

const removeAnswer = (state, id) => {
  if (!id) return;
  let index = state.allIds.findIndex(answerId => answerId === id);
  if (index !== -1) {
    state.allIds.splice(index);
    const answer = state.data[id];
    const { questionId } = answer;

    if (questionId) {
      index = state.byQuestionId.findIndex(answerId => answerId === id);
      if (index !== -1) state.byQuestionId[questionId].splice(index);
    }

    delete state.data[id];
  }
};

const removeAllAnswers = (state, ids) => {
  if (!ids) return;
  ids.forEach(id => removeAnswer(id));
};

export default createReducer(initialState, {
  [createAnswerError]: (state, action) => {
    state.errors.create = action.payload;
    state.loading.create = false;
  },
  // eslint-disable-next-line no-unused-vars
  [createAnswerStart]: (state, action) => {
    state.errors.create = null;
    state.loading.create = true;
  },
  [createAnswerSuccess]: (state, action) => {
    addOrUpdateAnswer(state, action.payload);
    state.errors.create = null;
    state.loading.create = false;
  },
  [deleteAnswerError]: (state, action) => {
    state.errors.delete = action.payload;
    state.loading.delete = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteAnswerStart]: (state, action) => {
    state.errors.delete = null;
    state.loading.delete = true;
  },
  [deleteAnswerSuccess]: (state, action) => {
    removeAnswer(state, action.payload);
    state.errors.delete = null;
    state.loading.delete = false;
  },
  [deleteAllAnswersError]: (state, action) => {
    state.errors.deleteAll = action.payload;
    state.loading.deleteAll = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteAllAnswersStart]: (state, action) => {
    state.errors.deleteAll = null;
    state.loading.deleteAll = true;
  },
  [deleteAllAnswersSuccess]: (state, action) => {
    removeAllAnswers(state, action.payload);
    state.errors.deleteAll = null;
    state.loading.deleteAll = false;
  },
  [updateAnswerError]: (state, action) => {
    state.errors.update = action.payload;
    state.loading.update = false;
  },
  // eslint-disable-next-line no-unused-vars
  [updateAnswerStart]: (state, action) => {
    state.errors.update = null;
    state.loading.update = true;
  },
  [updateAnswerSuccess]: (state, action) => {
    addOrUpdateAnswer(state, action.payload);
    state.errors.update = null;
    state.loading.update = false;
  },
});
