import axios from 'axios';

export const apiRoot = 'http://localhost:8080/api';

export const init = () => {
  axios.defaults.baseURL = apiRoot;
};

export const Form = {
  create: form => axios.post('forms', form),
  fetch: id => axios.get(`/forms/${id}`),
  fetchAll: () => axios.get('/forms'),
  remove: id => axios.delete(`/forms/${id}`),
  update: (id, form) => axios.put(`/forms/${id}`, form),
};

export const Question = {
  create: (formId, question) => axios.post(`/forms/${formId}/questions`, question),
  createSubQuestion: (questionId, subQuestion) =>
    axios.post(`/questions/${questionId}/sub-questions`, subQuestion),
  remove: id => axios.delete(`/questions/${id}`),
  removeAll: formId => axios.delete(`/forms/${formId}/questions`),
  removeAllSubQuestions: questionId => axios.delete(`/questions/${questionId}/sub-questions`),
  update: (id, question) => axios.put(`/questions/${id}`, question),
};

export const Answer = {
  create: (questionId, answer) => axios.post(`/questions/${questionId}/answers`, answer),
  remove: id => axios.delete(`/answers/${id}`),
  removeAll: questionId => axios.delete(`/questions/${questionId}/answers`),
  update: (id, answer) => axios.put(`/answers/${id}`, answer),
};
