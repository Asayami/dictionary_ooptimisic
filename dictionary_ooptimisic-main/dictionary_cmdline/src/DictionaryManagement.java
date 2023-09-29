import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    public static Scanner scan = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.println("input the number of word: ");
        int n = scan.nextInt(); // slg từ vựng.

        scan = new Scanner(System.in);
        // nhập vào n từ vựng
        for (int i = 0; i < n; i++) {
            System.out.println(i + 1 + ".input word_target: ");
            String word_target_ = scan.nextLine();

            System.out.println("input word_type: ");
            String word_type_ = scan.nextLine();

            System.out.println("input word_explain: ");
            String word_explain_ = scan.nextLine();

            System.out.println("input pronunciation: ");
            String pronunciation_ = scan.nextLine();

            System.out.println("input exam: ");
            String example_ = scan.nextLine();

            Words.add(new Word(word_target_, word_type_, word_explain_, pronunciation_, example_)); // add vào arraylist
        }
    }

}
