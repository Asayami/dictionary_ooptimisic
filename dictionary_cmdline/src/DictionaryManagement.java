import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.lang.String;


public class DictionaryManagement extends Dictionary {
    public static Scanner scan = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.println("Nhập số từ muốn thêm: ");
        int n = scan.nextInt(); // slg từ vựng.


        // nhập vào n từ vựng
        for (int i = 0; i < n; i++) {
            System.out.println(i + 1 + ".Nhập từ mới: ");
            String word_target_ = scan.nextLine();

            System.out.println("Nhập dạng từ của từ mới: ");
            String word_type_ = scan.nextLine();

            System.out.println("Nhập định nghĩa tiếng Việt của từ mới: ");
            String word_explain_ = scan.nextLine();

            System.out.println("Nhập cách phát âm từ mới: ");
            String pronunciation_ = scan.nextLine();

            System.out.println("Nhập ví dụ áp dụng từ này: ");
            String example_ = scan.nextLine();

            Word temp = new Word(word_target_, word_type_, word_explain_, pronunciation_, example_);
            temp.setId(word_count);
            Words.add(temp); // add vào arraylist
            word_count++;
        }

    }

    public void insertFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dictionary_cmdline\\src\\resources\\dictionaries.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineFromFile = line.split("\t");
                if (lineFromFile.length == 2) {
                    String wordInEnglish = lineFromFile[0];
                    String wordExplainInVn = lineFromFile[1];
                    Word word = new Word(wordInEnglish, "temp_word_type", wordExplainInVn, "temp_pronunciation", "temp_word_example");
                    word.setId(word_count);
                    word_count++;
                    Words.add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {
        System.out.println("Mời bạn nhập id từ muốn tra: ");
        int id = scan.nextInt();
        for (Word word : Words) {
            if (id == word.getId()) {
                System.out.printf("%-3d | %-10s\t| %-15s\t| %-30s\t| %-30s\t| %s%n", word.getId(), word.getWord_target(), word.getWord_type(), word.getWord_explain(), word.getPronunciation(), word.getExample());
                break;
            }
        }
    }

    public void addWord() {
        System.out.println("Mời bạn nhập một từ mới theo hướng dẫn");
        System.out.println("Nhập từ mới: ");
        String word_target = scan.nextLine();
        System.out.println("Nhập dạng từ của từ mới: ");
        String word_type = scan.nextLine();
        System.out.println("Nhập định nghĩa tiếng Việt của từ mới: ");
        String word_explain = scan.nextLine();
        System.out.println("Nhập cách phát âm từ mới: ");
        String pronunciation = scan.nextLine();
        System.out.println("Nhập ví dụ áp dụng từ này: ");
        String example = scan.nextLine();
        Word newWord = new Word(word_target, word_type, word_explain, pronunciation, example);
        Words.add(newWord);
        word_count++;
        Collections.sort(Words, new Comparator<Word>() {
            public int compare(Word obj1, Word obj2) {
                String str1 = obj1.getWord_target();
                String str2 = obj2.getWord_target();
                return str1.compareToIgnoreCase(str2);
            }

        });
        /*
        note này đc ghi vào ngày 1/10
        lúc trước, addWord thêm vào ở giữa sẽ cho id sai vd: stt là 200 nhưng id là 1000 (wordcount+1)
        sort Words array sau khi add từ mới để cho đúng id
        */
        int tempcount = 0;
        for (Word word : Words) {
            tempcount++;
            if (word.equals(newWord)) {
                newWord.setId(tempcount - 1);
            }
            if (word.getWord_target().compareTo(newWord.getWord_target()) > 0) {
                word.setId(tempcount - 1);
            }
        }
    }

    public void updateWord() {
        System.out.println("Hãy chọn chức năng: \n" + "[0] Tìm từ được cập nhật theo id \n" + "[1] Tìm từ được cập nhật theo tên \n");
        int option;
        do {
            System.out.println("Chỉ được phép nhập 0 hoặc 1:");
            option = scan.nextInt();
            switch (option) {
                case 0 -> {
                    System.out.print("Nhập id từ cần cập nhật: \n");
                    int updatedWordId = scan.nextInt(); //tim tu muon update theo id
                    for (Word word : Words) {
                        if (updatedWordId == word.getId()) {
                            System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay vào chỗ id: " + word.getId());
                            String newWordTarget = scan.nextLine();
                            word.setWord_target(newWordTarget);
                            System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            System.out.println("Mời bạn nhập phần giải thích từ mới: ");
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            System.out.println("TEMP: Mời bạn nhập cách đọc từ mới: ");
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            System.out.println("Mời bạn nhập một ví dụ cách dùng từ mới: ");
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            break;
                        }
                    }
                }
                case 1 -> {
                    System.out.print("Nhập từ cần cập nhật: \n");
                    String updatedWord = scan.next(); //tim tu muon update theo ten/wordTarget
                    for (Word word : Words) {
                        if (updatedWord.equals(word.getWord_target())) {
                            scan.nextLine();
                            System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay chỗ của từ: " + word.getWord_target());
                            System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            System.out.println("Mời bạn nhập phần giải thích từ mới: ");
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            System.out.println("TEMP: Mời bạn nhập cách đọc từ mới: ");
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            System.out.println("Mời bạn nhập một ví dụ cách dùng từ mới: ");
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            break;
                        }
                    }
                }
            }
        } while (option != 0 && option != 1);
    }

    public void removeWord() {
        boolean hasBeenRemoved = false;
        System.out.println("Hãy nhập id của từ cần xóa: ");
        System.out.println("Lưu ý nhập ID của từ trong khoảng (0-" + (word_count - 1) + ")");
        int removedWordId = scan.nextInt();
        int iterator = 0;
        String tempString = "";
        for (Word word : Words) {
            if (removedWordId == word.getId()) {
                tempString = word.getWord_target();
                Words.remove(word.getId());
                hasBeenRemoved = true;
                word_count--;
                break;
            }
        }
        for (int i = removedWordId; i < word_count; i++) {
            Words.get(i).setId(i);
        }
        if (!hasBeenRemoved) {
            System.out.println("ERROR: Không xóa được từ!");
        } else {
            System.out.println("Bạn đã xóa từ thành công!" + "\nID từ đã xóa: " + removedWordId + "\nTừ đã xóa: " + tempString);
        }
    }

    public void dictionarySearcher() {
        System.out.println("Nhập từ bạn cần tìm: ");
        String prefixOfWord = scan.next();
        for (Word word : Words) {
            if (word.getWord_target().startsWith(prefixOfWord)) {
                System.out.print(word.getWord_target() + ", ");
            }
        }
    }

    public void dictionaryExportToFile() {
        try {
            System.out.println("Đang xuất ra file dictionaries_exported.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dictionary_cmdline\\src\\resources\\dictionaries_exported.txt"));
            String line;
            int j = 0;
            while (j < Words.size()) {
                line = String.format("%-5d %-20s %-15s %-40s %-20s %s%n", Words.get(j).getId(), Words.get(j).getWord_target(), Words.get(j).getWord_type(), Words.get(j).getWord_explain(), Words.get(j).getPronunciation(), Words.get(j).getExample());
                bufferedWriter.write(line);
                j++;
            }
            bufferedWriter.close();
            System.out.println("Đã xuất ra file dictionaries_exported.txt");
        } catch (Exception e) {
            e.printStackTrace(); //co the de return;
        }
    }
}
