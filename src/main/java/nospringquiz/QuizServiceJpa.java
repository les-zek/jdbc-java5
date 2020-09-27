package nospringquiz;

import entity.Question;
import entity.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuizServiceJpa implements QuizService {
    private final QuizRepository quizRepository;

    public QuizServiceJpa(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }


    @Override
    public List<Question> getQuiz(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            return new ArrayList<>(quiz.get().getQuestions());
        }
        return Collections.emptyList();
    }
}