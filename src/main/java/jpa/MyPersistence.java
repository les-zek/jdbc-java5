package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum MyPersistence {
    JAVA5("java5");
    // public static MyPersistence JAVA5 = new MyPersistence("java5");
    private final EntityManagerFactory factory;

    MyPersistence(String persistenceUnit){
        this.factory = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    public EntityManager getEntityManager (){
        return factory.createEntityManager();
    }
}
