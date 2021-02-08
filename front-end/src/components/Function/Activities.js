import React from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { useTheme } from "@material-ui/core/styles";
import useMediaQuery from "@material-ui/core/useMediaQuery";
import { red } from "@material-ui/core/colors";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <Typography
      component="div"
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      <Box p={3}>{children}</Box>
    </Typography>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired
};

function a11yProps(index) {
  return {
    id: `vertical-tab-${index}`,
    "aria-controls": `vertical-tabpanel-${index}`
  };
}

const useStyles = makeStyles(theme => ({
  root: {
    margin: "0 2vh",
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    display: "flex"
  },
  tabs: {
    borderRight: `1.5px solid ${theme.palette.divider}`
  },
  tabIndicator: {
    backgroundColor: "#7193cc"
  },
  videoTitle: {
    textAlign: "center",
    color: "#404156",
    fontFamily: "Times New Roma",
    fontWeight: "bold",
    margin: "3vh 2vh 0 2vh",
    lineHeight: 3,
    "@media (max-width:600px)": {
      fontSize: "0.8em",
      lineHeight: 2
    }
  },
  videoDisplay: {
    width: "80%",
    "@media (max-width:600px)": {
      width: "50%"
    }
  },
  video: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    "@media (max-width:600px)": {
      display: "flex",
      justifyContent: "right",
      alignItems: "right"
    }
  },
  play: {
    width: "850px",
    height: "500px",
    "@media (max-width:600px)": {
      width: "250px",
      height: "350px"
    }
  }
}));

const data = {
  video_names: ["Video 1", "video 2", "video 3"],
  video_details: [
    {
      _id: 1,
      name: "Video 1",
      url: "https://www.youtube.com/embed/F2hc2FLOdhI",
      watched: false
    },
    {
      _id: 2,
      name: "Video 2",
      url: "https://www.youtube.com/embed/kD_Fr3VAsYM",
      watched: false
    },
    {
      _id: 3,
      name: "Video 3",
      url: "https://www.youtube.com/embed/rUMF5R7DoOA",
      watched: false
    }
  ]
};

export default function VerticalTabs() {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  function handleChange(event, newValue) {
    setValue(newValue);
  }

  return (
    <div className="frame-view">
      <div className={classes.videoTitle}>START YOUR VIDEO TREATMENT</div>
      <div className={classes.root}>
        <Tabs
          orientation="vertical"
          variant="scrollable"
          value={value}
          onChange={handleChange}
          aria-label="Vertical tabs example"
          className={classes.tabs}
          classes={{ indicator: classes.tabIndicator }}
        >
          <Tab label={data.video_names[0]} {...a11yProps(0)} />
          <Tab label={data.video_names[1]} {...a11yProps(1)} />
          <Tab label={data.video_names[2]} {...a11yProps(2)} />
        </Tabs>
        <TabPanel className={classes.videoDisplay} value={value} index={0}>
          <div className={classes.video}>
            <iframe
              className={classes.play}
              title="first"
              src={data.video_details[0]["url"]}
            ></iframe>
          </div>
        </TabPanel>
        <TabPanel className={classes.videoDisplay} value={value} index={1}>
          <div className={classes.video}>
            <iframe
              className={classes.play}
              title="second"
              src={data.video_details[1]["url"]}
            ></iframe>
          </div>
        </TabPanel>
        <TabPanel className={classes.videoDisplay} value={value} index={2}>
          <div className={classes.video}>
            <iframe
              className={classes.play}
              title="third"
              src={data.video_details[2]["url"]}
            ></iframe>
          </div>
        </TabPanel>
      </div>
    </div>
  );
}
