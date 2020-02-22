package tw.waterball.vocabnotes.spring.profiles;

import org.springframework.context.annotation.Profile;

@Profile({Prod.name})
public @interface Prod {
    String name = "prod";
}
