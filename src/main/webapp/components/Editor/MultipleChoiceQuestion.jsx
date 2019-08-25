import React from 'react';
import PropTypes from 'prop-types';

import MultipleChoiceAnswer from './MultipleChoiceAnswer';

const MultipleChoiceQuestion = ({ answers }) => (
  <>
    {answers.map(a => (
      <MultipleChoiceAnswer id={a.id} title={a.title} order={a.order} />
    ))}
  </>
);

MultipleChoiceQuestion.propTypes = {
  answers: PropTypes.shape.isRequired,
};

export default MultipleChoiceQuestion;
