package mysql.db.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Appointment class
 */
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // appointment id
    @ManyToOne
    @RestResource(exported = false)
    private User patient; // patient in appointment
    @RestResource(exported = false)
    @ManyToOne
    private User psychologist;
    @Column(updatable = false,columnDefinition = "TIMESTAMP")
//    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private LocalDateTime createDateTime=LocalDateTime.now();
    @Column(columnDefinition = "TIMESTAMP")
//    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private LocalDateTime appointmentTime;
    private Long appointmentTimeStamp;
    private Long appointmentEndTimeStamp;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Duration duration;
    @Enumerated
    private AppointmentState state=AppointmentState.BOOKED;
    private boolean is_canceled;
}
