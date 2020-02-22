package tw.waterball.vocabnotes.models.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.utils.RandomEntityGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@Sql(scripts = {"classpath:clear.sql", "classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@SuppressWarnings("OptionalGetWithoutIsPresent")
class RepositoryTest extends BaseSpringTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    WordGroupRepository wordGroupRepository;

    @Test
    public void testMemberRepository() {
        Member member = memberRepository.findById(1).get();
        assertEquals("Johnny", member.getFirstName());

        Member created = new Member(2, "A", "B", 15, "test@email.com", "Pass", Member.Role.MEMBER);
        created = memberRepository.save(created);
        Member found = memberRepository.findById(created.getId()).get();
        assertEntityEquals(created, found);
    }

    @Test
    public void testDictionaryRepository() {
        Dictionary toeic = dictionaryRepository.findById(1).get();

        Dictionary expect = RandomEntityGenerator.randomDictionary(Dictionary.Type.OWN,
                3, 5, 2, 7);
        assertEntityEquals(expect, toeic);

        // change its id then add again
        expect.setId(2);
        dictionaryRepository.save(expect);
        Dictionary found = dictionaryRepository.findById(2).get();
        assertEntityEquals(expect, found);
    }

    private Dictionary testDictionary() {
        return Dictionary.builder().id(1)
                .title("TOEIC Level 1")
                .description("The Toeic basic dictionary.")
                .type(Dictionary.Type.OWN)
                .owner(new Member(1, "Johnny", "Pan", 23,
                        "johnny850807@gmail.com", "hashed", Member.Role.MEMBER))
                .wordGroup(WordGroup.builder().id(1)
                        .word(new Word(1, "lease", "", "https://www.lawdonut.co.uk/sites/default/files/your-options-for-getting-out-of-a-lease-552568144.jpg"))
                        .word(new Word(2, "treaty", "", "https://static01.nyt.com/images/2018/12/15/opinion/15ArmsControl-edt/15ArmsControl-edt-articleLarge.jpg"))
                        .word(new Word(3, "stipulation", "", "http://www.hicounsel.com/wp-content/uploads/2017/07/maxresdefault.jpg"))
                        .build())
                .build();
    }
}