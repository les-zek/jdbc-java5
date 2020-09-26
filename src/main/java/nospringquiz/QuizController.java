package nospringquiz;

import entity.Question;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class QuizController {
    private final QuizService quizService;
    Map<Question, Integer> answers = new HashMap<>();
    ListIterator<Question> questions;
    Question currentQuestion;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;

        questions = quizService.getQuiz(0).listIterator();
    }

    public Question next() {
        if (questions.hasNext()) {
            currentQuestion = questions.next();
        }
        return currentQuestion;

    }

    public Question previous() {
        if (questions.hasPrevious()) {
            currentQuestion = questions.previous();
        }
        return currentQuestion;
    }

    public void saveAnswer(Question question, int answer) {
        answers.put(question, answer);
    }

    public int summary () {
        return answers.entrySet().stream()
                .filter(entry ->
                        entry.getKey().getValidOption() == entry.getValue())
                .mapToInt(entry -> entry.getKey().getPoints())
                .sum();
    }
}