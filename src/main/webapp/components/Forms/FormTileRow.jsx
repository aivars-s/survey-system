import React from 'react';
import PropTypes from 'prop-types';

import FormTile from './FormTile';

const FormTileRow = ({ forms }) => (
  <div className="tile">
    {forms.map(form => (
      <FormTile form={form} key={form.id} />
    ))}
  </div>
);

FormTileRow.propTypes = {
  forms: PropTypes.arrayOf(PropTypes.shape).isRequired,
};

export default FormTileRow;
