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

package tw.waterball.vocabnotes.spring.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;

/**
 * TODO non-type-safe exclusion over attributes, is there any better practice to config Gson not using annotation?
 * @author johnny850807@gmail.com (Waterball))
 */
public class GsonExclusionStrategies {
    private static Logger logger = LogManager.getLogger(GsonExclusionStrategies.class);

    public final static class Serialization implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            boolean shouldSkip = false;
            Class<?> clazzType = fieldAttributes.getDeclaringClass();
            Class<?> attrType = fieldAttributes.getDeclaredClass();
            if (Member.class == attrType) {
                shouldSkip = Member.Role.class == clazzType ||
                        "password".equals(fieldAttributes.getName());
            } else if (Dictionary.class == attrType) {
                shouldSkip = "wordGroups".equals(fieldAttributes.getName());
            }

            if (shouldSkip) {
                logger.debug("Deserialization exclusion: " + fieldAttributes.getDeclaredClass() + "." + fieldAttributes.getName());
            }
            return shouldSkip;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

    public final static class Deserialization implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            boolean shouldSkip = false;
            Class<?> clazzType = fieldAttributes.getDeclaringClass();
            Class<?> attrType = fieldAttributes.getDeclaredClass();

            if (Dictionary.class == clazzType) {
                shouldSkip = Member.class == attrType;
            }

            if (shouldSkip) {
                logger.debug("Deserialization exclusion: " + fieldAttributes.getDeclaredClass() + "." + fieldAttributes.getName());
            }
            return shouldSkip;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }
}
