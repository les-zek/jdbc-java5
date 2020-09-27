package nospringquiz;

import entity.Option;
import entity.Question;
import jpa.MyPersistence;

import java.util.Scanner;

public class QuizApp {
    public static void initData(QuestionRepository repository) {
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
        repository.save(q);
    }

    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepositoryJpa(MyPersistence.QUIZ);
             initData(questionRepository);
        //    questionRepository.findAll().forEach(System.out::println);
        QuizService quizService = new QuizServiceJpa(questionRepository);
        QuizController controller = new QuizController(quizService);
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