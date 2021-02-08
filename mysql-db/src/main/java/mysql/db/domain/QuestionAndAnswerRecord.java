package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Class for question and answer record
 */
public class QuestionAndAnswerRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // record id
    @RestResource(exported = false)
    @ManyToOne
//    @RestResource(exported = false)
    private User user; // record user
    @ElementCollection
    @RestResource(exported = false)
    private Map<String,Integer> scores;
    private final Long createdTime = System.currentTimeMillis();
}
