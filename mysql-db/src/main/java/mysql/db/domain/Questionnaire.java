package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Questionnaire class
 */
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //questionnaire id
    @RestResource(exported = false)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions; // questions list for questionnaire
    private String description; // questionnaire description
    private LocalDateTime updateTime; //questionnaire update time
}
