import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
        scan.close(); // phien ban cua Loc chua close scan, ong Liem close scanner :)
    }
    public void insertFromFile() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dictionary_cmdline\\src\\resources\\dictionaries.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineFromFile = line.split("\t");
                if (lineFromFile.length == 2) {
                    String wordInEnglish = lineFromFile[0];
                    String wordExplainInVn = lineFromFile[1];
                    Word word = new Word(wordInEnglish, "temp_word_type", wordExplainInVn, "temp_pronunciation", "temp_word_example");
                    Words.add(word);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dictionaryLookup() {
        int id = scan.nextInt();
        for (Word word : Words) {
            if (id == word.getId()) {
                System.out.println(word.getId() + "     |  " + word.getWord_target() + " |  " + word.getWord_explain());
                break;
            }
        }
        scan.close();
    }
    public void addWord() {
        scan.nextLine();
        String word_target = scan.nextLine();
        String word_type = scan.nextLine();
        String word_explain = scan.nextLine();
        String pronunciation = scan.nextLine();
        String example = scan.nextLine();
        Word newWord = new Word(word_target, word_type, word_explain, pronunciation, example);
        word_count++;
        Words.add(newWord);
        newWord.setId(word_count);
        scan.close();
    }
    public void updateWord() {
        System.out.println("Select an option: \n"
                + "0) Find updated word by id \n"
                + "1) Find updated word by name \n");
        int option;
        do {
            System.out.println("Enter either 0 or 1: \n");
            option = scan.nextInt();
            switch (option) {
                case 0 -> {
                    System.out.print("Enter id of word to be updated: \n");
                    int updatedWordId = scan.nextInt(); //tim tu muon update theo id
                    for (Word word : Words) {
                        if (updatedWordId == word.getId()) {
                            scan.nextLine();
                            String newWordTarget = scan.nextLine();
                            word.setWord_target(newWordTarget);
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            break;
                        }
                    }
                    scan.close();
                }
                case 1 -> {
                    System.out.print("Enter word to be updated: \n");
                    String updatedWord = scan.next(); //tim tu muon update theo ten/wordTarget
                    for (Word word : Words) {
                        if (updatedWord.equals(word.getWord_target())) {
                            scan.nextLine();
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            break;
                        }
                    }
                    scan.close();
                }
            }
        } while (option != 0 && option != 1);
    }
    public void removeWord() {
        int removedWordId = scan.nextInt();
        for (Word word : Words) {
            if (removedWordId == word.getId()) {
                Words.remove(word.getId());
            }
        }
        scan.close();
    }
    public void dictionarySearcher() {
        String prefixOfWord = scan.next();
        for (Word word : Words) {
            if(word.getWord_target().startsWith(prefixOfWord)) {
                System.out.print(word.getWord_target() + ", ");
            }
        }
        scan.close();
    }

    public void dictionaryExportToFile() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dictionaries_exported.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace(); //co the de return;
        }
    }
}
