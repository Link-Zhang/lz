import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import {withStyles} from '@material-ui/core/styles';

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
    cardPlain: {
        background: "transparent",
        boxShadow: "none"
    },
    cardCarousel: {
        overflow: "hidden"
    }
};

const Card = ({...props}) => {
    const {classes, className, children, plain, carousel, ...rest} = props;
    const cardClasses = classNames({
        [classes.card]: true,
        [classes.cardPlain]: plain,
        [classes.cardCarousel]: carousel,
        [className]: className !== undefined
    });
    return (
        <div className={cardClasses} {...rest}>
            {children}
        </div>
    );
};

Card.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string,
    plain: PropTypes.bool,
    carousel: PropTypes.bool
};

export default withStyles(cardStyle)(Card);
