package jpa;

import entity.Demo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EntityManagerDemo {

    public static void main(String[] args) {
        EntityManager em = MyPersistence.JAVA5.getEntityManager();
        // znajdowanie encji w bazie
        Demo demo = em.find(Demo.class, 1);
        System.out.println(demo);
        Demo newDemo = new Demo();
        newDemo.setName("TESTOWY");
        newDemo.setPoints(300);

        // dodanie encji do bazy
        // robimy transakcję
        em.getTransaction().begin();
        em.persist(newDemo);
        em.getTransaction().commit();
        // koniec transakcji

        Query query = em.createQuery(
                "select d from Demo d where d.name = :qname and d.points = :qpoints",
                Demo.class);
        // d jest alias
        // gdy chcemy wybrane pole to select d.pole
        query.setParameter("qname", "TESTOWY");
        query.setParameter("qpoints", 300);
        List<Demo> resultList = query.getResultList();
        for (Demo d : resultList) {
            System.out.println(d);

        }

        // usuwanie encji z bazy
        em.getTransaction().begin();
        em.remove(demo);
        em.getTransaction().commit();
        // obiekt usuniety z bazy ale istniejacy w pamieci
        System.out.println(demo);
        demo = em.find(Demo.class,1);
        // obiekt pobrany z bazy po usunieciu
        System.out.println(demo);


        em.close();
    }
}
