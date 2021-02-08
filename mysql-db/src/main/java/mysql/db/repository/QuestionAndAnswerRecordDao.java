package mysql.db.repository;

import mysql.db.domain.QuestionAndAnswerRecord;
import mysql.db.domain.QuestionOption;
import mysql.db.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAndAnswerRecordDao
        extends CrudRepository<QuestionAndAnswerRecord, Long> {
    List<QuestionAndAnswerRecord> findAllByUserUsername(String username);

}
