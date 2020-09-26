package nospringquiz;

import entity.Question;

import java.util.List;

public class QuizServiceJpa implements QuizService{
    private final QuestionRepository questionRepository;

    public QuizServiceJpa(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getQuiz(long id) {
        return questionRepository.findAll();
    }
}
