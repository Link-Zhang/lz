let users = JSON.parse(localStorage.getItem('users')) || [{
    "username": "admin",
    "password": "admin"
}];

export const fakeBackend = () => {
    let realFetch = window.fetch;
    window.fetch = (url, opts) => {
        return new Promise(
            (resolve, reject) => {
                setTimeout(
                    () => {
                        // authentication
                        if (url.endsWith('/users/authenticate') && opts.method === 'POST') {
                            let params = JSON.parse(opts.body);
                            let filteredUsers = users.filter(
                                user => {
                                    return user.username === params.username && user.password === params.password
                                }
                            );
                            if (filteredUsers.length) {
                                let user = filteredUsers[0];
                                let responseJson = {
                                    id: user.id,
                                    username: user.username,
                                    firstName: user.firstName,
                                    lastName: user.lastName,
                                    token: 'fake-jwt-token'
                                };
                                resolve({ok: true, json: () => responseJson});
                            } else {
                                reject('Username or password is incorrect');
                            }
                            return;
                        }

                        // retrieve users
                        if (url.endsWith('/users') && opts.method === 'GET') {
                            if (opts.headers && opts.headers.Authorization === 'Bearer fake-jwt-token') {
                                // respond 200 OK with users
                                resolve({ok: true, json: () => users});
                            } else {
                                // return 401 not authorised if token is null or invalid
                                reject('Unauthorised');
                            }
                            return;
                        }

                        // retrieve user by id
                        if (url.match(/\/users\/\d+$/) && opts.method === 'GET') {
                            if (opts.headers && opts.headers.Authorization === 'Bearer fake-jwt-token') {
                                let urlParts = url.split('/');
                                let id = parseInt(urlParts[urlParts.length - 1],10);
                                let matchedUsers = users.filter(user => {
                                    return user.id === id;
                                });
                                let user = matchedUsers.length ? matchedUsers[0] : null;
                                // respond 200 OK with user
                                resolve({ok: true, json: () => user});
                            } else {
                                // return 401 not authorised if token is null or invalid
                                reject('Unauthorised');
                            }

                            return;
                        }

                        // register user
                        if (url.endsWith('/users/register') && opts.method === 'POST') {
                            let newUser = JSON.parse(opts.body);
                            // validation
                            let duplicateUser = users.filter(user => {
                                return user.username === newUser.username;
                            }).length;
                            if (duplicateUser) {
                                reject('Username "' + newUser.username + '" is already taken');
                                return;
                            }
                            // save new user
                            newUser.id = users.length ? Math.max(...users.map(user => user.id)) + 1 : 1;
                            users.push(newUser);
                            localStorage.setItem('users', JSON.stringify(users));
                            // respond 200 OK
                            resolve({ok: true, json: () => ({})});
                            return;
                        }

                        // delete user
                        if (url.match(/\/users\/\d+$/) && opts.method === 'DELETE') {
                            if (opts.headers && opts.headers.Authorization === 'Bearer fake-jwt-token') {
                                let urlParts = url.split('/');
                                let id = parseInt(urlParts[urlParts.length - 1],10);
                                for (let i = 0; i < users.length; i++) {
                                    let user = users[i];
                                    if (user.id === id) {
                                        // delete user
                                        users.splice(i, 1);
                                        localStorage.setItem('users', JSON.stringify(users));
                                        break;
                                    }
                                }
                                // respond 200 OK
                                resolve({ok: true, json: () => ({})});
                            } else {
                                // return 401 not authorised if token is null or invalid
                                reject('Unauthorised');
                            }
                            return;
                        }

                        realFetch(url, opts).then(
                            response => resolve(response)
                        );
                    },
                    500
                );
            }
        );
    };
};
