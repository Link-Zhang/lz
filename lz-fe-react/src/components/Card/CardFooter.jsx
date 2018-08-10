import React from "react";
import {Link} from 'react-router-dom';
import PropTypes from "prop-types";
import classNames from "classnames";
import {withStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const cardFooterStyle = theme => ({
    cardFooter: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "transparent",
        borderRadius: "6px",
        padding: "0.5rem",
    },
    button: {
        margin: theme.spacing.unit,
        borderRadius: "30px",
    },
    colorRed: {
        backgroundColor: "#f44336",
        boxShadow:
            "0 2px 2px 0 rgba(244, 67, 54, 0.14), 0 3px 1px -2px rgba(244, 67, 54, 0.2), 0 1px 5px 0 rgba(244, 67, 54, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#f44336",
            boxShadow:
                "0 14px 26px -12px rgba(244, 67, 54, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(244, 67, 54, 0.2)"
        }
    },
    colorBlue: {
        backgroundColor: "#00acc1",
        boxShadow:
            "0 2px 2px 0 rgba(0, 188, 212, 0.14), 0 3px 1px -2px rgba(0, 188, 212, 0.2), 0 1px 5px 0 rgba(0, 188, 212, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#00acc1",
            boxShadow:
                "0 14px 26px -12px rgba(0, 188, 212, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 188, 212, 0.2)"
        }
    },
    colorPurple: {
        backgroundColor: "#9c27b0",
        boxShadow:
            "0 2px 2px 0 rgba(156, 39, 176, 0.14), 0 3px 1px -2px rgba(156, 39, 176, 0.2), 0 1px 5px 0 rgba(156, 39, 176, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#9c27b0",
            boxShadow:
                "0 14px 26px -12px rgba(156, 39, 176, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(156, 39, 176, 0.2)"
        }
    },
    colorGreen: {
        backgroundColor: "#4caf50",
        boxShadow:
            "0 2px 2px 0 rgba(76, 175, 80, 0.14), 0 3px 1px -2px rgba(76, 175, 80, 0.2), 0 1px 5px 0 rgba(76, 175, 80, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#4caf50",
            boxShadow:
                "0 14px 26px -12px rgba(76, 175, 80, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(76, 175, 80, 0.2)"
        }
    },
    colorOrange: {
        backgroundColor: "#ff9800",
        boxShadow:
            "0 2px 2px 0 rgba(255, 152, 0, 0.14), 0 3px 1px -2px rgba(255, 152, 0, 0.2), 0 1px 5px 0 rgba(255, 152, 0, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#ff9800",
            boxShadow:
                "0 14px 26px -12px rgba(255, 152, 0, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(255, 152, 0, 0.2)"
        }
    },
    colorRose: {
        backgroundColor: "#e91e63",
        boxShadow:
            "0 2px 2px 0 rgba(233, 30, 99, 0.14), 0 3px 1px -2px rgba(233, 30, 99, 0.2), 0 1px 5px 0 rgba(233, 30, 99, 0.12)",
        "&:hover,&:focus": {
            backgroundColor: "#e91e63",
            boxShadow:
                "0 14px 26px -12px rgba(233, 30, 99, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(233, 30, 99, 0.2)"
        }
    },
    simple: {
        "&,&:focus,&:hover": {
            color: "#FFFFFF",
            background: "transparent",
            boxShadow: "none"
        },
        "&$colorPurple": {
            "&,&:focus,&:hover": {
                color: "#9c27b0"
            }
        },
        "&$colorBlue": {
            "&,&:focus,&:hover": {
                color: "#00acc1"
            }
        },
        "&$colorGreen": {
            "&,&:focus,&:hover": {
                color: "#4caf50"
            }
        },
        "&$colorOrange": {
            "&,&:focus,&:hover": {
                color: "#ff9800"
            }
        },
        "&$colorRose": {
            "&,&:focus,&:hover": {
                color: "#e91e63"
            }
        },
        "&$colorRed": {
            "&,&:focus,&:hover": {
                color: "#f44336"
            }
        },
    },
});

const CardFooter = ({onClick = f => f, ...props}) => {
    const {classes, color} = props;
    const cardFooterClasses = classNames({
        [classes.cardFooter]: true,
    });
    const buttonClasses = classNames({
        [classes.button]: true,
        [classes.simple]: true,
        [classes[color]]: color
    });
    return (
        <div className={cardFooterClasses}>
            <Button size='medium' className={buttonClasses} onClick={onClick}>
                Login
            </Button>
            <Button size='medium' className={buttonClasses}>
                {/*<Link to="/register">Register</Link>*/}
                Register
            </Button>
        </div>
    );
};

CardFooter.propTypes = {
    onClick: PropTypes.func,
    classes: PropTypes.object.isRequired,
    color: PropTypes.oneOf(["colorRed", "colorBlue", "colorPurple", "colorGreen", "colorOrange", "colorRose"])
};

export default withStyles(cardFooterStyle)(CardFooter);
