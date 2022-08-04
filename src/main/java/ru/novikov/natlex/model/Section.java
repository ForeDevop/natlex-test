package ru.novikov.natlex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "section",
            fetch = FetchType.EAGER)
    private List<GeoClass> geologicalClasses;

    public static int getMaxGeoClassesCount(List<Section> sections) {
        return sections.stream()
                .map(section -> section.getGeologicalClasses().size())
                .max(Integer::compareTo).get();
    }
}
