package tw.waterball.vocabnotes.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter() @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "member")
public class Member {
    @Id
    private int id;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    private int age;

    @NotNull
    @Length(min = 1, max = 50)
    private String email;

    @NotNull
    private String password;

    private int exp = 0;

    private int level = 1;

    private Type type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner")
    private List<Dictionary> ownDictionaries = new ArrayList<>();

    public enum Type {
        MEMBER, ADMIN
    }

    public Member(int id, String firstName,
                  String lastName,
                  int age, String email,
                  String password, Type type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
