import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { createForm } from '../../store/actions/forms';

const CreateForm = ({ newForm }) => {
  const [title, setTitle] = useState('');

  return (
    <div className="tile is-parent">
      <div className="tile is-child box">
        <form>
          <p className="title">New form</p>
          <div className="field is-grouped">
            <p className="control is-expanded">
              <input
                className="input"
                placeholder="Title"
                value={title}
                onChange={e => setTitle(e.target.value)}
              />
            </p>
            <p className="control">
              <button className="button is-primary" type="button" onClick={() => newForm(title)}>
                Create
              </button>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

CreateForm.propTypes = {
  newForm: PropTypes.func.isRequired,
};

const mapDispatchToProps = dispatch => ({
  newForm: title => dispatch(createForm({ title })),
});

export default connect(
  null,
  mapDispatchToProps,
)(CreateForm);
