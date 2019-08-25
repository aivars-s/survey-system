import { createReducer } from 'redux-starter-kit';

import {
  createFormError,
  createFormStart,
  createFormSuccess,
  deleteFormError,
  deleteFormStart,
  deleteFormSuccess,
  fetchFormError,
  fetchFormStart,
  fetchFormSuccess,
  fetchAllFormsError,
  fetchAllFormsStart,
  fetchAllFormsSuccess,
  updateFormError,
  updateFormStart,
  updateFormSuccess,
  setCurrentForm,
} from '../actions/actionTypes';

const initialState = {
  data: {},
  errors: {
    create: null,
    delete: null,
    fetch: null,
    fetchAll: null,
    update: null,
  },
  loading: {
    create: false,
    delete: false,
    fetch: false,
    fetchAll: false,
    update: false,
  },
  allIds: [],
  currentId: null,
};

/* eslint-disable no-param-reassign */
const addOrUpdateForm = (state, form) => {
  if (!form) return;
  const { id } = form;
  const index = state.allIds.findIndex(formId => formId === id);
  if (index === -1) state.allIds.push(id);
  state.data[id] = form;
};

const addOrUpdateAllForms = (state, forms) => {
  if (!forms) return;
  forms.forEach(form => addOrUpdateForm(state, form));
};

const removeForm = (state, id) => {
  if (!id) return;
  const index = state.allIds.findIndex(formId => formId === id);
  if (index !== -1) {
    state.allIds.splice(index);
    delete state.data[id];
  }
};

export default createReducer(initialState, {
  [createFormError]: (state, action) => {
    state.errors.create = action.payload;
    state.loading.create = false;
  },
  // eslint-disable-next-line no-unused-vars
  [createFormStart]: (state, action) => {
    state.errors.create = null;
    state.loading.create = true;
  },
  [createFormSuccess]: (state, action) => {
    addOrUpdateForm(state, action.payload);
    state.errors.create = null;
    state.loading.create = false;
  },
  [deleteFormError]: (state, action) => {
    state.errors.delete = action.payload;
    state.loading.delete = false;
  },
  // eslint-disable-next-line no-unused-vars
  [deleteFormStart]: (state, action) => {
    state.errors.delete = null;
    state.loading.delete = true;
  },
  [deleteFormSuccess]: (state, action) => {
    removeForm(state, action.payload);
    state.errors.delete = null;
    state.loading.delete = false;
  },
  [fetchFormError]: (state, action) => {
    state.errors.fetch = action.payload;
    state.loading.fetch = false;
  },
  // eslint-disable-next-line no-unused-vars
  [fetchFormStart]: (state, action) => {
    state.errors.fetch = null;
    state.loading.fetch = true;
  },
  [fetchFormSuccess]: (state, action) => {
    addOrUpdateForm(state, action.payload);
    state.errors.fetch = null;
    state.loading.fetch = false;
  },
  [fetchAllFormsError]: (state, action) => {
    state.errors.fetchAll = action.payload;
    state.loading.fetchAll = false;
  },
  // eslint-disable-next-line no-unused-vars
  [fetchAllFormsStart]: (state, action) => {
    state.errors.fetchAll = null;
    state.loading.fetchAll = true;
  },
  [fetchAllFormsSuccess]: (state, action) => {
    addOrUpdateAllForms(state, action.payload);
    state.errors.fetchAll = null;
    state.loading.fetchAll = false;
  },
  [updateFormError]: (state, action) => {
    state.errors.update = action.payload;
    state.loading.update = false;
  },
  // eslint-disable-next-line no-unused-vars
  [updateFormStart]: (state, action) => {
    state.errors.update = null;
    state.loading.update = true;
  },
  [updateFormSuccess]: (state, action) => {
    addOrUpdateForm(state, action.payload);
    state.errors.update = null;
    state.loading.update = false;
  },
  [setCurrentForm]: (state, action) => {
    state.currentId = action.payload;
  },
});
