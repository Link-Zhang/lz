import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

import {withStyles} from '@material-ui/core/styles';

import CardHeader from './CardHeader';
import CardBody from './CardBody';
import CardFooter from './CardFooter';

const cardStyle = {
    card: {
        border: "0",
        marginBottom: "30px",
        marginTop: "30px",
        borderRadius: "6px",
        color: "rgba(0, 0, 0, 0.87)",
        background: "#fff",
        width: "100%",
        boxShadow:
            "0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12)",
        position: "relative",
        display: "flex",
        flexDirection: "column",
        minWidth: "0",
        wordWrap: "break-word",
        fontSize: ".875rem",
        transition: "all 300ms linear",
    },
    loginForm: {
        margin: "0"
    },
    cardHidden: {
        opacity: "0",
        transform: "translate3d(0, -60px, 0)"
    }
};

class Card extends React.Component {
    constructor() {
        super();
        this.state = {
            cardAnimation: 'cardHidden',
            submitted: false,
        };
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

    handleSubmit = event => {
        event.preventDefault();
        this.setState({submitted: true});
        // const {username, password} = this.state;
        // const {dispatch} = this.props;
        // if (username && password) {
        //     dispatch(userActions.login(username, password));
        // }
        console.log("Click Login");
    };

    render() {
        const {classes} = this.props;

        const cardClasses = classNames({
            [classes.card]: true,
            [classes[this.state.cardAnimation]]: true
        });

        return (
            <div className={cardClasses}>
                <form className={classes.loginForm} method="POST">
                    <CardHeader color={'colorBlue'} plainCard={false}/>
                    <CardBody/>
                    <CardFooter onClick={this.handleSubmit} color={'colorBlue'}/>
                </form>
            </div>
        );
    };
}

Card.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(cardStyle)(Card);
