package tw.waterball.vocabnotes.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static MemberWithDictionariesDTO project(Member m, boolean includeOwnDictionaries, boolean includeFavDictionaries) {
        return new MemberWithDictionariesDTO(m.getId(), m.getFirstName(), m.getLastName(),
                m.getAge(), m.getEmail(), m.getExp(), m.getLevel(), m.getRole(),
                includeOwnDictionaries ?
                        m.getOwnDictionaries().stream().map(DictionaryDTO::project).collect(Collectors.toList()) : null,
                includeFavDictionaries ?
                        m.getFavoriteDictionaries().stream().map(DictionaryDTO::project).collect(Collectors.toSet()) : null);
    }

}
