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
