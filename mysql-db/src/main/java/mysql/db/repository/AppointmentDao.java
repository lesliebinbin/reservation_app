package mysql.db.repository;

import mysql.db.domain.Appointment;
import mysql.db.domain.AppointmentState;
import mysql.db.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentDao extends CrudRepository<Appointment, Long> {
    Appointment findAppointmentByPatient(User patient);
    Appointment findAppointmentByPatientAndStateIsBefore(User patient, AppointmentState state);
    Appointment findAppointmentByPsychologistAndAppointmentTimeBetween(User psychologist, LocalDateTime startTime, LocalDateTime endTime);
    Appointment findAppointmentById(Long id);
    Appointment deleteByPatientId(Long id);
    List<Appointment> findAllByStateAndAppointmentTimeBefore(AppointmentState state, LocalDateTime time);
    List<Appointment> findAllByPatientUsernameAndPsychologistUsername(String patient, String psychologist);
}
