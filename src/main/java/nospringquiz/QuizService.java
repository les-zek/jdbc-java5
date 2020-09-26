package nospringquiz;

import entity.Question;

import java.util.List;

public interface QuizService {
    List<Question> getQuiz(long id);

}
