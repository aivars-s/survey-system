import { Form } from '../../api';

import {
  createFormError,
  createFormStart,
  deleteFormError,
  deleteFormStart,
  deleteFormSuccess,
  fetchFormError,
  fetchFormStart,
  fetchAllFormsError,
  fetchAllFormsStart,
  fetchAllFormsSuccess,
  updateFormError,
  updateFormStart,
  updateFormSuccess,
} from './actionTypes';

import { addForm, addQuestions, removeQuestions } from './common';

export const createForm = form => dispatch => {
  dispatch(createFormStart());
  Form.create(form)
    .then(res => {
      const { data } = res;
      dispatch(addForm(data));
      const { questions } = data;
      if (questions && questions.length > 0) dispatch(addQuestions(data));
    })
    .catch(err => dispatch(createFormError(err)));
};

export const deleteForm = id => (dispatch, getState) => {
  dispatch(deleteFormStart());
  Form.remove(id)
    .then(() => {
      if (getState().questions.byFormId[id]) dispatch(removeQuestions(id));
      dispatch(deleteFormSuccess(id));
    })
    .catch(err => dispatch(deleteFormError(err)));
};

export const fetchForm = id => dispatch => {
  dispatch(fetchFormStart());
  Form.fetch(id)
    .then(res => {
      const { data } = res;
      dispatch(addForm(data));
    })
    .catch(err => dispatch(fetchFormError(err)));
};

export const fetchAllForms = () => dispatch => {
  dispatch(fetchAllFormsStart());
  Form.fetchAll()
    .then(res => {
      const { data } = res;
      dispatch(fetchAllFormsSuccess(data));
    })
    .catch(err => dispatch(fetchAllFormsError(err)));
};

export const updateForm = (id, form) => dispatch => {
  dispatch(updateFormStart());
  Form.update(id, form)
    .then(() => {
      dispatch(updateFormSuccess(form));
    })
    .catch(err => dispatch(updateFormError(err)));
};
