package cmdline_package;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class DictionaryGame extends Dictionary {

    private static Map<String, Quiz> quizzes = new HashMap<>();

    public static void quizGame() {
        try {
            File ques = new File("dictionary_cmdline\\src\\resources\\dictionaries.txt");
            Scanner scanner = new Scanner(System.in);
            Scanner text = new Scanner(ques);
            while (true) {
                System.out.println("Enter a command: (create, take, view, list, exit)");
                String command = scanner.nextLine();
                if (command.equals("create")) {
                    createQuiz(text);
                } else if (command.equals("take")) {
                    takeQuiz(text, scanner);
                } else if (command.equals("view")) {
                    viewQuiz(scanner);
                } else if (command.equals("list")) {
                    listQuizzes();
                } else if (command.equals("exit")) {
                    break;
                } else {
                    System.out.println("Invalid command.");
                }
            }
            text.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void createQuiz(Scanner text) {
        System.out.println("Enter the name of the quiz:");
        String quizName = text.nextLine();
        Quiz quiz = new Quiz(quizName);
        System.out.println("Enter the number of questions:");
        int numQuestions = Integer.parseInt(text.nextLine());
        for (int i = 0; i < numQuestions; i++) {
            System.out.println("Enter the question:");
            String question = text.nextLine();
            System.out.println("Enter the number of choices:");
            int numChoices = Integer.parseInt(text.nextLine());
            List<String> choices = new ArrayList<>();
            for (int j = 0; j < numChoices; j++) {
                System.out.println("Enter choice " + (j+1) + ":");
                String choice = text.nextLine();
                choices.add(choice);
            }
            System.out.println("Enter the index of the correct choice:");
            int correctChoice = Integer.parseInt(text.nextLine()) - 1;
            quiz.addQuestion(new Question(question, choices, correctChoice));
        }
        quizzes.put(quizName, quiz);
        System.out.println("Quiz created.");
    }

    private static void takeQuiz(Scanner text, Scanner scanner) {
        System.out.println("Enter the name of the quiz:");
        String quizName = scanner.nextLine();
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        int score = 0;
        for (int i = 0; i < quiz.getNumQuestions(); i++) {
            Question question = quiz.getQuestion(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ": " + choices.get(j));
            }
            System.out.println("Enter your answer:");
            int userAnswer = Integer.parseInt(scanner.nextLine()) - 1;
            if (userAnswer == question.getCorrectChoice()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer is " + (question.getCorrectChoice() + 1) + ".");
            }
        }
        System.out.println("Your score is " + score + " out of " + quiz.getNumQuestions() + ".");
    }

    private static void viewQuiz(Scanner scanner) {
        System.out.println("Enter the name of the quiz:");
        String quizName = scanner.nextLine();
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        System.out.println("Quiz: " + quiz.getName());
        for (int i = 0; i < quiz.getNumQuestions(); i++) {
            Question question = quiz.getQuestion(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ": " + choices.get(j));
            }
            System.out.println("Answer: " + (question.getCorrectChoice() + 1));
        }
    }

    private static void listQuizzes() {
        System.out.println("Quizzes:");
        for (String quizName : quizzes.keySet()) {
            System.out.println("- " + quizName);
        }
    }
}

class Quiz {
    private String name;
    private List<Question> questions = new ArrayList<>();

    public Quiz(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getNumQuestions() {
        return questions.size();
    }
}

class Question {
    private String question;
    private List<String> choices;
    private int correctChoice;

    public Question(String question, List<String> choices, int correctChoice) {
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

}