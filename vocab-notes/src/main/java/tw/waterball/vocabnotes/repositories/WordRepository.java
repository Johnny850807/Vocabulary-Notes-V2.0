package tw.waterball.vocabnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.entities.Word;

@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
}
