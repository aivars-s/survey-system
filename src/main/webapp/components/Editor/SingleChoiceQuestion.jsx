import React from 'react';
import PropTypes from 'prop-types';

import SingleChoiceAnswer from './SingleChoiceAnswer';

const SingleChoiceQuestion = ({ answers }) => (
  <div className="field">
    {answers.map(a => (
      <SingleChoiceAnswer id={a.id} title={a.title} order={a.order} />
    ))}
  </div>
);

SingleChoiceQuestion.propTypes = {
  answers: PropTypes.arrayOf(PropTypes.shape).isRequired,
};

export default SingleChoiceQuestion;
