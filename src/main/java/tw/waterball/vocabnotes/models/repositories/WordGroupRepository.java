package tw.waterball.vocabnotes.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.WordGroup;

// TODO JdbcSQLIntegrityConstraintViolationException will be thrown on delete method due to
//      many-to-many relationship
@Repository
public interface WordGroupRepository extends CrudRepository<WordGroup, Integer> {

}
