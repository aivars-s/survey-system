import React from 'react';
import PropTypes from 'prop-types';

const MultipleChoiceAnswer = ({ id, title, order }) => (
  <div className="control">
    <label className="checkbox" htmlFor={`${id}`}>
      <input type="checkbox" disabled id={`${id}`} />
      {`${order}. ${title}`}
    </label>
  </div>
);

MultipleChoiceAnswer.propTypes = {
  id: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  order: PropTypes.number.isRequired,
};

export default MultipleChoiceAnswer;
