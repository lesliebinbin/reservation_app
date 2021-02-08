package mysql.db.repository;

import mysql.db.domain.Question;
import mysql.db.domain.QuestionOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface QuestionDao extends CrudRepository<Question, Long> {
//    Question findFirstByOptions(QuestionOption option);
}
