import React, { useReducer, useContext } from "react";
// import { send } from "./globalAppointment";
const initialState = { auth: null };
const reducer = (state, action) => {
  switch (action.type) {
    case "patient":
      return { auth: "patient" };
    case "psychologist":
      return { auth: "psychologist" };
    case "unauth":
      return { auth: "unauth" };
    default:
      return { auth: null };
  }
};

const CountContext = React.createContext();

const CountProvider = ({ children }) => {
  const contextValue = useReducer(reducer, initialState);
  return (
    <CountContext.Provider value={contextValue}>
      {children}
    </CountContext.Provider>
  );
};

const useCount = () => {
  const contextValue = useContext(CountContext);
  return contextValue;
};

export { CountProvider, useCount };
