import React from 'react';
import PropTypes from 'prop-types';

const GridSubQuestion = ({ order, title, ...rest }) => (
  <div className="field is-grouped">
    <p className="control">
      <p>{`${order}. ${title}`}</p>
    </p>
    {rest.children}
  </div>
);

GridSubQuestion.propTypes = {
  order: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
};

export default GridSubQuestion;
