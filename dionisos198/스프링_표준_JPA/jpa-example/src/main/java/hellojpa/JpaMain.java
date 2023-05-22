package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        try{
            Child child1=new Child();
            child1.setName("lee");
            Child child2=new Child();
            child2.setName("kim");
            Parent parent=new Parent();
            parent.addChild(child1);
            parent.addChild(child2);
          /*  em.persist(child1);
            em.persist(child2);*/
            em.persist(parent);
            em.remove(parent);

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();

    }
}
