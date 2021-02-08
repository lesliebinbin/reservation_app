package mysql.db.repository;

import mysql.db.domain.Email;
import mysql.db.domain.Role;
import mysql.db.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    List<User> findByRole(Role role);
    User findFirstByEmails(Email email);
    User findById (long id);
    User findFirstByUsername(String username);
}
