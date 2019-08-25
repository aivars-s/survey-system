import { createReducer } from 'redux-starter-kit';

import {
  createQuestionError,
  createQuestionStart,
  createQuestionSuccess,
  deleteQuestionError,
  deleteQuestionStart,
  deleteQuestionSuccess,
  deleteAllQuestionsError,
  deleteAllQuestionsStart,
  deleteAllQuestionsSuccess,
  updateQuestionError,
  updateQuestionStart,
  updateQuestionSuccess,
  createSubQuestionError,
  createSubQuestionStart,
  createSubQuestionSuccess,
  deleteAllSubQuestionsError,
  deleteAllSubQuestionsStart,
  deleteAllSubQuestionsSuccess,
} from '../actions/actionTypes';

const initialState = {
  data: {},
  errors: {
    create: null,
    delete: null,
    deleteAll: null,
    update: null,
    createSub: null,
    deleteAllSubs: null,
  },
  loading: {
    create: false,
    delete: false,
    deleteAll: false,
    update: false,
    createSub: false,
    deleteAllSubs: false,
  },
  allIds: [],
  byFormId: {},
  byParentId: {},
};

/* eslint-disable no-param-reassign */
const addOrUpdateQuestion = (state, question) => {
  if (!question) return;
  const { id, formId, parentId } = question;
  let index = state.allIds.findIndex(questionId => questionId === id);
  if (index === -1) state.allIds.push(id);

  if (formId) {
    if (!state.byFormId[formId]) state.byFormId[formId] = [];
    index = state.byFormId[formId].findIndex(questionId => questionId === id);
    if (index === -1) state.byFormId[formId].push(id);
  }

  if (parentId) {
    if (!state.byParentId[parentId]) state.byParentId[parentId] = [];
    index = state.byParentId[parentId].findIndex(questionId => questionId === id);
    if (index === -1) state.byParentId[parentId].push(id);
  }

  state.data[id] = question;
};

const removeQuestion = (state, id) => {
  if (!id) return;
  let index = state.allIds.findIndex(questionId => questionId === id);
  if (index !== -1) {
    state.allIds.splice(index);
    const question = state.data[id];
    const { formId, parentId } = question;

    if (formId) {
      index = state.byFormId[formId].findIndex(questionId => questionId === id);
      if (index !== -1) state.byFormId[formId].splice(index);
    }

    if (parentId) {
      index = state.byParentId[parentId].findIndex(questionId => questionId === id);
      if (index !== -1) state.byParentId[parentId].splice(index);
    }

    delete state.data[id];
  }
};

const removeAllQuestions = (state, ids) => {
  if (!ids) return;
  ids.forEach(id => removeQuestion(state, id));
};

/* eslint no-unused-vars: 1 */
export default createReducer(initialState, {
  [createQuestionError]: (state, action) => {
    state.errors.create = action.payload;
    state.loading.create = false;
  },
  // eslint-disable-next-line no-unused-vars
  [createQuestionStart]: (state, action) => {
    state.errors.create = null;
    state.loading.create = true;
  },
  [createQuestionSuccess]: (state, action) => {
    addOrUpdateQuestion(state, action.payload);
    state.errors.create = null;
    state.loading.create = false;
  },
  [deleteQuestionError]: (state, action) => {
    state.errors.delete = action.payload;
    state.loading.delete = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteQuestionStart]: (state, action) => {
    state.errors.delete = null;
    state.loading.delete = true;
  },
  [deleteQuestionSuccess]: (state, action) => {
    removeQuestion(action.payload);
    state.errors.delete = null;
    state.loading.delete = false;
  },
  [deleteAllQuestionsError]: (state, action) => {
    state.errors.deleteAll = action.payload;
    state.loading.deleteAll = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteAllQuestionsStart]: (state, action) => {
    state.errors.deleteAll = null;
    state.loading.deleteAll = true;
  },
  [deleteAllQuestionsSuccess]: (state, action) => {
    removeAllQuestions(state, action.payload);
    state.errors.deleteAll = null;
    state.loading.deleteAll = false;
  },
  [updateQuestionError]: (state, action) => {
    state.errors.update = action.payload;
    state.loading.update = false;
  },
  // eslint-disable-next-line no-unused-vars
  [updateQuestionStart]: (state, action) => {
    state.errors.update = null;
    state.loading.update = true;
  },
  [updateQuestionSuccess]: (state, action) => {
    addOrUpdateQuestion(action.payload);
    state.errors.update = null;
    state.loading.update = false;
  },
  [createSubQuestionError]: (state, action) => {
    state.errors.createSub = action.payload;
    state.loading.createSub = false;
  },
  // eslint-disable-next-line no-unused-vars
  [createSubQuestionStart]: (state, action) => {
    state.errors.createSub = null;
    state.loading.createSub = true;
  },
  [createSubQuestionSuccess]: (state, action) => {
    addOrUpdateQuestion(state, action.payload);
    state.errors.createSub = null;
    state.loading.createSub = false;
  },
  [deleteAllSubQuestionsError]: (state, action) => {
    state.errors.deleteAllSubs = action.payload;
    state.loading.deleteAllSubs = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteAllSubQuestionsStart]: (state, action) => {
    state.errors.deleteAllSubs = null;
    state.loading.deleteAllSubs = true;
  },
  [deleteAllSubQuestionsSuccess]: (state, action) => {
    removeAllQuestions(state, action.payload);
    state.errors.deleteAllSubs = null;
    state.loading.deleteAllSubs = false;
  },
});
