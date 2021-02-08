package mysql.db.repository;

import mysql.db.domain.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDao extends CrudRepository<Email, Long> {
}
