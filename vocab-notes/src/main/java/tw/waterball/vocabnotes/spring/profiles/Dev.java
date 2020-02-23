package tw.waterball.vocabnotes.spring.profiles;

import org.springframework.context.annotation.Profile;

@Profile(Dev.name)
public @interface Dev {
    String name = "dev";
}
