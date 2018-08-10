import React from "react";
import PropTypes from "prop-types";

import {withStyles} from '@material-ui/core/styles';

import Grid from '@material-ui/core/Grid';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment'
import FormHelperText from '@material-ui/core/FormHelperText';

import AccountBox from '@material-ui/icons/AccountBox';
import Lock from '@material-ui/icons/Lock';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

const cardBodyStyle = {
    cardBody: {
        textAlign: "center",
        padding: "1rem",
    },
    gridUsername: {
        alignItems: "flex-end",
        display: "inline-block",
        spacing:16
    },
    gridPassword: {
        alignItems: "flex-end",
        display: "inline-block",
        spacing:16,
        padding:"0.5rem",
    }
};

class CardBody extends React.Component {
    constructor() {
        super();
        this.state = {
            username: '',
            password: '',
            showPassword: false,
            submitted: false
        };
    }

    handleChange = name => event => {
        this.setState(
            {
                [name]: event.target.value
            }
        );
    };

    handleShowPassword = () => {
        this.setState({showPassword: !this.state.showPassword});
    };

    render() {
        const {classes} = this.props;

        return (
            <div className={classes.cardBody}>
                {/*username*/}
                <Grid container className={classes.gridUsername}>
                    <Grid item>
                        <AccountBox/>
                        <FormControl error={this.state.submitted && !this.state.username} margin={"normal"}>
                            <InputLabel>Username</InputLabel>
                            <Input id="login-page-form-username"
                                   type='text'
                                   value={this.state.username}
                                   onChange={this.handleChange('username')}
                                   endAdornment={
                                       <InputAdornment position="end">
                                           <IconButton disabled={true} disableRipple={true}/>
                                       </InputAdornment>
                                   }/>
                            {
                                this.state.submitted && !this.state.username &&
                                <FormHelperText>Username is required</FormHelperText>
                            }
                        </FormControl>
                    </Grid>
                </Grid>

                {/*password*/}
                <Grid container className={classes.gridPassword}>
                    <Grid item>
                        <Lock/>
                        <FormControl error={this.state.submitted && !this.state.password ? true : false}>
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
                                   }/>
                            {
                                this.state.submitted && !this.state.password &&
                                <FormHelperText>Password is required</FormHelperText>
                            }
                        </FormControl>
                    </Grid>
                </Grid>
            </div>
        );
    }
}

CardBody.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(cardBodyStyle)(CardBody);
