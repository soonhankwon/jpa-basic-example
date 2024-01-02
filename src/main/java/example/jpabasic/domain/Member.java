package example.jpabasic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne // Member 입장에서 멤버가 다수에 속하고 팀은 단일이다. & 연관관계의 주인
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    // 양방향 연관관계 편의메서드
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
