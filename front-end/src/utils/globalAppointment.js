import React, { useReducer, useContext } from "react";
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
const sock = new SockJS(
  "http://localhost:8082/notifications/gs-guide-websocket"
);
const stompClient = Stomp.over(sock);
stompClient.connect(
  { userId: "leslie" },
  frame => {
    stompClient.subscribe("/topic/cancel", greeting => {
      console.log(JSON.parse(greeting.body).content);
    });
    //This is for alone
    stompClient.subscribe("/user/topic/cancel", greeting => {
      console.log(JSON.parse(greeting.body).content);
    });
  },
  err => console.log(err)
);
const send = (type, appointment) =>
  stompClient.send(`/notifications/appointment/${type}`, {}, appointment);

const initialState = {
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

const reducer = (state, action) => {
  switch (action.type) {
    case "PSY":
      let { psychologists } = action;
      return { ...state, psychologists };
    case "APPFETCH":
      const { appointments } = action;
      return { ...state, appointments };
    case "CANCEL":
      const newAppointments = state.appointments.filter(
        a => a._id !== action.id
      );
      return { ...state, appointments: newAppointments };
    case "UPDATE":
      const { id, appointmentStartTime, appointmentEndTime } = action;
      return {
        ...state,
        appointments: state.appointments.map(a =>
          a._id === id
            ? { ...a, _id: id, appointmentStartTime, appointmentEndTime }
            : a
        )
      };
    case "CREAT":
      const { type, ...rest } = action;
      return { ...state, appointments: [...state.appointments, rest] };
    default:
      return { auth: "UNAUTH" };
  }
};
const AppointContext = React.createContext();
const AppointmentProvider = ({ children }) => {
  const contextValue = useReducer(reducer, initialState);
  return (
    <AppointContext.Provider value={contextValue}>
      {children}
    </AppointContext.Provider>
  );
};
const useAppointment = () => useContext(AppointContext);
export { send, AppointmentProvider, useAppointment };
