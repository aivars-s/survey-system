import React from 'react';
import PropTypes from 'prop-types';

import SingleChoiceQuestion from './SingleChoiceQuestion';
import MultipleChoiceQuestion from './MultipleChoiceQuestion';
import SingleChoiceGridQuestion from './SingleChoiceGridQuestion';
import MultipleChoiceGridQuestion from './MultipleChoiceGridQuestion';
import SingleLineTextQuestion from './SingleLineTextQuestion';
import MultiLineTextQuestion from './MultiLineTextQuestion';

const Question = ({ title, order, type, answers, subQuestions }) => {
  let content;

  switch (type) {
    case 'SINGLE_CHOICE':
      content = <SingleChoiceQuestion answers={answers} />;
      break;
    case 'MULTIPLE_CHOICE':
      content = <MultipleChoiceQuestion answers={answers} />;
      break;
    case 'GRID_SINGLE_CHOICE':
      content = <SingleChoiceGridQuestion answers={answers} subQuestions={subQuestions} />;
      break;
    case 'GRID_MULTIPLE_CHOICE':
      content = <MultipleChoiceGridQuestion answers={answers} subQuestions={subQuestions} />;
      break;
    case 'SINGLE_LINE_TEXT':
      content = <SingleLineTextQuestion />;
      break;
    case 'MULTIPLE_LINE_TEXT':
      content = <MultiLineTextQuestion />;
      break;
    default:
      content = <p className="title is-danger">ERROR</p>;
      break;
  }

  return (
    <div className="tile is-parent is-12">
      <div className="tile is-child">
        <p className="subtitle">{`${order}. ${title}`}</p>
        <br />
        {content}
      </div>
    </div>
  );
};

Question.propTypes = {
  title: PropTypes.string.isRequired,
  order: PropTypes.number.isRequired,
  type: PropTypes.string.isRequired,
  answers: PropTypes.arrayOf(PropTypes.shape),
  subQuestions: PropTypes.arrayOf(PropTypes.shape),
};

Question.defaultProps = {
  answers: [],
  subQuestions: [],
};

export default Question;
