import React from 'react';
import {render} from 'react-dom';
import App from './colorapp/App'

const target = document.getElementById('react-container');

window.React = React;

render(
    <App/>,
    target
);

if (module.hot) {
    module.hot.accept();
}
