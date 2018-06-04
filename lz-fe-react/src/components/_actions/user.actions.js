import {userConstants} from '../_constants';
import {userService} from '../_services';
import {alertActions} from './alert.actions';
import {history} from '../_helpers';

const login = (username, password) => {
    const request = (user) => (
        {type: userConstants.LOGIN_REQUEST, user}
    );

    const success = (user) => (
        {type: userConstants.LOGIN_SUCCESS, user}
    );

    const failure = (error) => (
        {type: userConstants.LOGIN_FAILURE, error}
    );

    return dispatch => {
        dispatch(request({username}));
        userService.login(username, password).then(
            user => {
                dispatch(success(user))
                // '/' is the base url
                history.push('/');
            },
            error => {
                dispatch(failure(error));
                dispatch(alertActions.error(error));
            }
        );
    };
};

const logout = () => {
    userService.logout();
    return {type: userConstants.LOGOUT};
};

const register = (user) => {
    const request = (user) => (
        {type: userConstants.REGISTER_REQUEST, user}
    );

    const success = (user) => (
        {type: userConstants.REGISTER_SUCCESS, user}
    );

    const failure = (error) => (
        {type: userConstants.REGISTER_FAILURE, error}
    );

    return dispatch => {
        dispatch(request(user));
        userService.register(user).then(
            user => {
                dispatch(success());
                history.push('/login');
                dispatch(alertActions.success('Registration successful'));
            },
            error => {
                dispatch(failure(error));
                dispatch(alertActions.error(error));
            }
        );
    };
};

const retrieveAll = () => {
    const request = () => (
        {type: userConstants.RETRIEVE_ALL_REQUEST}
    );

    const success = (users) => (
        {type: userConstants.RETRIEVE_ALL_SUCCESS, users}
    );

    const failure = (error) => (
        {type: userConstants.RETRIEVE_ALL_FAILURE, error}
    );

    return dispatch => {
        dispatch(request());
        userService.retrieveAll().then(
            users => dispatch(success(users)),
            error => dispatch(failure(error))
        );
    };
};

// prefixed function name with underscore because delete is a reserved word in javascript
const _delete = (id) => {
    const request = (id) => (
        {type: userConstants.DELETE_REQUEST, id}
    );

    const success = (id) => (
        {type: userConstants.DELETE_SUCCESS, id}
    );

    const failure = (id, error) => (
        {type: userConstants.DELETE_FAILURE, id, error}
    );

    return dispatch => {
        dispatch(request(id));
        userService.delete(id).then(
            user => {
                dispatch(success(id));
            },
            error => {
                dispatch(failure(id, error));
            }
        );
    };
};

export const userActions = {
    login,
    logout,
    register,
    retrieveAll,
    delete: _delete
};
