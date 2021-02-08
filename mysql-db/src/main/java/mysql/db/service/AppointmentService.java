package mysql.db.service;

import lombok.extern.slf4j.Slf4j;
import mysql.db.domain.Appointment;
import mysql.db.domain.AppointmentState;
import mysql.db.domain.Role;
import mysql.db.domain.User;
import mysql.db.repository.AppointmentDao;
import mysql.db.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * Service layer for appointment including all appointment needy methods.
 *
 * @Author Haozhi Tang
 * @Author Zhibin Wong
 */
@Slf4j
@Service
//@Transactional
public class AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;

    /**
     * To update appointment status from current state to next state.
     * @param appointment User's appointment
     * @param state the next state after update
     * @return True if update successfully, false if update current state to previous state.
     */
    public boolean updateState(Appointment appointment, AppointmentState state) {
        if (state.compareTo(appointment.getState()) < 0) return false;

        appointment.setState(state);
        appointmentDao.save(appointment);

        return true;
    }

    /**
     * To create a new appointment for user with chosen psychologist
     * Or cancel previous appointment and rebook a new appointment with a new time.
     * @param appointment The appointment time that chosen by patient and available for psychologist.
     * @return True if booking or re-booking is successful, otherwise false.
     *
     */
    public boolean bookingValidCheck(Appointment appointment) {
        // check whether they are correct role.
        if  (!roleVerification(appointment)) {
            return false;
        }
        // check the time is psychologist available time.
        if (!validTimeCheck(appointment)) {
            return false;
        }
        // check whether patient has an appointment with another psychologist.
        if (!otherAppointmentCheck(appointment)) {
            return false;
        }
        return true;
    }

    /**
     * Whether the psychologist accept the pending appointment.
     * @param appointment Appointment the appointment need to be accepted.
     * @param accept True if accept, otherwise false.
     */
    public void acceptAppointment(Appointment appointment, boolean accept) {
        if (appointment.getState().equals(AppointmentState.PENDING) ||appointment.getState().equals(AppointmentState.REBOOK)) {
            if (accept) {
                this.updateState(appointment,AppointmentState.BOOKED);
                log.info("Accept appointment");
            } else {
                this.cancelAppointment(appointment);
                log.info("Cancel appointment");
            }
        }

    }

    /**
     * Cancel a appointment in case either the patient or psychologist wants to.
     * @param appointment the appointment need to be cancelled.
     * @return True if the appointment state is booked or pending, otherwise false.
     */
    public boolean cancelAppointment (Appointment appointment) {
        // only booked or pending appointment can be canceled.
        if (!appointment.getState().equals(AppointmentState.BOOKED)
                || !appointment.getState().equals(AppointmentState.PENDING)) {
            log.warn("your appointment can not be canceled", appointment.getState());
            return false;
        }

        this.updateState(appointment,AppointmentState.CANCELLED);
        log.info("Successfully cancel appointment ");
        return true;
    }

    /**
     * To set appointment finished.
     * @param appointment the appointment need to be finished.
     * @return True if the appointment state is booked, otherwise false.
     */
    public boolean finishAppointment (Appointment appointment) {
        if (appointment.getState() == AppointmentState.INPROGRESS) {
            this.updateState(appointment,AppointmentState.FINISHED);
            log.info("Appointment finished.");
            return true;
        }

        log.warn("This appointment can not be finished.");
        return false;
    }

    /**
     * Verify whether role is correct.
     * @param appointment The appointment need to be verified.
     * @return True if the roles are correct, otherwise false.
     */
    public boolean roleVerification (Appointment appointment) {
        User patient = appointment.getPatient();
        User psychologist = appointment.getPsychologist();

        if (patient.getRole().getId() != 1 || psychologist.getRole().getId() != 2 ) {
            log.warn("wrong role!");
            return false;
        }

        return true;
    }

    /**
     * To check whether the time is available for psychologist.
     * @param appointment the appointment with available time that need to be checked with psychologist.
     * @return True if the time is valid for psychologist, otherwise false.
     */
    public boolean validTimeCheck (Appointment appointment) {
        Appointment psychologistAppointment = appointmentDao.
                findAppointmentByPsychologistAndAppointmentTimeBetween(appointment.getPsychologist(),
                        appointment.getAppointmentTime(),
                        appointment.getAppointmentTime().plusMinutes(appointment.getDuration().toMinutes()));

        return psychologistAppointment == null;

    }

    /**
     * To check whether patient has other unfinished appointment.
     * @param appointment the appointment that need to be checked.
     * @return false if patient has other unfinished appointments, otherwise true.
     */

    public boolean otherAppointmentCheck(Appointment appointment) {
        User patient = appointment.getPatient();

        Appointment patientAppointment = appointmentDao.
                findAppointmentByPatientAndStateIsBefore(patient, AppointmentState.FINISHED);

        return (patientAppointment != null && !patientAppointment.getPsychologist().equals(appointment.getPsychologist()));
    }

    /**
     * To check whether booking a new appointment or rebook an appointment.
     * @param appointment appointment that need to be checked.
     * @return true if need to rebook, otherwise false.
     */
    private boolean rebookCheck(Appointment appointment) {
        User patient = appointment.getPatient();

        Appointment patientAppointment = appointmentDao.
                findAppointmentByPatientAndStateIsBefore(patient, AppointmentState.FINISHED);

        if (patientAppointment != null) {
            this.cancelAppointment(appointment);
            return true;
        }
        return false;
    }

    /**
     * Create a new appointment
     * @param appointment the appointment that need to be created
     * @return the appointment that be saved.
     */
    public Appointment createAppointment(Appointment appointment) {
//        if (rebookCheck(appointment)) {
//            appointment.setState(AppointmentState.REBOOK);
//            Appointment save = appointmentDao.save(appointment);
//            return save;
//        } else {
            appointment.setState(AppointmentState.PENDING);
            return appointmentDao.save(appointment);
//        }
    }
}
