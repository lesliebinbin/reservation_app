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

export default class PsychSchedule extends React.PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      currentViewName: "work-week"
    };
    // this.commitChanges = this.commitChanges.bind(this);
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
        console.log(result);
        this.setState({ data: result });
      }
    );
  }

  async getAppointmentByPatientAndPsychologist(patient, psychologist) {
    const url = `/data/api/appointments/search/findAllByPatientUsernameAndPsychologistUsername?patient=${patient}&psychologist=${psychologist}
`;
    const resp = await fetch(url);
    const jsonData = await resp.json();
    return jsonData;
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
