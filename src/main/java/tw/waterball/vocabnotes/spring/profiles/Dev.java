package tw.waterball.vocabnotes.spring.profiles;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Profile(Dev.name)
public @interface Dev {
    String name = "dev";
}
