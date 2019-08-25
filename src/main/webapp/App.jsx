import React from 'react';

import { Redirect, Route, Switch } from 'react-router';

import Editor from './containers/Editor';
import Forms from './containers/Forms';
import Survey from './containers/Survey';

const App = () => (
  <Switch>
    <Route path="/editor/:formId" exact component={Editor} />
    <Route path="/forms" exact component={Forms} />
    <Route path="/survey/:formId" exact component={Survey} />
    <Redirect from="/" to="/forms" />
  </Switch>
);

export default App;
