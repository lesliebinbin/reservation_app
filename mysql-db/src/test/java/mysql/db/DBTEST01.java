package mysql.db;

import mysql.db.domain.*;
import mysql.db.repository.*;
import mysql.db.service.AppointmentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DBTEST01 {
  @Autowired
  private RoleDao dao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private AppointmentDao appointmentDao;
  @Autowired
  private AppointmentService appointmentService;
  @Autowired
  private QuestionnaireDao questionnaireDao;

  @Autowired
  private EmailDao emailDao;

  @Test
  public void testInsertRole() {
    dao.save(Role.builder().type("psychologist").build());
    dao.save(Role.builder().type("admin").build());

  }

  @Test
  public void testInsertEmail() {
    List<Email> emails = Arrays.asList(
        Email.builder().type("gmail").emailAddress("lesliebinbin19900129@gmail.com").build(),
        Email.builder().type("facebook").emailAddress("huangzhibin11@live.com").build());
    emailDao.saveAll(emails);
  }

  @Transactional
  @Test
  public void testUpdateState() {
    Appointment appointmentone = appointmentDao.findAppointmentById((long) 164);
    appointmentone.setState(AppointmentState.INIT);
    Assert.assertTrue(appointmentService.updateState(appointmentone, AppointmentState.PENDING));
    Assert.assertTrue(appointmentService.updateState(appointmentone, AppointmentState.BOOKED));
    Assert.assertTrue(appointmentService.updateState(appointmentone, AppointmentState.FINISHED));
    Assert.assertFalse(appointmentService.updateState(appointmentone, AppointmentState.REBOOK));

  }

  @Transactional
  @Test
  public void testBookingAppointment() {

    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime appointmentTime1 = LocalDateTime.now().plusDays(1);
    Duration duration = Duration.ofHours(1);

    User patient1 = userDao.findById((long) 1);
    User psychologist1 = userDao.findById((long) 4);

    Appointment appointment1 = Appointment.builder().patient(patient1).psychologist(psychologist1).duration(duration)
        .appointmentTime(appointmentTime1).createDateTime(localDateTime).build();

    appointmentService.createAppointment(appointment1);
    Assert.assertEquals(appointment1, appointmentDao.findAppointmentById((long) 248));
    ;

  }

  @Transactional
  @Test
  public void roleTest() {

    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime appointmentTime1 = LocalDateTime.now().plusDays(1);
    Duration duration = Duration.ofHours(1);

    User patient1 = userDao.findById((long) 1);
    User patient2 = userDao.findById((long) 2);
    User psychologist1 = userDao.findById((long) 4);

    Appointment appointment1 = Appointment.builder().patient(patient1).psychologist(psychologist1).duration(duration)
        .appointmentTime(appointmentTime1).createDateTime(localDateTime).build();
    Appointment appointment2 = Appointment.builder().patient(patient1).psychologist(patient2).duration(duration)
        .appointmentTime(appointmentTime1).createDateTime(localDateTime).build();

    Assert.assertTrue(appointmentService.roleVerification(appointment1));
    Assert.assertFalse(appointmentService.roleVerification(appointment2));
  }

  @Transactional
  @Test
  // @Rollback(false)
  public void validTime() {

    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime appointmentTime1 = LocalDateTime.now().plusDays(1);
    Duration duration = Duration.ofHours(1);

    User patient1 = userDao.findById((long) 1);
    User patient2 = userDao.findById((long) 2);
    User psychologist1 = userDao.findById((long) 4);

    Appointment appointment1 = Appointment.builder().patient(patient1).psychologist(psychologist1).duration(duration)
        .appointmentTime(appointmentTime1).createDateTime(localDateTime).build();

    Appointment appointment2 = Appointment.builder().patient(patient2).psychologist(psychologist1).duration(duration)
        .appointmentTime(appointmentTime1).createDateTime(localDateTime).build();

    Assert.assertTrue(appointmentService.validTimeCheck(appointment1));
    appointmentDao.save(appointment1);

    Assert.assertFalse(appointmentService.validTimeCheck(appointment2));

  }

  @Test
  public void testSomething() {
    for (int i = 0; i < 10000; i++) {
      System.out.println("Fuck...");
      if (i == 1000) {
        System.out.println(100 / 0);
      }
    }
  }
}
