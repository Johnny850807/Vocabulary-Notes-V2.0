package tw.waterball.vocabnotes.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.List;

@Repository
public interface WordGroupRepository extends CrudRepository<WordGroup, Integer> {
}
