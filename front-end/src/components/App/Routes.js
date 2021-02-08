import React from "react";
import { Switch, Route } from "react-router-dom";

import HomeView from "../Function/HomeView";
import Activities from "../Function/Activities";
import Appointment from "../Function/Appointment";
import Progress from "../Function/Progress";
import Result from "../Function/QuestionResult";
import ChatRobot from "../Function/ChatRobot";

export default () => (
  <Switch>
    <Route exact path="/" component={HomeView} />
    <Route exact path="/home" component={HomeView} />
    <Route exact path="/activities" component={Activities} />
    <Route exact path="/appointments" component={Appointment} />
    <Route exact path="/progress" component={Progress} />
    <Route exact path="/chatbot" component={ChatRobot} />
    <Route path="*" component={Result} />
  </Switch>
);
