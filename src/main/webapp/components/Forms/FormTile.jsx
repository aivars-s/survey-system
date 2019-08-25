import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { deleteForm } from '../../store/actions/forms';

const FormTile = ({ form, deleteThis }) => (
  <div className="tile is-parent is-4">
    <article className="tile is-child box">
      <p className="title">{form.title}</p>
      <div className="field is-grouped">
        <p className="control">
          <Link className="button" to={`/editor/${form.id}`}>
            Edit
          </Link>
        </p>

        <p className="control">
          <Link className="button" to={`/survey/${form.id}`}>
            Preview
          </Link>
        </p>

        <p className="control">
          <button className="button is-danger" type="button" onClick={deleteThis}>
            Delete
          </button>
        </p>
      </div>
    </article>
  </div>
);

FormTile.propTypes = {
  form: PropTypes.shape.isRequired,
  deleteThis: PropTypes.func.isRequired,
};

const mapDispatchToProps = (dispatch, ownProps) => ({
  deleteThis: () => dispatch(deleteForm(ownProps.form.id)),
});

export default connect(
  null,
  mapDispatchToProps,
)(FormTile);
