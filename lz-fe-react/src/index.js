import React from 'react';
import {render, unmountComponentAtNode} from 'react-dom';
import Clock from './clock/Clock'

const target = document.getElementById('react-container');

window.React = React;

render(
    <Clock onClose={() => unmountComponentAtNode(target)}/>,
    target
);

if (module.hot) {
    module.hot.accept();
}
