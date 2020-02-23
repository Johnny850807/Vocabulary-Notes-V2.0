package tw.waterball.vocabnotes.utils;

import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.*;

/**
 * Since we didn't override the 'equals and hashcode' method in our entities,
 * we have to provide own utility for testing equality.
 *
 * This is really redundant as we have to copy the code from java.utils.Collection#equals.
 *
 * <p>
 * The reason of avoiding using Object.equals is explained in the following link
 * Quoted: "Equals and hashCode must behave consistently across all entity state transitions.":
 * https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
 *
 * TODO Is there any way to perform equality test among entities, rather than Object.equals ?
 *
 * @author johnny850807@gmail.com (waterball)
 */
public class EntityEquality {

    public static boolean equals(Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            if (o1.getClass() != o2.getClass()) {
                return false;
            } else if (o1 instanceof Member) {
                return equals((Member) o1, (Member) o2);
            } else if (o1 instanceof Dictionary) {
                return equals((Dictionary) o1, (Dictionary) o2);
            } else if (o1 instanceof WordGroup) {
                return equals((WordGroup) o1, (WordGroup) o2);
            } else if (o1 instanceof Word) {
                return equals((Word) o1, (Word) o2);
            } else {
                throw new IllegalArgumentException("The type " + o1.getClass().getSimpleName() + " is not an entity.");
            }
        } else return o1 == null && o2 == null;
    }

    public static boolean equals(Member m1, Member m2) {
        return Objects.equals(m1.getId(), m2.getId()) &&
                Objects.equals(m1.getFirstName(), m2.getFirstName()) &&
                Objects.equals(m1.getLastName(), m2.getLastName()) &&
                Objects.equals(m1.getEmail(), m2.getEmail()) &&
                Objects.equals(m1.getPassword(), m2.getPassword()) &&
                m1.getAge() == m2.getAge() &&
                m1.getExp() == m2.getExp() &&
                Objects.equals(m1.getEmail(), m2.getEmail());
    }

    public static boolean equals(Dictionary d1, Dictionary d2) {
        return Objects.equals(d1.getId(), d2.getId()) &&
                Objects.equals(d1.getTitle(), d2.getTitle()) &&
                Objects.equals(d1.getDescription(), d2.getDescription()) &&
                equals(d1.getOwner(), d2.getOwner()) &&
                Objects.equals(d1.getType(), d2.getType()) &&
                equals(d1.getWordGroups(), d2.getWordGroups());

    }

    public static boolean equals(WordGroup wg1, WordGroup wg2) {
        return Objects.equals(wg1.getId(), wg2.getId()) &&
                wg1.hasTitle() == wg2.hasTitle() &&
                !wg1.hasTitle() || Objects.equals(wg1.getTitle(), wg2.getTitle()) &&
                equals(wg1.getWords(), wg2.getWords());
    }


    public static boolean equals(Word w1, Word w2) {
        return Objects.equals(w1.getId(), w2.getId()) &&
                Objects.equals(w1.getImageUrl(), w2.getImageUrl()) &&
                Objects.equals(w1.getName(), w2.getName()) &&
                Objects.equals(w1.getSynonyms(), w2.getSynonyms());
    }

    // code copy and modify from java.utils.Collection#equals
    private static boolean equals(Collection c1, Collection c2) {
        if (c1 == c2)
            return true;
        if (c1.size() != c2.size())
            return false;
        try {
            return containsAll(c1, c2);
        } catch (ClassCastException | NullPointerException unused)   {
            return false;
        }
    }

    private static boolean containsAll(Collection c1, Collection c2) {
        for (Object e : c2)
            if (!contains(c1, e))
                return false;
        return true;
    }

    private static boolean contains(Collection c, Object o) {
        Iterator it = c.iterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (equals(o, it.next()))
                    return true;
        }
        return false;
    }

}
