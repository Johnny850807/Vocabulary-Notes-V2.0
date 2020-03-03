package tw.waterball.vocabnotes.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data @AllArgsConstructor
public class MemberWithDictionariesDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private int exp = 0;
    private int level = 1;
    private Member.Role role;
    private List<DictionaryDTO> ownDictionaries;
    private Set<DictionaryDTO> favoriteDictionaries;
}
