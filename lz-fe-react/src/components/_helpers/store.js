import {createStore, applyMiddleware} from 'redux';
import thunkMiddleware from 'redux-thunk';
import {createLogger} from 'redux-logger';
import rootReducer from '../_reducers';

const loggerMiddleware = createLogger();

export const store = createStore(
    rootReducer,
    applyMiddleware(
        thunkMiddleware, // 异步中间件
        loggerMiddleware // 日志中间件
    )
);
