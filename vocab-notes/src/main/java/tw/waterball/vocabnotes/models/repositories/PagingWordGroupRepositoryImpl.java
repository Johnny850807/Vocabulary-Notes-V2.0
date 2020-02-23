package tw.waterball.vocabnotes.models.repositories;

import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PagingWordGroupRepositoryImpl implements PagingWordGroupRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<WordGroup> findWordGroupsFromDictionary(int dictionaryId, int offset, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WordGroup> query = cb.createQuery(WordGroup.class);
        Root<Dictionary> root = query.from(Dictionary.class);
        Join<Dictionary, WordGroup> join = root.join("wordGroups");
        query.select(join).where(cb.equal(root.get("id"), dictionaryId));
        TypedQuery<WordGroup> typedQuery = em.createQuery(query);
        return typedQuery.setFirstResult(offset)
                .setMaxResults(limit).getResultList();
    }

    // TODO this approach throws NullPointerException, so we resort to Criteria API.
    //  However, JPQL is considered more legible, we should figure out what's wrong with it here/
    @Deprecated
    private List<WordGroup> findWordGroupsFromDictionaryViaJPQL(int dictionaryId, int offset, int limit) {
        return em.createQuery("SELECT wg FROM Dictionary dict JOIN Dictionary.wordGroups wg " +
                "WHERE dict.id = ?1", WordGroup.class)
                .setParameter(1, dictionaryId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
