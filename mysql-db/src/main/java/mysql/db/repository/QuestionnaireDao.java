package mysql.db.repository;

import mysql.db.domain.Questionnaire;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface QuestionnaireDao extends CrudRepository<Questionnaire, Long> {
    Questionnaire findFirstByDescription(String description);
}
