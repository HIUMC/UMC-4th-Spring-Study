package JPQL;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        try{
            Team team=new Team();
            team.setName("teamJPQL");
            em.persist(team);
            Member member=new Member();
            member.setUsername("lee");
            member.setAge(25);
            Member member1=new Member();
            member1.setUsername("kim");
            member1.setAge(30);
            member.changeTeam(team);
            member1.changeTeam(team);
            Member member2=new Member();
            member2.setAge(35);
            member2.setUsername("아싸");
            Team team3=new Team();
            team3.setName("team아싸");
            em.persist(member2);
            em.persist(team3);
            em.persist(member);em.persist(member1);
            List<Member> members=em.createQuery("select m from Member m inner join m.team t where t.name='teamJPQL'",Member.class).getResultList();
            for (Member member3 : members) {
                System.out.println(member3.getId()+""+member3.getAge()+ member3.getUsername()+member3.getTeam().getId());
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
