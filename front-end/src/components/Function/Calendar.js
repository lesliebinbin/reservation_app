import * as React from "react";
import Paper from "@material-ui/core/Paper";
import {
  ViewState,
  EditingState,
  IntegratedEditing
} from "@devexpress/dx-react-scheduler";
import {
  Scheduler,
  WeekView,
  Appointments,
  AppointmentForm,
  AppointmentTooltip,
  Toolbar,
  ViewSwitcher,
  MonthView,
  DayView,
  DateNavigator,
  TodayButton
} from "@devexpress/dx-react-scheduler-material-ui";
import useMediaQuery from "@material-ui/core/useMediaQuery";

export default class Calendar extends React.PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      currentViewName: "work-week"
    };
    this.commitChanges = this.commitChanges.bind(this);
    this.currentViewNameChange = currentViewName => {
      this.setState({ currentViewName });
    };
  }

  componentDidMount() {
    this.getAppointmentByPatientAndPsychologist(`yueer`, this.props.psy).then(
      resp => {
        const {
          _embedded: { appointments }
        } = resp;
        const temp = appointments.map(value => ({
          title: value.patient.username,
          startDate: new Date(
            new Date(value.appointmentTimeStamp).toLocaleString("en-US", {
              timeZone: "Australia/Brisbane"
            })
          ),
          endDate: new Date(
            new Date(value.appointmentEndTimeStamp).toLocaleString("en-US", {
              timeZone: "Australia/Brisbane"
            })
          ),
          link: value._links.appointment.href
        }));
        const result = temp.map((value, index) => ({
          id: index,
          appointmentId: value.link.split("appointments/")[1],
          ...value
        }));
        this.setState({ data: result });
      }
    );
  }

  componentWillReceiveProps() {
    this.getAppointmentByPatientAndPsychologist(`yueer`, this.props.psy).then(
      resp => {
        const {
          _embedded: { appointments }
        } = resp;
        const temp = appointments.map(value => ({
          title: value.patient.username,
          startDate: new Date(
            new Date(value.appointmentTimeStamp).toLocaleString("en-US", {
              timeZone: "Australia/Brisbane"
            })
          ),
          endDate: new Date(
            new Date(value.appointmentEndTimeStamp).toLocaleString("en-US", {
              timeZone: "Australia/Brisbane"
            })
          ),
          link: value._links.appointment.href
        }));
        const result = temp.map((value, index) => ({
          id: index,
          appointmentId: value.link.split("appointments/")[1],
          ...value
        }));
        this.setState({ data: result });
      }
    );
  }

  async cancelAppointmen(appointmentId) {
    await fetch(`/data/api/appointments/${appointmentId}`, {
      method: "DELETE"
    });
  }

  async updateAppointment(appointmentId, startTimeStamp, endTimeStamp) {
    const resp = await fetch(`/data/updateAppointment`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        appointmentId,
        startTimeStamp,
        endTimeStamp
      })
    });
  }

  async postAppointment(dataToSend) {
    const url = "/data/postAppointments";
    const resp = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(dataToSend)
    });
    const jsonData = resp.json();
    console.log(jsonData);
    console.log(dataToSend);
    return jsonData;
  }

  async getAppointmentByPatientAndPsychologist(patient, psychologist) {
    const url = `/data/api/appointments/search/findAllByPatientUsernameAndPsychologistUsername?patient=${patient}&psychologist=${psychologist}
`;
    const resp = await fetch(url);
    const jsonData = await resp.json();
    return jsonData;
  }
  commitChanges({ added, changed, deleted }) {
    this.setState(state => {
      let { data } = state;
      if (added) {
        const startingAddedId =
          data.length > 0 ? data[data.length - 1].id + 1 : 0;
        console.log(startingAddedId);
        data = [...data, { id: startingAddedId, ...added }];
        const dataToSend = {};
        dataToSend["patient"] = data[startingAddedId]["title"];
        dataToSend["psychologist"] = this.props.psy;
        dataToSend["start"] = data[startingAddedId].startDate.getTime();
        dataToSend["end"] = data[startingAddedId].endDate.getTime();
        this.postAppointment(dataToSend);
        console.log(this.props.psy);
      }
      if (changed) {
        Object.entries(changed).forEach(value => {
          const appointmentId = data[value[0]]["appointmentId"];
          let { startDate, endDate } = value[1];
            if(!startDate){
                startDate = data[value[0]].startDate;
            }
            if(!endDate){
                endDate = data[value[0]].endDate;
            }
          this.updateAppointment(
            appointmentId,
             
            startDate.getTime(),
            endDate.getTime()
          );
        });
        data = data.map(appointment =>
          changed[appointment.id]
            ? { ...appointment, ...changed[appointment.id] }
            : appointment
        );
      }
      if (deleted >= 0) {
        data.forEach(appointment => {
          if (appointment.id === deleted) {
            this.cancelAppointmen(appointment.appointmentId);
          }
        });
        data = data.filter(appointment => appointment.id !== deleted);
      }

      return { data };
    });
  }

  render() {
    const { data, currentViewName } = this.state;
    const currentDate = new Date().getDate();
    if (window.screen.width > 600) {
      return (
        <Paper>
          <Scheduler data={data} height={500} width={730}>
            <ViewState
              CurrentDate={currentDate}
              currentViewName={currentViewName}
              onCurrentViewNameChange={this.currentViewNameChange}
            />
            <EditingState onCommitChanges={this.commitChanges} />
            <IntegratedEditing />

            <WeekView startDayHour={9} endDayHour={17} />
            <WeekView
              name="work-week"
              displayName="Work Week"
              excludedDays={[0, 6]}
              startDayHour={9}
              endDayHour={17}
            />
            <MonthView />
            <DayView />

            <Appointments />
            <AppointmentTooltip showOpenButton showDeleteButton />
            <AppointmentForm />

            <Toolbar />
            <DateNavigator />
            <TodayButton />
            <ViewSwitcher />
          </Scheduler>
        </Paper>
      );
    } else {
      return (
        <Paper>
          <Scheduler Scheduler data={data} height={500} width={730}>
            <ViewState CurrentDate={currentDate} />
            <EditingState onCommitChanges={this.commitChanges} />
            <IntegratedEditing />
            <DayView startDayHour={9} endDayHour={17} />

            <Appointments />
            <AppointmentTooltip showOpenButton showDeleteButton />
            <AppointmentForm />

            <Toolbar />
            <DateNavigator />
          </Scheduler>
        </Paper>
      );
    }
  }
}
