import React from "react";

import Routes from "./components/App/Routes";
import TopNav from "./components/App/TopNav";
import Footer from "./components/App/Footer";
import SignIn from "./components/App/SignIn";
import Brand from "./components/App/Brand";
import PsychSchedule from "./components/PsychApp/PsychSchedule";
import { useCount } from "./utils/globalAuthentication";
import { useEffect } from "react";

export default function App() {
  const [state, dispatch] = useCount();
  useEffect(() => {
    if (state.auth !== "patient" && state.auth !== "psychologist") {
      fetch("/auth/login/check")
        .then(resp => resp.json())
        .then(json => {
          if (json.username !== "anonymousUser") {
            dispatch({ type: "patient" });
          }
        });
    }
  });
  if (state.auth === "patient") {
    return (
      <div>
        <TopNav />
        <main className="frame-container">
          <Routes />
        </main>{" "}
        <Footer />
      </div>
    );
  } else if (state.auth === "psychologist") {
    return (
      <div>
        <Brand />
        <main className="psych-container">
          <div className="frame-view">
            <span className="greeting-psych">Welcome Back, Dr. JANICE</span>
            <PsychSchedule psy="JANICE PETERS" />
          </div>
        </main>{" "}
        <Footer />
      </div>
    );
  } else if (state.auth === "unauth") {
    alert("Invalid password or username, please try again");
    return (
      <div>
        <Brand />
        <SignIn />
        <Footer />
      </div>
    );
  } else {
    return (
      <div>
        <Brand />
        <SignIn />
        <Footer />
      </div>
    );
  }
}
