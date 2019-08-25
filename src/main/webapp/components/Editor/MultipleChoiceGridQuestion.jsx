import React from 'react';
import PropTypes from 'prop-types';

import GridSubQuestion from './GridSubQuestion';
import MultipleChoiceAnswer from './MultipleChoiceAnswer';

// eslint-disable-next-line no-unused-vars
const MultipleChoiceGridQuestion = ({ answers, subQuestions }) => (
  <>
    {subQuestions.map(sub => (
      <GridSubQuestion order={sub.order} title={sub.title} key={sub.id}>
        {answers.map(a => (
          <MultipleChoiceAnswer id={a.id} title={a.title} order={a.order} key={a.id} />
        ))}
      </GridSubQuestion>
    ))}
  </>
);

MultipleChoiceGridQuestion.propTypes = {
  answers: PropTypes.arrayOf(PropTypes.shape).isRequired,
  subQuestions: PropTypes.arrayOf(PropTypes.shape).isRequired,
};

export default MultipleChoiceGridQuestion;
