package tw.waterball.vocabnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class VocabNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocabNotesApplication.class, args);
	}

}
