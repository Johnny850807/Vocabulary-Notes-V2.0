package tw.waterball.vocabnotes.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.Word;

import java.util.Optional;

@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
    Optional<Word> findByName(String wordName);
    void deleteWordByName(String wordName);
}
