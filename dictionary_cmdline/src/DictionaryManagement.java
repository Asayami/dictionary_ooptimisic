import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
            int idSetter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineFromFile = line.split("\t");
                if (lineFromFile.length == 2) {
                    String wordInEnglish = lineFromFile[0];
                    String wordExplainInVn = lineFromFile[1];
                    Word word = new Word(wordInEnglish, "temp_word_type", wordExplainInVn, "temp_pronunciation", "temp_word_example");
                    idSetter++;
                    word.setId(idSetter);
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
                System.out.printf("%-3d | %-10s\t| %-15s\t| %-30s\t| %-30s\t| %s%n", word.getId(), word.getWord_target(), word.getWord_type(), word.getWord_explain(), word.getPronunciation(), word.getExample());
                break;
            }
        }
    }
    public void addWord() {
        System.out.println("Mời bạn nhập một từ mới theo hướng dẫn: \n");
        scan.nextLine();
        System.out.println("Nhập từ mới: \n");
        String word_target = scan.nextLine();
        word_target = word_target.substring(0, 1).toUpperCase() + word_target.substring(1);
        System.out.println("Nhập dạng từ của từ mới: \n");
        String word_type = scan.nextLine();
        word_type = word_type.substring(0, 1).toUpperCase() + word_type.substring(1);
        System.out.println("Nhập định nghĩa tiếng Việt của từ mới: \n");
        String word_explain = scan.nextLine();
        System.out.println("Nhập cách phát âm từ mới: \n");
        String pronunciation = scan.nextLine();
        System.out.println("Nhập ví dụ áp dụng từ này: \n");
        String example = scan.nextLine();
        Word newWord = new Word(word_target, word_type, word_explain, pronunciation, example);
        word_count++;
        Words.add(newWord);
        newWord.setId(word_count);
        scan.close();
    }
    public void updateWord() {
        System.out.println("Hãy chọn chức năng: \n"
                + "[0] Tìm từ được cập nhật theo id \n"
                + "[1] Tìm từ được cập nhật theo tên \n");
        int option;
        do {
            System.out.println("Chỉ được phép nhập 0 hoặc 1: \n");
            option = scan.nextInt();
            switch (option) {
                case 0 -> {
                    System.out.print("Nhập id từ cần cập nhật: \n");
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
                    System.out.print("Nhập từ cần cập nhật: \n");
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
        System.out.println("Xin hãy nhập Id của từ cần xóa: ");
        int removedWordId = scan.nextInt();
        for (Word word : Words) {
            if (removedWordId - 1 == word.getId()) {
                Words.remove(word.getId());
                break;
            }
        }
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
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dictionary_cmdline\\src\\resources\\dictionaries_exported.txt"));
            String line;
            int j = 0;
            while(Words.get(j)!=null) {
                line = String.format("%-5d %-20s %-15s %-40s %-20s %s%n", Words.get(j).getId(), Words.get(j).getWord_target(),
                        Words.get(j).getWord_type(), Words.get(j).getWord_explain(), Words.get(j).getPronunciation(),
                        Words.get(j).getExample());
                bufferedWriter.write(line);
                j++;
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace(); //co the de return;
        }
    }
}
