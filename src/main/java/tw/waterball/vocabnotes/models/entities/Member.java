package tw.waterball.vocabnotes.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Member {
    private Integer id;

    @Size(min = 1, max=18)
    private String firstName;

    @Size(min = 1, max=18)
    private String lastName;

    @Min(1) @Max(150)
    private int age;

    @Email
    @Size(min = 1, max=50)
    private String email;

    @NotNull @NotBlank
    private String password;

    private int exp = 0;
    private int level = 1;

    @NotNull
    private Role role;

    @ToString.Exclude
    private transient List<Dictionary> ownDictionaries = new ArrayList<>();

    public enum Role {
        MEMBER, ADMIN
    }

    public Member(int id, String firstName,
                  String lastName,
                  int age, String email,
                  String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public void setExp(int exp) {
        this.exp = exp;
        this.level = Level.getLevelFromExp(exp).getNumber();
    }

    public void addDictionary(Dictionary dictionary) {
        ownDictionaries.add(dictionary);
        dictionary.setOwner(this);
    }

    public void removeDictionary(Dictionary dictionary) {
        ownDictionaries.remove(dictionary);
        dictionary.setOwner(null);
    }

}
