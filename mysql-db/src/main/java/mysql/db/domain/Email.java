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
 * Email class
 */
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // email id
    private String type; // email type
    private String emailAddress; // email address
}
