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

package tw.waterball.vocabnotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import tw.waterball.vocabnotes.models.entities.IdEntity;
import tw.waterball.vocabnotes.utils.EntityEquality;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@ExtendWith(SpringExtension.class)
public class BaseSpringTest {
    public void assertEntityEquals(IdEntity o1, IdEntity o2, boolean includeAssociation) {
        assertTrue(EntityEquality.equals(o1, o2, includeAssociation),
                "Two " + o1.getClass().getSimpleName() +"s are not equals.");
    }
    public void assertEntityEquals(IdEntity o1, IdEntity o2) {
        assertTrue(EntityEquality.equals(o1, o2),
                "Two " + o1.getClass().getSimpleName() +"s are not equals.");
    }

    protected static void commitAndRestartTransaction() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    protected static void assertEqualsIgnoreOrder(Collection c1, Collection c2) {
        assertTrue(c1.size() == c2.size() &&
                c1.containsAll(c2) && c2.containsAll(c1));
    }
}
