package nospringquiz;

import entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    void save(Question question);
    Optional <Question> findById(long id); // dajemy optional aby nie było null
    void delete(Question question);
    void update (long id, Question question);
    List<Question> findAll();
}
