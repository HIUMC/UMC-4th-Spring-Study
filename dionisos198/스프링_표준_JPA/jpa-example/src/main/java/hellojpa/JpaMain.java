package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        try{
            Member member=new Member();
            member.setUsername("lee");
            Member member2=new Member();
            member2.setUsername("kim");
            Member member3=new Member();
            member3.setUsername("leeeee");
            em.persist(member);
            em.persist(member2);
            em.persist(member3);
           List<Member> members=em.createNativeQuery("select ID from Member",Member.class).getResultList();
            for (Member member1 : members) {
                System.out.println(member1.getUsername());
            }
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();

    }
}
