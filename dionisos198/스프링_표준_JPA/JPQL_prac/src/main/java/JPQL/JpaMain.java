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
            Team teamA=new Team();
            teamA.setName("teamA");
            Team teamB=new Team();
            teamB.setName("teamB");
            Team teamC=new Team();
            teamC.setName("teamC");
            em.persist(teamA);
            em.persist(teamB);
            em.persist(teamC);
            Member members[]=new Member[4];
            for(int i=0;i<3;i++){
                members[i]=new Member();
                members[i].setUsername("회원"+(i+1));
                if(i==0||i==1){
                    members[i].changeTeam(teamA);
                }
                else if(i==2){
                    members[i].changeTeam(teamB);
                }
                em.persist(members[i]);
            }
            /*em.flush();
            em.clear();*/
            System.out.println("------------------------------");
            List<Team> teams=em.createQuery("select distinct t from Team t join fetch t.members",Team.class).getResultList();
            for (Team team : teams) {
                System.out.println(team.getName()+team.getMembers().size());
            }
            members[0].setAge(30);
            int resultCount=em.createQuery("update Member m set m.age=20").executeUpdate();
            em.clear();
            Member findMember=em.find(Member.class,members[0].getId());
            System.out.println(findMember.getAge());
            System.out.println(resultCount);
            tx.commit();
        }catch(Exception e){
            System.out.println("------------------오류");
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();

    }
}
