package tw.waterball.vocabnotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tw.waterball.vocabnotes.utils.EntityEquality;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class BaseSpringTest {
    public void assertEntityEquals(Object o1, Object o2) {
        Assertions.assertTrue(EntityEquality.equals(o1, o2),
                "Two " + o1.getClass().getSimpleName() +"s are not equals.");
    }
}
