package mysql.db.repository;

import mysql.db.domain.QuestionOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface QuestionOptionDao
        extends CrudRepository<QuestionOption, Long> {
    List<QuestionOption> findByMark(Integer mark);
}
