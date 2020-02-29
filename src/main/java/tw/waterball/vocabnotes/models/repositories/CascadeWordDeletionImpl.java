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
import tw.waterball.vocabnotes.models.entities.Word;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Repository
public class CascadeWordDeletionImpl implements CascadeWordDeletion {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteById(int wordId) {
        em.createNativeQuery("DELETE FROM wordgroup_word WHERE word_id = ?1")
                .setParameter(1, wordId).executeUpdate();

        em.createNativeQuery("DELETE FROM word WHERE id = ?1")
                .setParameter(1, wordId).executeUpdate();
    }

    @Override
    public void deleteByName(String name) {
        int id = em.createQuery("SELECT w FROM Word w Where w.name = ?1",
                Word.class)
                .setParameter(1, name)
                .getResultList().get(0).getId();

        deleteById(id);
    }
}
