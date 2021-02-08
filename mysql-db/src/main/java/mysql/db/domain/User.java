package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * User class
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // user id
    @ManyToOne
    @RestResource(exported = false)
    private Role role; // user role (either client or psychologist)
    @OneToMany
    @ElementCollection(targetClass = Email.class, fetch = FetchType.LAZY)
    @RestResource(exported = false)
    private List<Email> emails;
    private String info;
    private String username;
}
