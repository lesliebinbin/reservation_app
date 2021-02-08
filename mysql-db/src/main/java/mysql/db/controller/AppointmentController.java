package mysql.db.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import mysql.db.service.AppointmentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mysql.db.domain.Appointment;
import mysql.db.domain.AppointmentState;
import mysql.db.domain.User;
import mysql.db.repository.AppointmentDao;
import mysql.db.repository.UserDao;

@RestController
public class AppointmentController {
  private UserDao userDao;
  private AppointmentDao appointmentDao;
  private AppointmentService appointmentService;

  public AppointmentController(UserDao userDao, AppointmentDao appointmentDao) {
    this.userDao = userDao;
    this.appointmentDao = appointmentDao;
  }

  @RequestMapping(value = "/postAppointments", method = RequestMethod.POST)
  public Appointment recordAppointment(@RequestBody Map<String, Object> payload) {
    String patient = (String) payload.get("patient");
    User userPatient = userDao.findFirstByUsername(patient);
    String psychologist = (String) payload.get("psychologist");
    User userPsychologist = userDao.findFirstByUsername(psychologist);
    Long start = Long.parseLong(payload.get("start").toString());
    Long end = Long.parseLong(payload.get("end").toString());
    LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(start),
        TimeZone.getDefault().toZoneId());
    Duration duration = Duration.ofSeconds(end - start);
    Appointment appointment = Appointment.builder().createDateTime(LocalDateTime.now()).appointmentTime(startDateTime)
        .appointmentTimeStamp(start).appointmentEndTimeStamp(end).duration(duration).patient(userPatient)
        .psychologist(userPsychologist).build();
    appointment.setState(AppointmentState.INIT);
    Appointment save = appointmentDao.save(appointment);
    return save;
  }

  @RequestMapping(value = "/updateAppointment", method = RequestMethod.POST)
  public Appointment updateAppointment(@RequestBody Map<String, Object> payload) {
    Long appointmentId = Long.parseLong(payload.get("appointmentId").toString());
    Long start = Long.parseLong(payload.get("startTimeStamp").toString());
    Long end = Long.parseLong(payload.get("endTimeStamp").toString());
    Duration duration = Duration.ofSeconds(end - start);
    Appointment appointment = appointmentDao.findById(appointmentId).get();
    appointment.setAppointmentTimeStamp(start);
    appointment.setAppointmentEndTimeStamp(end);
    appointment.setDuration(duration);
    appointment.setState(AppointmentState.REBOOK);
    return appointmentDao.save(appointment);
  }

}
