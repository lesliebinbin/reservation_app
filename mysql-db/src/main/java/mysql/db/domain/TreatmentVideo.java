package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Treatment video class
 */
public class TreatmentVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // video id
    private String description; // video description
    private LocalDateTime updateTime; //video update time
    private String url; //video url link
}
