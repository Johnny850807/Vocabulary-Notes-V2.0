package tw.waterball.vocabnotes.entities;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
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
