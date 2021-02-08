package mysql.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// change it into enum would be better
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// role id
    @Column(unique = true, nullable = false)
    private String type; // type of role
}
