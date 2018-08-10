import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import {withStyles} from '@material-ui/core/styles';

const cardHeaderStyle = {
    cardHeader: {
        border: "0",
        borderRadius: "6px",
        padding: "0.5rem",
        marginLeft: "15px",
        marginRight: "15px",
        marginTop: "-36px",
        marginBottom: "0px",
        textAlign: "center",
        verticalAlign: "middle",
    },
    colorRed: {
        color: "#fff",
        background: "linear-gradient(60deg, #ef5350, #e53935)",
        boxShadow: "0 12px 20px -10px rgba(244, 67, 54, 0.28), 0 4px 20px 0px rgba(0, 0, 0, 0.12), 0 7px 8px -5px rgba(244, 67, 54, 0.2)"
    },
    colorBlue: {
        // info
        color: "#fff",
        background: "linear-gradient(60deg, #26c6da, #00acc1)",
        boxShadow: "0 12px 20px -10px rgba(0, 188, 212, 0.28), 0 4px 20px 0px rgba(0, 0, 0, 0.12), 0 7px 8px -5px rgba(0, 188, 212, 0.2)"
    },
    colorPurple: {
        color: "#fff",
        background: "linear-gradient(60deg, #ab47bc, #8e24aa)",
        boxShadow: "0 12px 20px -10px rgba(156, 39, 176, 0.28), 0 4px 20px 0px rgba(0, 0, 0, 0.12), 0 7px 8px -5px rgba(156, 39, 176, 0.2)"
    },
    colorGreen: {
        color: "#fff",
        background: "linear-gradient(60deg, #66bb6a, #43a047)",
        boxShadow: "0 12px 20px -10px rgba(76, 175, 80, 0.28), 0 4px 20px 0px rgba(0, 0, 0, 0.12), 0 7px 8px -5px rgba(76, 175, 80, 0.2)"
    },
    colorOrange: {
        color: "#fff",
        background: "linear-gradient(60deg, #ffa726, #fb8c00)",
        boxShadow: "0 12px 20px -10px rgba(255, 152, 0, 0.28), 0 4px 20px 0px rgba(0, 0, 0, 0.12), 0 7px 8px -5px rgba(255, 152, 0, 0.2)"
    },
    colorRose: {
        color: "#fff",
        background: "linear-gradient(60deg, #ec407a, #d81b60)",
        boxShadow: "0 4px 20px 0px rgba(0, 0, 0, 0.14), 0 7px 10px -5px rgba(233, 30, 99, 0.4)"
    }
};

const CardHeader = ({...props}) => {
    const {classes, color} = props;
    const cardHeaderClasses = classNames({
        [classes.cardHeader]: true,
        [classes[color]]: color,
    });
    return (
        <div className={cardHeaderClasses}>
            <h3>Content Management System</h3>
        </div>
    );
};

CardHeader.propTypes = {
    classes: PropTypes.object.isRequired,
    color: PropTypes.oneOf(["colorRed", "colorBlue", "colorPurple", "colorGreen", "colorOrange", "colorRose"])
};

export default withStyles(cardHeaderStyle)(CardHeader);
