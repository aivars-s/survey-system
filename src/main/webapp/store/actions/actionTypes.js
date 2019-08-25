import { createAction } from 'redux-starter-kit';

// Answers
export const createAnswerError = createAction('answer/create-error');
export const createAnswerStart = createAction('answer/create-start');
export const createAnswerSuccess = createAction('answer/create-success');

export const deleteAnswerError = createAction('answer/delete-error');
export const deleteAnswerStart = createAction('answer/delete-start');
export const deleteAnswerSuccess = createAction('answer/delete-success');

export const deleteAllAnswersError = createAction('answer/delete-all-error');
export const deleteAllAnswersStart = createAction('answer/delete-all-start');
export const deleteAllAnswersSuccess = createAction('answer/delete-all-success');

export const updateAnswerError = createAction('answer/update-error');
export const updateAnswerStart = createAction('answer/update-start');
export const updateAnswerSuccess = createAction('answer/update-success');

// Forms
export const createFormError = createAction('form/create-error');
export const createFormStart = createAction('form/create-start');
export const createFormSuccess = createAction('form/create-success');

export const deleteFormError = createAction('form/delete-error');
export const deleteFormStart = createAction('form/delete-start');
export const deleteFormSuccess = createAction('form/delete-success');

export const fetchFormError = createAction('form/fetch-error');
export const fetchFormStart = createAction('form/fetch-start');
export const fetchFormSuccess = createAction('form/fetch-success');

export const fetchAllFormsError = createAction('form/fetch-error');
export const fetchAllFormsStart = createAction('form/fetch-start');
export const fetchAllFormsSuccess = createAction('form/fetch-success');

export const updateFormError = createAction('form/update-error');
export const updateFormStart = createAction('form/update-start');
export const updateFormSuccess = createAction('form/update-success');

export const setCurrentForm = createAction('form/set-current');

// Questions
export const createQuestionError = createAction('question/create-error');
export const createQuestionStart = createAction('question/create-start');
export const createQuestionSuccess = createAction('question/create-success');

export const deleteQuestionError = createAction('question/delete-error');
export const deleteQuestionStart = createAction('question/delete-start');
export const deleteQuestionSuccess = createAction('question/delete-success');

export const deleteAllQuestionsError = createAction('question/delete-all-error');
export const deleteAllQuestionsStart = createAction('question/delete-all-start');
export const deleteAllQuestionsSuccess = createAction('question/delete-all-success');

export const updateQuestionError = createAction('question/update-error');
export const updateQuestionStart = createAction('question/update-start');
export const updateQuestionSuccess = createAction('question/update-success');

// Sub-questions
export const createSubQuestionError = createAction('sub-question/create-error');
export const createSubQuestionStart = createAction('sub-question/create-start');
export const createSubQuestionSuccess = createAction('sub-question/create-success');

export const deleteAllSubQuestionsError = createAction('sub-question/delete-all-error');
export const deleteAllSubQuestionsStart = createAction('sub-question/delete-all-start');
export const deleteAllSubQuestionsSuccess = createAction('sub-question/delete-all-success');
