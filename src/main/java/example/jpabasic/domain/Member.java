package example.jpabasic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Member {

    @Id
    private Long id;

    private String username;
}
