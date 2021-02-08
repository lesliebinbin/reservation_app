package mysql.db.repository;

import mysql.db.domain.TreatmentVideo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentVideoDao
        extends CrudRepository<TreatmentVideo, Long> {
}
