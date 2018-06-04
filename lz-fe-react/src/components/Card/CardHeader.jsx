import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import {withStyles} from '@material-ui/core/styles';
import {
    dangerCardHeader,
    infoCardHeader, primaryCardHeader, successCardHeader,
    warningCardHeader
} from "../../assets/jsx/common";

const cardHeaderStyle = {
    cardHeader: {
        borderRadius: "3px",
        padding: "1rem 15px",
        marginLeft: "15px",
        marginRight: "15px",
        marginTop: "-30px",
        border: "0",
        marginBottom: "0"
    },
    cardHeaderPlain: {
        marginLeft: "0px",
        marginRight: "0px"
    },
    warningCardHeader,
    successCardHeader,
    dangerCardHeader,
    infoCardHeader,
    primaryCardHeader
};

const CardHeader = ({...props}) => {
    const {classes, className, children, color, plainCard, ...rest} = props;
    const cardHeaderClasses = classNames({
        [classes.cardHeader]: true,
        [classes[color + "CardHeader"]]: color,
        [classes.cardHeaderPlain]: plainCard,
        [className]: className !== undefined
    });
    return (
        <div className={cardHeaderClasses} {...rest}>
            {children}
        </div>
    );
};

CardHeader.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string,
    color: PropTypes.oneOf(["warning", "success", "danger", "info", "primary"]),
    plainCard: PropTypes.bool
};

export default withStyles(cardHeaderStyle)(CardHeader);
