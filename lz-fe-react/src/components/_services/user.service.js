import {authHeader} from '../_helpers';

const login = (username, password) => {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    };
    return fetch('/users/authenticate', requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        })
        .then(user => {
            if (user && user.token) {
                localStorage.setItem('user', JSON.stringify(user));
            }
            return user;
        });
};

const logout = () => {
    localStorage.removeItem('user');
};

const handleResponse = (response) => {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
};

const retrieveAll = () => {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch('/users', requestOptions).then(handleResponse);
};

const retrieveById = (id) => {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch('/users/' + id, requestOptions).then(handleResponse);
};

const register = (user) => {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    };
    return fetch('/users/register', requestOptions).then(handleResponse);
};

const update = (user) => {
    const requestOptions = {
        method: 'PUT',
        headers: {...authHeader(), 'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    };
    return fetch('/users/' + user.id, requestOptions).then(handleResponse);
};

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
    const requestOptions = {
        method: 'DELETE',
        headers: authHeader()
    };
    return fetch('/users/' + id, requestOptions).then(handleResponse);
}

export const userService = {
    login,
    logout,
    retrieveAll,
    retrieveById,
    register,
    update,
    delete: _delete
};
