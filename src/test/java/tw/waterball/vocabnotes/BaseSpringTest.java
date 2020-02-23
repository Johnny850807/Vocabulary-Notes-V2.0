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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tw.waterball.vocabnotes.utils.EntityEquality;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class BaseSpringTest {
    public void assertEntityEquals(Object o1, Object o2) {
        Assertions.assertTrue(EntityEquality.equals(o1, o2),
                "Two " + o1.getClass().getSimpleName() +"s are not equals.");
    }
}
