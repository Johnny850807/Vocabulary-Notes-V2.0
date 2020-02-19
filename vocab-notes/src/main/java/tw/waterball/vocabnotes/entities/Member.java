package tw.waterball.vocabnotes.entities;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter() @Setter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode

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

    private int exp;

    private int level;

    private Type type;

    @OneToMany(mappedBy = "owner")
    private List<Dictionary> ownDictionaries = new ArrayList<>();

    public enum Type {
        MEMBER, ADMIN
    }
}
