import React from 'react';
import ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import 'typeface-roboto';
import {Provider} from 'react-redux';


import {fakeBackend} from './components/_helpers';
import {BrowserRouter, Route} from 'react-router-dom';
import LoginPage from "./views/LoginPage/LoginPage";
import {store} from "./components/_helpers";

import './index.css';

fakeBackend();

// render(
//     <Provider store={store}>
//         <App/>
//     </Provider>,
//     document.querySelector('#app'));

ReactDOM.render(
    <BrowserRouter>
        <Route path="/login" component={LoginPage}/>
    </BrowserRouter>,
    document.querySelector('#app')
);

document.title = 'CMS';

registerServiceWorker();
