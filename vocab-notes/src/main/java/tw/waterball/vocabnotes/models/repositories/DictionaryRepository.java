package tw.waterball.vocabnotes.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.Dictionary;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Integer>, PagingWordGroupRepository {

}