package jpa;

import javax.persistence.EntityManager;

public class EntityManagerDemo {

    public static void main(String[] args) {
        EntityManager em = MyPersistence.JAVA5.getEntityManager();
        em.close();
    }
}
