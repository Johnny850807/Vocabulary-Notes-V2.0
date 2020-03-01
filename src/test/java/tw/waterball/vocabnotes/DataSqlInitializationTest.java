package tw.waterball.vocabnotes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tw.waterball.vocabnotes.models.entities.Member;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test if the data.sql is executed.
 * @author johnny850807 (johnny850807@gmail.com)
 */
@DataJpaTest
public class DataSqlInitializationTest {
    @Autowired
    private TestEntityManager em;

    @Test
    public void test() {
        assertNotNull(em.find(Member.class, 1));
    }
}
