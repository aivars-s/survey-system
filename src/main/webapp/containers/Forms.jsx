import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { allForms } from '../store/selectors';
import { fetchAllForms } from '../store/actions/forms';

import CreateForm from '../components/Forms/CreateForm';
import FormTileRow from '../components/Forms/FormTileRow';

const Forms = ({ forms, fetchAll, ...rest }) => {
  useEffect(() => {
    document.title = 'Forms';
  }, [rest.location]);

  useEffect(() => {
    fetchAll();
  }, [rest.location]);

  const rows = [];
  for (let i = 0; i < forms.length; i += 3)
    rows.push(forms.slice(i, i + 3 < forms.length ? i + 3 : forms.length));

  return (
    <div className="tile is-ancestor is-vertical is-centered">
      <CreateForm />
      {rows.map(row => (
        <FormTileRow forms={row} />
      ))}
    </div>
  );
};

Forms.propTypes = {
  forms: PropTypes.arrayOf(PropTypes.shape).isRequired,
  fetchAll: PropTypes.func.isRequired,
};

const mapStateToProps = state => ({
  forms: allForms(state),
});

const mapDispatchToProps = dispatch => ({
  fetchAll: () => dispatch(fetchAllForms()),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(Forms);
