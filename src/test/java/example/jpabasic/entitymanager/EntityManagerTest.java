package example.jpabasic.entitymanager;

import static org.assertj.core.api.Assertions.assertThat;

import example.jpabasic.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityManagerTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void entity_life_cycle() {
        EntityManager em = emf.createEntityManager();
        // 비영속(new)
        Member member = new Member();

        // 영속(managed)
        em.persist(member);

        // 준영속(detached)
        em.detach(member);

        // 삭제(removed)
        em.remove(member);
    }

    @Test
    void first_cache() {
        EntityManager em = emf.createEntityManager();

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("USER1");

        // 1차 캐시에 저장
        em.persist(member1);

        // 1차 캐시에서 조회
        Member findMember = em.find(Member.class, 1L);
    }

    @Test
    void same_transaction_isSameAs_object() {
        EntityManager em = emf.createEntityManager();

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("USER1");

        Member member2 = em.find(Member.class, 1L);
        Member member3 = em.find(Member.class, 1L);

        // 같은 트랜잭션 안에서 같은 객체는 동일성 보장
        assertThat(member2).isSameAs(member3);
    }

    @Test
    void dirty_checking() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("USER1");

        em.persist(member1);
        // INSERT QUERY
        em.flush();

        Member findMember = em.find(Member.class, 1L);
        em.persist(findMember);
        findMember.setUsername("USER2");
        // UPDATE QUERY - DIRTY CHECKING
        em.flush();

        assertThat(findMember.getUsername()).isEqualTo("USER2");
    }
}
