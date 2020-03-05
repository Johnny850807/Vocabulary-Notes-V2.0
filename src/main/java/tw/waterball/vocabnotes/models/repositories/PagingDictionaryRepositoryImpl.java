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
import tw.waterball.vocabnotes.services.dto.DictionaryDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Repository
public class PagingDictionaryRepositoryImpl implements PagingDictionaryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DictionaryDTO> findPublicDictionaries(int offset, int limit) {
        return em.createQuery("SELECT new tw.waterball.vocabnotes.services.dto.DictionaryDTO" +
                "(d.id, d.title, d.description, d.owner.id, d.type) FROM Dictionary d", DictionaryDTO.class)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
    }

    @Override
    public List<DictionaryDTO> findOwnDictionaries(int ownerId, int offset, int limit) {
        return em.createQuery("SELECT new tw.waterball.vocabnotes.services.dto.DictionaryDTO" +
                "(d.id, d.title, d.description, d.owner.id, d.type) FROM Dictionary d JOIN d.owner m " +
                "WHERE m.id = ?1", DictionaryDTO.class)
                .setParameter(1, ownerId)
                .setFirstResult(offset)
                .setMaxResults(limit).getResultList();
    }
}
