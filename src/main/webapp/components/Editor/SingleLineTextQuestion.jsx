import React from 'react';
import PropTypes from 'prop-types';

// eslint-disable-next-line no-unused-vars
const SingleLineTextQuestion = ({ question }) => (
  <div className="field">
    <div className="control">
      <input className="input" disabled />
    </div>
  </div>
);

SingleLineTextQuestion.propTypes = {
  question: PropTypes.shape.isRequired,
};

export default SingleLineTextQuestion;
