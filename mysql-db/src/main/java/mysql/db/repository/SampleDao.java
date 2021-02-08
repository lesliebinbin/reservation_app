package mysql.db.repository;

import mysql.db.domain.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleDao extends CrudRepository<Sample, Long> {
}
