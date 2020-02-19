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
import javax.persistence.Table;

@Getter()
@Setter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode

@Table(name = "word")
public class Word {
    @Id
    private int id;

    @NotNull
    @Length(min = 1, max = 30)
    private String name;

    @Length(max = 80)
    private String synonyms;

    @NotNull
    @Length(min = 1, max = 1000)
    @Column(name = "image_url")
    private String imageUrl;
}
