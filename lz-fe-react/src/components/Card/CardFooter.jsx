import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import {withStyles} from '@material-ui/core/styles';

const cardFooterStyle = {
    cardFooter: {
        display: "flex",
        alignItems: "center",
        backgroundColor: "transparent",
        padding: "0.9375rem 1.875rem"
    }
};

const CardFooter = ({...props}) => {
    const {classes, className, children, ...rest} = props;
    const cardFooterClasses = classNames({
        [classes.cardFooter]: true,
        [className]: className !== undefined
    });
    return (
        <div className={cardFooterClasses} {...rest}>
            {children}
        </div>
    );
};

CardFooter.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string
};

export default withStyles(cardFooterStyle)(CardFooter);
