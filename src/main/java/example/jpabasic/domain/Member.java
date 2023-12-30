package example.jpabasic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "member",
        uniqueConstraints = {@UniqueConstraint(
                name = "username_age_unique",
                columnNames = {"username", "age"}
        )})
public class Member {

    @Id
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer age;

    @Column(columnDefinition = "varchar(100) default 'EMPTY'")
    private String data;

    @Column(precision = 10, scale = 2)
    private BigDecimal cal;
}
