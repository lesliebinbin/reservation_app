package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Question option class for questionnaire questions
 */
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // question id
    private String description; //question description
    private Integer mark; //question mark
}
