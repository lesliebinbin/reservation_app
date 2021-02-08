import React from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import Calender from "./Calendar";

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
    borderRight: `1px solid ${theme.palette.divider}`
  },
  tabIndicator: {
    backgroundColor: "#7193cc"
  },
  calendarDisplay: {
    width: "90%",
    "@media (max-width:600px)": {
      width: "80%",
      fontSize: "0.8em"
    }
  },
  calendarTitle: {
    textAlign: "center",
    justifyContent: "center",
    color: "#404156",
    fontWeight: "bold",
    fontFamily: "Times New Roma",
    margin: "3vh 2vh 0 2vh",
    lineHeight: 3,
    "@media (max-width:600px)": {
      fontSize: "0.8em",
      lineHeight: 2
    }
  }
}));

const data = {
  psychologists: [
    "Janice Peters",
    "Jane Paul",
    "Peter Edwards",
    "Pierce Jones",
    "Qor'n Feld",
    "Rielly Goud",
    "Henry Goldman"
  ],
  appointments: [
    {
      _id: 1,
      patientName: "Andre Wine",
      psychologist: "Janice Peters",
      appointmentStartTime: "2019-09-15 09:00",
      appointmentEndTime: "2019-09-15 11:00",
      cancelled: false
    },
    {
      _id: 2,
      patientName: "Perla Mcgowin",
      psychologist: "Janice Peters",
      appointmentStartTime: "2019-09-30 10:00",
      appointmentEndTime: "2019-09-30 11:00",
      cancelled: false
    },
    {
      _id: 3,
      patientName: "Danilo Pelayo",
      psychologist: "Janice Peters",
      appointmentStartTime: "2019-09-24 13:00",
      appointmentEndTime: "2019-09-24 14:00",
      cancelled: false
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
      <div className={classes.calendarTitle}>
        PICK YOUR PSYCHOLOGIST AND PREFERRED TIME
      </div>
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
          <Tab label={data.psychologists[0]} {...a11yProps(0)} />
          <Tab label={data.psychologists[1]} {...a11yProps(1)} />
          <Tab label={data.psychologists[2]} {...a11yProps(2)} />
          <Tab label={data.psychologists[3]} {...a11yProps(3)} />
          <Tab label={data.psychologists[4]} {...a11yProps(4)} />
          <Tab label={data.psychologists[5]} {...a11yProps(5)} />
          <Tab label={data.psychologists[6]} {...a11yProps(6)} />
        </Tabs>
        <TabPanel className={classes.calendarDisplay} value={value} index={0}>
          Janice Peters
          <Calender psy={data.psychologists[0]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={1}>
          Jane Paul
          <Calender psy={data.psychologists[1]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={2}>
          Peter Edwards
          <Calender psy={data.psychologists[2]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={3}>
          Pierce Jones
          <Calender psy={data.psychologists[3]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={4}>
          Qor'n Feld
          <Calender psy={data.psychologists[4]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={5}>
          Rielly Goud
          <Calender psy={data.psychologists[5]} />
        </TabPanel>
        <TabPanel className={classes.calendarDisplay} value={value} index={6}>
          Henry Goldman
          <Calender />
        </TabPanel>
      </div>
    </div>
  );
}
