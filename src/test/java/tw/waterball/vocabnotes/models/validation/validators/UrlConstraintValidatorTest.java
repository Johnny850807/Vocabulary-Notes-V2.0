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

package tw.waterball.vocabnotes.models.validation.validators;

import org.junit.jupiter.api.Test;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.validation.BeanValidations;
import tw.waterball.vocabnotes.utils.RandomEntityGenerator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author johnny850807 (Waterball)
 */
class UrlConstraintValidatorTest {

    @Test
    public void test() {
        Word word = RandomEntityGenerator.randomWord();
        assertTrue(BeanValidations.isValid(word));

        word.setImageUrl("http://123.bb.cc.ss/d/a/s/d/./f?s55=s");
        assertFalse(BeanValidations.isValid(word));
    }
}