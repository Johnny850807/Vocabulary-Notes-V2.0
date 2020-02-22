package tw.waterball.vocabnotes.models.repositories;

import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PagingWordGroupRepositroyImpl implements PagingWordGroupRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<WordGroup> findWordGroupsFromDictionary(int dictionaryId, int limit, int offset) {
        return em.createQuery("SELECT wg FROM Dictionary dict JOIN Dictionary.wordGroups wg " +
                "WHERE dict.id = ?1", WordGroup.class)
                .setParameter(1, dictionaryId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
