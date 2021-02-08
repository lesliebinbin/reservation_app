import React from "react";
import { Link } from "react-router-dom";

import logo from "../../assets/logo.png";

const Brand = () => {
  return (
    <Link to="/" className="navbar-brand">
      <img src={logo} style={{ height: 40 }} alt="logo" />
      <span>Mind Initiative</span>
    </Link>
  );
};

export default Brand;
