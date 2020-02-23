package tw.waterball.vocabnotes.models.repositories;

import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.List;

public interface PagingWordGroupRepository {
    List<WordGroup> findWordGroupsFromDictionary(int dictionaryId, int offset, int limit);
}
