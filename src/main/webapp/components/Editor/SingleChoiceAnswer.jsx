import React from 'react';
import PropTypes from 'prop-types';

const SingleChoiceAnswer = ({ id, title, order }) => (
  <div className="control">
    <label className="radio" htmlFor={`${id}`}>
      <input type="radio" disabled id={`${id}`} />
      {`${order}. ${title}`}
    </label>
  </div>
);

SingleChoiceAnswer.propTypes = {
  id: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  order: PropTypes.number.isRequired,
};

export default SingleChoiceAnswer;
