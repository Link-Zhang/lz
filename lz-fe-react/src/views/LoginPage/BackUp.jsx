import React from 'react';
import {withStyles} from '@material-ui/core/styles';

import {connect} from 'react-redux';
import {Link} from 'react-router-dom';

import image from '../../assets/img/login-bg.jpg';

import Card from '../../components/Card/Card';
import CardHeader from '../../components/Card/CardHeader';
import CardBody from '../../components/Card/CardBody';
import CardFooter from '../../components/Card/CardFooter';
import Button from '../../components/Button/RegularButton';

import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment'
import AccountBox from '@material-ui/icons/AccountBox';
import Grid from '@material-ui/core/Grid';
import Lock from '@material-ui/icons/Lock';

import {userActions} from '../../components/_actions';

const containerFluid = {
    paddingRight: "15px",
    paddingLeft: "15px",
    marginRight: "auto",
    marginLeft: "auto",
    width: "100%"
};

const container = {
    ...containerFluid,
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
    }
};

const loginPageStyle = {
    cardHidden: {
        opacity: "0",
        transform: "translate3d(0, -60px, 0)"
    },
    pageBackground: {
        minHeight: "100vh",
        height: "auto",
        display: "inherit",
        position: "relative",
        margin: "0",
        padding: "0",
        border: "0",
        alignItems: "center",
        backgroundImage: 'url(' + image + ')',
        backgroundSize: 'cover',
        backgroundPosition: 'center top',
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
        }
    },
    loginContainer: {
        ...container,
        zIndex: "2",
        position: "relative",
        paddingTop: "20vh",
        color: "#FFFFFF"
    },
    gridContainer: {
        display: "flex"
    },
    gridItem: {
        position: "relative",
        width: "100%",
        minHeight: "1px",
        paddingRight: "15px",
        paddingLeft: "15px",
        flexBasis: "auto"
    },
    loginForm: {
        margin: "0"
    },
    loginTitle: {
        fontSize: "18px",
        fontWeight: "300",
        webkitTextSizeAdjust: "100%",

    },
    cardHeader: {
        width: "auto",
        textAlign: "center",
        marginLeft: "20px",
        marginRight: "20px",
        marginTop: "-40px",
        padding: "20px 0",
        marginBottom: "15px",
    },
    cardFooter: {
        paddingTop: "0rem",
        border: "0",
        borderRadius: "6px",
        justifyContent: "center !important"
    },

};

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        // todo logout with username
        // this.props.dispatch(userActions.logout());
        this.state = {
            username: '',
            password: '',
            showPassword: false,
            submitted: false,
            cardAnimation: 'cardHidden'
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        setTimeout(
            function () {
                this.setState({cardAnimation: ''});
            }.bind(this),
            500
        );
    }

    handleChange = name => event => {
        this.setState({
            [name]: event.target.value,
        });
    };

    handleShowPassword = () => {
        this.setState({showPassword: !this.state.showPassword});
    };

    handleSubmit = event => {
        event.preventDefault();
        this.setState({submitted: true});
        const {username, password} = this.state;
        const {dispatch} = this.props;
        if (username && password) {
            dispatch(userActions.login(username, password));
        }
    };

    render() {
        const {classes, loggingIn} = this.props;

        const {username, password, submitted} = this.state;

        return (
            <div>
                <div className={classes.pageBackground}>
                    <div className={classes.loginContainer}>
                        <Grid container justify='center' className={classes.gridContainer}>
                            <Grid item className={classes.gridItem} xs={12} sm={12} md={4}>
                                <Card className={classes[this.state.cardAnimation]}>
                                    <form className={classes.loginForm}>
                                        <CardHeader color='primary' className={classes.cardHeader}>
                                            <h4 className={classes.loginTitle}>Content Management System</h4>
                                        </CardHeader>
                                        <CardBody>
                                            {/*// username*/}
                                            <Grid container spacing={16} alignItems="flex-end"
                                                  alignContent='space-between'>
                                                <Grid item>
                                                    <AccountBox/>
                                                </Grid>
                                                <Grid item>
                                                    <FormControl>
                                                        <InputLabel>Username</InputLabel>
                                                        <Input id="login-page-form-username"
                                                               type='text'
                                                               value={this.state.username}
                                                               onChange={this.handleChange('username')}
                                                               endAdornment={
                                                                   <InputAdornment position="end">
                                                                       <IconButton disabled={true}>
                                                                       </IconButton>
                                                                   </InputAdornment>
                                                               }
                                                        />
                                                    </FormControl>
                                                </Grid>
                                            </Grid>
                                            {/*// password*/}
                                            <Grid container spacing={16} alignItems="flex-end"
                                                  alignContent='space-between'>
                                                <Grid item>
                                                    <Lock/>
                                                </Grid>
                                                <Grid item>
                                                    <FormControl>
                                                        <InputLabel>Password</InputLabel>
                                                        <Input id="login-page-form-password"
                                                               type={this.state.showPassword ? 'text' : 'password'}
                                                               value={this.state.password}
                                                               onChange={this.handleChange('password')}
                                                               endAdornment={
                                                                   <InputAdornment position="end">
                                                                       <IconButton
                                                                           onClick={this.handleShowPassword}>
                                                                           {this.state.showPassword ? <VisibilityOff/> :
                                                                               <Visibility/>}
                                                                       </IconButton>
                                                                   </InputAdornment>
                                                               }
                                                        />
                                                    </FormControl>
                                                </Grid>
                                            </Grid>
                                        </CardBody>
                                        <CardFooter className={classes.cardFooter}>
                                            <Button simple color='primary' size='lg'>
                                                Login
                                            </Button>
                                            <Button simple color='primary' size='lg'>
                                                Register
                                            </Button>
                                        </CardFooter>
                                    </form>
                                </Card>
                            </Grid>
                        </Grid>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    // const {loggingIn} = state.authentication;
    return {loggingIn: state.authentication};
};


export default withStyles(loginPageStyle)(LoginPage);
