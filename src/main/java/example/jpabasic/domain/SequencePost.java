package example.jpabasic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "post_seq_generator",
        sequenceName = "post_seq",
        initialValue = 1, allocationSize = 50)
public class SequencePost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_seq_generator")
    private Long id;
}
