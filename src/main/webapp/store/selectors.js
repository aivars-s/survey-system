import { createSelector } from 'redux-starter-kit';
import { objectsToArray } from '../util';

// export const allForms = createSelector(
//   ['forms.data'],
//   data =>
//     Object.keys(data).reduce((array, key) => {
//       array.push(data[key]);
//       return array;
//     }, []),
// );

export const allForms = state => objectsToArray(state.forms.data);

// export const allQuestionsIncludingSubQuestions = createSelector(
//   ['questions.data'],
//   data =>
//     Object.keys(data).reduce((array, key) => {
//       array.push(data[key]);
//       return array;
//     }, []),
// );

export const allQuestionsIncludingSubQuestions = state => objectsToArray(state.questions.data);

export const allQuestions = createSelector(
  [allQuestionsIncludingSubQuestions],
  questions => questions.filter(q => !q.parentId),
);

export const allSubQuestions = createSelector(
  [allQuestionsIncludingSubQuestions],
  questions => questions.filter(q => q.parentId),
);

export const allAnswers = createSelector(
  ['answers.data'],
  data =>
    Object.keys(data).reduce((array, key) => {
      array.push(data[key]);
      return array;
    }, []),
);

export const currentFormQuestions = createSelector(
  [allQuestions, 'forms.currentId'],
  (questions, currentId) =>
    questions.filter(q => q.formId === currentId).sort((a, b) => a.order - b.order),
);

export const currentFormFullQuestions = createSelector(
  [currentFormQuestions, allSubQuestions, allAnswers],
  (questions, subs, answers) => {
    const result = [];
    questions.forEach(q => {
      const q2 = { ...q };
      q2.subQuestions = subs.filter(sq => sq.parentId === q.id);
      q2.answers = answers.filter(a => a.questionId === q.id);
      result.push(q2);
    });
    return result;
  },
);

export const currentFormHierarchy = createSelector(
  ['forms.data', 'forms.currentId', currentFormFullQuestions],
  (data, id, questions) => {
    const form = { ...data[id] };
    form.questions = questions;
    return form;
  },
);
