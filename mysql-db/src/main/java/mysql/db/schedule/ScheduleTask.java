package mysql.db.schedule;

import mysql.db.domain.Appointment;
import mysql.db.domain.AppointmentState;
import mysql.db.repository.AppointmentDao;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@EnableAsync
public class ScheduleTask {
    private AppointmentDao dao;

    public ScheduleTask(AppointmentDao dao) {
        this.dao = dao;
    }

    @Async
    @Scheduled(cron = "0 8 * * *")
    public void scheduleHandleExpiredAppointments() {
        List<Appointment> pendingAppointments = dao.findAllByStateAndAppointmentTimeBefore(AppointmentState.PENDING, LocalDateTime.now());
        pendingAppointments.forEach(a -> a.setState(AppointmentState.CANCELLED));
        dao.saveAll(pendingAppointments);
    }
}
