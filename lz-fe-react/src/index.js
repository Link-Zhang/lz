import React from 'react';
import ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import 'typeface-roboto';
import {Provider} from 'react-redux';

import {fakeBackend} from './components/_helpers';
import {BrowserRouter, Route} from 'react-router-dom';
import LoginPage from "./views/LoginPage/LoginPage";
import CardHeader from './components/Card/CardHeader'

import {createStore} from 'redux'

import './index.css';
import {authentication} from "./components/_reducers/authentication.reducer";

fakeBackend();

const login = (state = {authentication: false}, action) => {
    const authentication = state.authentication;
    switch (action.type) {
        case 'login':
            return {authentication: !authentication};
        default:
            return state;
    }
};

const store = createStore(login);

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <Route path="/" component={LoginPage}/>
        </BrowserRouter>
    </Provider>
    ,
    document.querySelector('#app')
);

document.title = 'CMS';

registerServiceWorker();
