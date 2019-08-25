import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { currentFormHierarchy } from '../store/selectors';

import { setCurrentForm } from '../store/actions/actionTypes';
import { fetchForm } from '../store/actions/forms';

import Question from '../components/Editor/Question';

const Editor = ({ form, fetch, setCurrent, ...rest }) => {
  useEffect(() => {
    document.title = `Editor - ${form ? form.title : 'undefined'}`;
  }, [rest.location, form]);

  useEffect(() => {
    fetch();
  }, [rest.location]);

  useEffect(() => {
    setCurrent(rest.match.params.formId);
  }, [rest.location]);

  return (
    <div className="tile is-ancestor is-vertical">
      {form.questions.map(q => (
        <Question
          title={q.title}
          order={q.order}
          type={q.type}
          answers={q.answers}
          subQuestions={q.subQuestions}
          key={q.id}
        />
      ))}
    </div>
  );
};

Editor.propTypes = {
  form: PropTypes.shape.isRequired,
  fetch: PropTypes.func.isRequired,
  setCurrent: PropTypes.func.isRequired,
};

const mapStateToProps = state => ({
  form: currentFormHierarchy(state),
});

const mapDispatchToProps = (dispatch, ownProps) => ({
  fetch: () => dispatch(fetchForm(ownProps.match.params.formId)),
  setCurrent: () => dispatch(setCurrentForm(ownProps.match.params.formId)),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(Editor);
