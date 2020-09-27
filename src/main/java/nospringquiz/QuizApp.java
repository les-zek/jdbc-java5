package nospringquiz;

import entity.Option;
import entity.Question;
import entity.Quiz;
import jpa.MyPersistence;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class QuizApp {
    public static void initData(QuestionRepository repository, QuizRepository quizRepository) {
        Set<Question> questions = new HashSet<>();

        Question q = Question.builder()
                .body("Wybierz słowo kluczowe Javy")
                .option(Option.builder().option1("Char").option2("integer").option3("boolean").option4("real").build())
                /*            .option1("Char")
                            .option2("integer")
                            .option3("boolean")
                            .option4("real")
                  */
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        q = Question.builder()
                .body("Wskaż instrukcję przerywającą iteracje")
                .option(Option.builder().option1("switch").option2("return").option3("continue").option4("break").build())
                /*
                        .option1("switch")
                        .option2("return")
                        .option3("continue")
                        .option4("break")
                  */
                .validOption(4)
                .points(5)
                .build();
        questions.add(q);

        repository.save(q);
        q = Question.builder()
                .body("Które wyrażenie jest fałszem ")
                .option(Option.builder().option1("10 > 5 && true").option2("\"a\".equals(\"a\")").option3("true == false").option4("10 != 4").build())
                /*
                .option1("10 > 5 && true")
                .option2("\"a\".equals(\"a\")")
                .option3("true == false")
                .option4("10 != 4")
                */
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);

        repository.save(q);
        Quiz quiz = Quiz.builder().title("Język Java").questions(questions).build();
        quizRepository.save(quiz);
    }

    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepositoryJpa(MyPersistence.QUIZ);
        //    questionRepository.findAll().forEach(System.out::println);
        QuizRepository quizRepository = new QuizRepossitoryJpa(MyPersistence.QUIZ);
        initData(questionRepository, quizRepository);

        QuizService quizService = new QuizServiceJpa(quizRepository);
        QuizController controller = new QuizController(quizService,1);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Question question = controller.next();
            System.out.println(question.getBody());
            /*
            System.out.println("1. " + question.getOption1());
            */
            System.out.println("1. " + question.getOption().getOption1());

            System.out.println("2. " + question.getOption().getOption2());

            System.out.println("3. " + question.getOption().getOption3());
            System.out.println("4. " + question.getOption().getOption4());
            System.out.println("0. Cofnij się do poprzedniego pytania");
            System.out.println("5. Koniec");
            int answer = scanner.nextInt();
            if (answer == 0) {
                controller.previous();
                controller.previous();
                continue;
            }
            if (answer == 5) {
                break;

            }
            controller.saveAnswer(question, answer);

        }
        System.out.println("Podsumowanie quizu :  " + controller.summary());
    }
}