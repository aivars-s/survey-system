import React from 'react';
import PropTypes from 'prop-types';

import GridSubQuestion from './GridSubQuestion';
import SingleChoiceAnswer from './SingleChoiceAnswer';

// eslint-disable-next-line no-unused-vars
const SingleChoiceGridQuestion = ({ answers, subQuestions }) => (
  <>
    {subQuestions.map(sub => (
      <GridSubQuestion order={sub.order} title={sub.title} key={sub.id}>
        {answers.map(a => (
          <SingleChoiceAnswer id={a.id} title={a.title} order={a.order} key={a.id} />
        ))}
      </GridSubQuestion>
    ))}
  </>
);

SingleChoiceGridQuestion.propTypes = {
  answers: PropTypes.arrayOf(PropTypes.shape).isRequired,
  subQuestions: PropTypes.arrayOf(PropTypes.shape).isRequired,
};

export default SingleChoiceGridQuestion;
