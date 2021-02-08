import React from "react";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import MuiDialogTitle from "@material-ui/core/DialogTitle";
import MuiDialogContent from "@material-ui/core/DialogContent";
import MuiDialogActions from "@material-ui/core/DialogActions";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Typography from "@material-ui/core/Typography";
import InfoIcon from "@material-ui/icons/Info";
import LogoutIcon from "@material-ui/icons/ExitToApp";
import { useCount } from "../../utils/globalAuthentication";

const styles = theme => ({
  root: {
    margin: 0,
    padding: theme.spacing(2)
  },
  closeButton: {
    position: "absolute",
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500]
  }
});

const DialogTitle = withStyles(styles)(props => {
  const { children, classes, onClose, ...other } = props;
  return (
    <MuiDialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={onClose}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </MuiDialogTitle>
  );
});

const DialogContent = withStyles(theme => ({
  root: {
    padding: theme.spacing(2)
  }
}))(MuiDialogContent);

const DialogActions = withStyles(theme => ({
  root: {
    margin: 0,
    padding: theme.spacing(1)
  }
}))(MuiDialogActions);

export default function Info(props) {
  const [_, dispatch] = useCount();
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const logout = () => {
    fetch("/auth/logout");
    dispatch({ type: "UNAUTH" });
    console.log(props);
    window.location.href = "/";
  };

  return (
    <div className="navbar-setting">
      <IconButton onClick={logout}>
        <LogoutIcon></LogoutIcon>
      </IconButton>
      <IconButton onClick={handleClickOpen}>
        <InfoIcon className="InfoButton"></InfoIcon>
      </IconButton>
      <Dialog
        onClose={handleClose}
        aria-labelledby="customized-dialog-title"
        open={open}
      >
        <DialogTitle id="customized-dialog-title" onClose={handleClose}>
          24/7 crisis lines
        </DialogTitle>
        <DialogContent dividers>
          <Typography gutterBottom>
            <h4>Emergency: 000</h4>
            <p>
              If you or someone you are with is in immediate danger, please call
              000 or go to your nearest hospital or emergency department
            </p>
          </Typography>
          <Typography gutterBottom>
            <h4>Lifeline: 131 114</h4>
            <p>Get immediate crisis support and suicide prevention services</p>
          </Typography>
          <Typography gutterBottom>
            <h4>Beyond Blue: 1300 22 46 36</h4>
            <p>
              Confidentially call a trained mental health professional who will
              listen, provide information, and advise you in the right direction
            </p>
          </Typography>
          <Typography gutterBottom>
            <h4>Kids Helpline: 1800 551 800</h4>
            <p>
              Over the phone counselling for children and young people between
              the ages 5 and 25
            </p>
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Got It
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
