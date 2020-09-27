package nospringquiz;

import entity.Question;
import entity.Quiz;
import jpa.MyPersistence;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class QuizRepossitoryJpa implements QuizRepository {
    private final MyPersistence persistence;

    public QuizRepossitoryJpa(MyPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public void save(Quiz quiz) {
        EntityManager em = persistence.getEntityManager();
        em.getTransaction().begin();
        em.persist(quiz);
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public Optional<Quiz> findById(long id) {
        EntityManager em = persistence.getEntityManager();
        Optional<Quiz> quiz = Optional.ofNullable(em.find(Quiz.class, id));
        em.close();
        return quiz;
    }

    @Override
    public void delete(Quiz quiz) {
        EntityManager em = persistence.getEntityManager();
        em.getTransaction().begin();
        em.remove(quiz);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(long id, Quiz quiz) {
        EntityManager em = persistence.getEntityManager();
        em.getTransaction().begin();
        Quiz entity = em.find(Quiz.class, id);
        if (entity == null) {
            em.close();
            em.getTransaction().commit();
            return;
        }
        entity.setQuestions(quiz.getQuestions());
        entity.setTitle(quiz.getTitle());
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public List<Quiz> findAll() {
        EntityManager em = persistence.getEntityManager();

        return em.createQuery("from Quiz", Quiz.class).getResultList();
    }
}
