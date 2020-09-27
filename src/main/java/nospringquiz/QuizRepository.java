package nospringquiz;

import entity.Question;
import entity.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    void save(Quiz quiz);
    Optional<Quiz> findById(long id); // dajemy optional aby nie było null
    void delete(Quiz quiz);
    void update (long id, Quiz quiz);
    List<Quiz> findAll();
}
