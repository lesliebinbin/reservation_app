import React from "react";
import { Link } from "react-router-dom";

import Brand from "./Brand";
// import LogoutIcon from "@material-ui/icons/ExitToApp";
import InfoIcon from "@material-ui/icons/Info";
import { IconButton } from "@material-ui/core";
import Info from "./Info";
// const info = () => {
//   console.log("test");
//   alert("hhhhh");
// };

// const logout = () => {
//   window.location.reload();
// };
export default class TopNav extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <nav className="top-nav">
        <div className="container">
          <div className="navbar-header">
            <Brand />
            <Info />
          </div>
          <div id="navbar" className="navbar-collapse collapse">
            <ul className="nav navbar-nav">
              <li>
                <Link to="/home">Home</Link>
              </li>
              <li>
                <Link to="/activities">Treatment Videos</Link>
              </li>
              <li>
                <Link to="/chatbot">Chat Robot</Link>
              </li>
              <li>
                <Link to="/appointments">Appointments</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}
