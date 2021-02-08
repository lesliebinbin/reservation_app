import React, { Component } from "react";
import logo from "../../assets/LAMBDA.png";

export default class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <footer>
          ©2019 - Team Lambda
          <img src={logo} style={{ height: 35 }} alt="logo" />
        </footer>
      </div>
    );
  }
}
