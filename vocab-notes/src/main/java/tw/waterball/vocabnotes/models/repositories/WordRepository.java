package tw.waterball.vocabnotes.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.Word;

import java.util.Optional;

// TODO JdbcSQLIntegrityConstraintViolationException will be thrown on delete method due to
//      many-to-many relationship
@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
    Optional<Word> findByName(String wordName);
    void deleteWordByName(String wordName);
}
