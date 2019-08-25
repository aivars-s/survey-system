import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { configureStore } from 'redux-starter-kit';
import { HashRouter } from 'react-router-dom';

import 'bulma/css/bulma.css';

import { init } from './api';

import answerReducer from './store/reducers/answer';
import formReducer from './store/reducers/form';
import questionReducer from './store/reducers/question';

import App from './App';

init();

const store = configureStore({
  reducer: {
    answers: answerReducer,
    forms: formReducer,
    questions: questionReducer,
  },
});

const root = (
  <Provider store={store}>
    <HashRouter>
      <App />
    </HashRouter>
  </Provider>
);

ReactDOM.render(root, document.getElementById('app'));
