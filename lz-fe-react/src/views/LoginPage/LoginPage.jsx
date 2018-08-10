import React from 'react';
import {connect} from 'react-redux';

import image from '../../assets/img/login-bg.jpg';
import Card from '../../components/Card/Card';

import Grid from '@material-ui/core/Grid';
import {withStyles} from '@material-ui/core/styles';

const loginPageStyle = {
    pageBackground: {
        minHeight: "100vh",
        height: "auto",
        margin: "0",
        padding: "0",
        border: "0",
        "&:before": {
            background: "rgba(0, 0, 0, 0.5)"
        },
        "&:before,&:after": {
            position: "absolute",
            zIndex: "1",
            width: "100%",
            height: "100%",
            display: "block",
            left: "0",
            top: "0",
            content: '""'
        },
        backgroundImage: "url(" + image + ")",
        backgroundSize: "cover",
        backgroundPosition: "top center"
    },
    loginContainer: {
        paddingRight: "15px",
        paddingLeft: "15px",
        marginRight: "auto",
        marginLeft: "auto",
        width: "100%",
        "@media (min-width: 576px)": {
            maxWidth: "540px"
        },
        "@media (min-width: 768px)": {
            maxWidth: "720px"
        },
        "@media (min-width: 992px)": {
            maxWidth: "960px"
        },
        "@media (min-width: 1200px)": {
            maxWidth: "1140px",
        },
        zIndex: "2",
        position: "relative",
        paddingTop: "20vh",
        color: "#FFFFFF"
    },
    gridItem: {
        position: "relative",
        width: "100%",
        minHeight: "1px",
        paddingRight: "15px",
        paddingLeft: "15px",
        flexBasis: "auto",
    },
};

class LoginPage extends React.Component {
    constructor(props) {
        super();
        // this.props.dispatch(userActions.logout());
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                <div className={classes.pageBackground}>
                    <div className={classes.loginContainer}>
                        <Grid container justify='center'>
                            <Grid item className={classes.gridItem} xs={12} sm={12} md={4}>
                                <Card/>
                            </Grid>
                        </Grid>
                    </div>
                </div>
            </div>
        );
    }
}

LoginPage.propTypes = {};

const mapStateToProps = state => {
    return {loggingIn: state.authentication};
};

const connectedLoginPage = connect(
    mapStateToProps
)(LoginPage);

export default withStyles(loginPageStyle)(connectedLoginPage);
