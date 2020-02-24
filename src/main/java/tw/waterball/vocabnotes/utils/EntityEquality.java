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

package tw.waterball.vocabnotes.utils;

import tw.waterball.vocabnotes.models.entities.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Since we didn't override the 'equals and hashcode' method in our entities,
 * we have to provide own utility for testing equality.
 * <p>
 * This is really redundant as we have to copy the code from java.utils.Collection#equals.
 *
 * <p>
 * The reason of avoiding using Object.equals is explained in the following link
 * Quoted: "Equals and hashCode must behave consistently across all entity state transitions.":
 * https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
 * <p>
 * TODO Is there any way to perform equality test among entities, rather than Object.equals ?
 *
 * @author johnny850807@gmail.com (waterball)
 */
public class EntityEquality {
    public static boolean equals(IdEntity o1, IdEntity o2) {
        return equals(o1, o2, true);
    }

    public static boolean equals(IdEntity o1, IdEntity o2, boolean testAssociations) {
        if (o1 != null && o2 != null) {
            if (o1.getClass() != o2.getClass()) {
                return false;
            } else if (o1 instanceof Member) {
                return equals((Member) o1, (Member) o2);
            } else if (o1 instanceof Dictionary) {
                return equals((Dictionary) o1, (Dictionary) o2, testAssociations);
            } else if (o1 instanceof WordGroup) {
                return equals((WordGroup) o1, (WordGroup) o2, testAssociations);
            } else if (o1 instanceof Word) {
                return equals((Word) o1, (Word) o2);
            } else {
                throw new IllegalArgumentException("The type " + o1.getClass().getSimpleName() + " is not an entity.");
            }
        } else return o1 == null && o2 == null;
    }

    public static boolean equals(Member m1, Member m2) {
        if (m1 == m2) {
            return true;
        }
        return Objects.equals(m1.getId(), m2.getId()) &&
                Objects.equals(m1.getFirstName(), m2.getFirstName()) &&
                Objects.equals(m1.getLastName(), m2.getLastName()) &&
                Objects.equals(m1.getEmail(), m2.getEmail()) &&
                Objects.equals(m1.getPassword(), m2.getPassword()) &&
                m1.getAge() == m2.getAge() &&
                m1.getExp() == m2.getExp() &&
                Objects.equals(m1.getEmail(), m2.getEmail());
    }

    public static boolean equals(Dictionary d1, Dictionary d2, boolean testAssociations) {
        if (d1 == d2) {
            return true;
        }
        return Objects.equals(d1.getId(), d2.getId()) &&
                Objects.equals(d1.getTitle(), d2.getTitle()) &&
                Objects.equals(d1.getDescription(), d2.getDescription()) &&
                equals(d1.getOwner(), d2.getOwner()) &&
                Objects.equals(d1.getType(), d2.getType()) &&
                (!testAssociations || equals(d1.getWordGroups(), d2.getWordGroups()));

    }

    public static boolean equals(WordGroup wg1, WordGroup wg2, boolean testAssociations) {
        if (wg1 == wg2) {
            return true;
        }
        return Objects.equals(wg1.getId(), wg2.getId()) &&
                wg1.hasTitle() == wg2.hasTitle() &&
                !wg1.hasTitle() || Objects.equals(wg1.getTitle(), wg2.getTitle()) &&
                (!testAssociations || equals(wg1.getWords(), wg2.getWords()));
    }


    public static boolean equals(Word w1, Word w2) {
        if (w1 == w2) {
            return true;
        }
        return Objects.equals(w1.getId(), w2.getId()) &&
                Objects.equals(w1.getImageUrl(), w2.getImageUrl()) &&
                Objects.equals(w1.getName(), w2.getName()) &&
                Objects.equals(w1.getSynonyms(), w2.getSynonyms());
    }

    // code copy and modify from java.utils.Collection#equals
    private static boolean equals(Collection<? extends IdEntity> c1, Collection<? extends IdEntity> c2) {
        if (c1 == c2)
            return true;
        if (c1.size() != c2.size())
            return false;
        try {
            return containsAll(c1, c2);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    private static boolean containsAll(Collection<? extends IdEntity> c1, Collection<? extends IdEntity> c2) {
        for (IdEntity e : c2)
            if (!contains(c1, e))
                return false;
        return true;
    }

    private static boolean contains(Collection<? extends IdEntity> c, IdEntity o) {
        Iterator<? extends IdEntity> it = c.iterator();
        if (o == null) {
            while (it.hasNext())
                if (it.next() == null)
                    return true;
        } else {
            while (it.hasNext())
                if (equals(o, it.next()))
                    return true;
        }
        return false;
    }

}
