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
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Configuration
public class GsonConfig {
    private static Logger logger = LogManager.getLogger(GsonConfig.class);

    @Bean
    public GsonBuilderCustomizer gsonBuilderCustomizer() {
        return gsonBuilder -> gsonBuilder.addDeserializationExclusionStrategy(new DeserializationExclusionStrategy());
    }

    private final static class DeserializationExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            boolean shouldSkip = false;
            if (Dictionary.class == fieldAttributes.getDeclaredClass()) {
                shouldSkip = fieldAttributes.getDeclaringClass() == Member.class;
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
