package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Question class
 */
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // question id
    private String description;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ElementCollection
    @RestResource(exported = false)
    private Map<String, Integer> scores;
}

