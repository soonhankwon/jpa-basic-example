package example.jpabasic.entitymanager;

import static org.assertj.core.api.Assertions.assertThat;

import example.jpabasic.domain.Member;
import example.jpabasic.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelationShipTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void one_direction_save() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member = new Member();
        member.setId(1L);
        member.setUsername("member1");
        member.setTeam(team);
        member.setAge(30);
        em.persist(member);

        Member findMember = em.find(Member.class, 1L);
        Team findTeam = findMember.getTeam();
        assertThat(findTeam.getName()).isEqualTo("TeamA");
        tx.commit();
    }

    @Test
    void two_direction() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("member1");
        member1.setTeam(team);
        member1.setAge(30);
        em.persist(member1);

        Member member2 = new Member();
        member2.setId(2L);
        member2.setUsername("member2");
        member2.setTeam(team);
        member2.setAge(30);
        em.persist(member2);
        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, 1L);
        List<Member> members = findMember.getTeam().getMembers();
        tx.commit();

        assertThat(members.stream().anyMatch
                ((member) -> member.getTeam().getName().equals("TeamA")))
                .isTrue();
    }

    @Test
    @DisplayName("연관관계의 주인에 값을 입력하지 않은 경우")
    void two_direction_ex() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("member1");
        member1.setAge(30);
        em.persist(member1);

        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        team.getMembers().add(member1);
        em.flush();
        em.clear();
        tx.commit();

        Member findMember = em.find(Member.class, 1L);
        assertThat(findMember.getTeam()).isNull();
    }

    @Test
    @DisplayName("연관관계 양쪽 다 입력 - 정상")
    void two_direction_success() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("member1");
        member1.setAge(30);
        member1.setTeam(team);
        em.persist(member1);

        team.getMembers().add(member1);
        em.flush();
        em.clear();
        tx.commit();

        Member findMember = em.find(Member.class, 1L);
        assertThat(findMember.getTeam().getName()).isEqualTo("TeamA");
    }
}
