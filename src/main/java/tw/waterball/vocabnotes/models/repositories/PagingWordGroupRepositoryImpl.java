/*
 *  Copyright 2020 johnny850807 (Waterball)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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

/**
 * @author johnny850807@gmail.com (Waterball))
 */
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
