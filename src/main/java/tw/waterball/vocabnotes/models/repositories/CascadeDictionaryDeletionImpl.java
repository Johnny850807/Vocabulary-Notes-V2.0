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

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Repository
public class CascadeDictionaryDeletionImpl implements CascadeDictionaryDeletion {
    @PersistenceContext
    private EntityManager em;

    @Modifying
    @Override
    public void deleteById(int dictionaryId) {
        em.createNativeQuery("DELETE FROM dictionary_wordgroup WHERE dictionary_id = ?1")
                .setParameter(1, dictionaryId).executeUpdate();

        em.createNativeQuery("DELETE FROM member_favorite_dictionary WHERE dictionary_id = ?1")
                .setParameter(1, dictionaryId).executeUpdate();

        em.createNativeQuery("DELETE FROM dictionary WHERE id = ?1")
                .setParameter(1, dictionaryId).executeUpdate();
    }
}
