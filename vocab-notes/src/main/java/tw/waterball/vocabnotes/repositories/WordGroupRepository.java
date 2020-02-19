package tw.waterball.vocabnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.entities.Word;
import tw.waterball.vocabnotes.entities.WordGroup;

@Repository
public interface WordGroupRepository extends CrudRepository<WordGroup, Integer> {
}
