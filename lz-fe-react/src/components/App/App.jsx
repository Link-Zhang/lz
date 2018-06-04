import React from 'react';
import {Router, Route} from 'react-router-dom';
import {connect} from 'react-redux';
import {history} from '../_helpers';
import {alertActions} from '../_actions';
import


class App extends React.Component {
    constructor(props) {
        super(props);
        const {dispatch} = this.props;
        history.listen(
            (location, action) => {
                dispatch(alertActions.clear());
            }
        );
    }

    render() {
        const {alert} = this.props;
        return (
            <div className="container">
                {
                    alert.message && <div className={`alert ${alert.type}`}>{alert.message}</div>
                }
                <Router history={history}>
                    <div>
                        <Route path="/login" component={LoginPage}/>
                    </div>
                </Router>
            </div>
        );
    }

}

const connectedApp = connect(mapStateToProps)(App);
export {connectedApp as App};
