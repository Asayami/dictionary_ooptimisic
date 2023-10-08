import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;


public class DictionaryManagement extends Dictionary {
    public static Scanner scan = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.println("Nhập số từ muốn thêm: ");
        int n = -1; // slg từ vựng.
        try {
            n = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

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
        String filePath;
        System.out.println("Please enter the file path or press ENTER to use default path");
        filePath = scan.nextLine();
        if (filePath.isBlank()) {
            System.out.println("No path was entered, using default path..");
            filePath = "dictionary_cmdline\\src\\resources\\dictionaries.txt";
        } else if (Files.notExists(Path.of(filePath))) {
            System.out.println("Cannot find the file, please make sure you entered the correct path ");
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
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
            System.out.println("Successfully inserted from file ");
        } catch (Exception e) {
            System.out.println("Something went wrong.. :(");
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {
        System.out.println("Mời bạn nhập id từ muốn tra: ");
        int id = -1;
        try {
            id = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        for (Word word : Words) {
            if (id == word.getId()) {
                System.out.printf("%-3d | %-10s\t| %-15s\t| %-30s\t| %-30s\t| %s%n", word.getId(), word.getWord_target(), word.getWord_type(), word.getWord_explain(), word.getPronunciation(), word.getExample());
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
        newWord.setId(word_count);
        Words.add(newWord);
        word_count++;
    }

    public void updateWord() {
        System.out.println("""
                Hãy chọn chức năng:\s
                [0] Tìm từ được cập nhật theo id\s
                [1] Tìm từ được cập nhật theo tên\s
                """);
        int option = -1;
        do {
            System.out.println("Chỉ được phép nhập 0 hoặc 1:");
            try {
                option = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            boolean checkFound = false;
            switch (option) {
                case 0 -> {
                    System.out.print("Nhập id từ cần cập nhật: \n");
                    int updatedWordId = 0; //tim tu muon update theo id
                    try {
                        updatedWordId = Integer.parseInt(scan.nextLine());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    for (Word word : Words) {
                        if (updatedWordId == word.getId()) {
                            System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay vào chỗ id: " + word.getId());
                            System.out.println("Mời bạn nhập từ mới: ");
                            String newWordTarget = scan.nextLine();
                            word.setWord_target(newWordTarget);
                            System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            System.out.println("Mời bạn nhập giải thích từ mới: ");
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            System.out.println("Mời bạn nhập cách đọc từ mới: ");
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            System.out.println("Mời bạn nhập ví dụ cho từ mới: ");
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            break;
                        }
                    }
                }
                case 1 -> {
                    System.out.print("Nhập từ cần cập nhật: \n");
                    String updatedWord = scan.next(); //tim tu muon update theo ten/wordTarget
                    scan.nextLine();
                    for (Word word : Words) {
                        if (updatedWord.equals(word.getWord_target())) {
                            System.out.println("Đã tìm được! Từ tiếng Anh mới sẽ thay chỗ của từ: " + word.getWord_target());
                            System.out.println("Mời bạn nhập dạng từ của từ mới: ");
                            String newWordType = scan.nextLine();
                            word.setWord_type(newWordType);
                            System.out.println("Mời bạn nhập phần giải thích từ mới: ");
                            String newWordExplain = scan.nextLine();
                            word.setWord_explain(newWordExplain);
                            System.out.println("Mời bạn nhập cách đọc từ mới: ");
                            String newPronunciation = scan.nextLine();
                            word.setPronunciation(newPronunciation);
                            System.out.println("Mời bạn nhập một ví dụ cách dùng từ mới: ");
                            String newExample = scan.nextLine();
                            word.setExample(newExample);
                            checkFound = true;
                            break;
                        }
                    }
                }
            }
            if (!checkFound) {
                System.out.println("Không tìm được từ !");
            }
        } while (option != 0 && option != 1);
    }

    public void removeWord() {
        boolean hasBeenRemoved = false;
        boolean choice = false;
        String input;
        System.out.print("""
                Choose removing method:
                [0] By ID
                [1] By Word
                """);
        input = scan.nextLine();
        if (input.equals("1")) {
            choice = true;
        } else if (!input.equals("0")) {
            System.out.println("No matching input, reverting to default method.. ");
        }
        if (choice) {
            System.out.println("Hãy nhập từ cần xóa: ");
            input = scan.nextLine();
            input = input.toLowerCase();
            input = input.trim();
            for (Word word : Words) {
                if (input.equals(word.getWord_target())) {
                    Words.remove(word);
                    hasBeenRemoved = true;
                    break;
                }
            }
        } else {
            System.out.println("Hãy nhập id của từ cần xóa: ");
            int removedWordId = 0;
            input = scan.nextLine();
            if (input.matches("[0-9]+")) {
                removedWordId = Integer.parseInt(input);
            } else {
                System.out.println("Invalid input, task abandoned! ");
                return;
            }
            for (Word word : Words) {
                if (removedWordId == word.getId()) {
                    input = word.getWord_target();
                    Words.remove(word);
                    hasBeenRemoved = true;
                    break;
                }
            }
        }

        if (!hasBeenRemoved) {
            System.out.println("Không xóa được từ! Có vẻ như từ này không có trong danh sách ");
        } else {
            System.out.println("Bạn đã xóa từ thành công!" + "\nTừ đã xóa: " + input);
        }
    }

    public void dictionarySearcher() {
        System.out.println("Nhập từ bạn cần tìm: ");
        String prefixOfWord = scan.nextLine();
        int count = 0; // optional addition
        if (!prefixOfWord.isBlank()) {
            prefixOfWord = prefixOfWord.toLowerCase();
            prefixOfWord = prefixOfWord.trim();
        }
        System.out.printf("%-5s | %-20s\n", "ID", "English");
        for (Word word : Words) {
            if (word.getWord_target().startsWith(prefixOfWord)) {
                System.out.printf("%-5s | %-20s\n", word.getId(), word.getWord_target());
                count++;
            }
        }
        System.out.println("Found " + count + " instance(s) of word starting with \"" + prefixOfWord + "\"");
    }

    public void dictionaryExportToFile() {
        try {
            String exportPath;
            System.out.println("Please enter the name of the file: ");
            String fileName = scan.nextLine();
            if (fileName.isBlank()) {
                fileName = "dictionaries_exported.txt";
                System.out.println("Invalid input detected, reverting to default file name..");
            } else {
                fileName += ".txt";
            }
            System.out.println("Please enter the path of the directory where you want to export to (optional) ");
            exportPath = scan.nextLine();
            if (exportPath.isBlank()) {
                exportPath = "dictionary_cmdline\\src\\resources\\";
            } else if (Files.notExists(Path.of(exportPath))) {
                System.out.println("Cannot find the directory, please make sure you entered the correct path ");
                return;
            }
            // kiem tra ki tu cuoi file la /
            if (exportPath.charAt(exportPath.length() - 1) != '\\') {
                exportPath = exportPath + "\\";
            }

            File exportedFile = new File(exportPath + fileName);
            if (exportedFile.exists()) {
                System.out.println("The file " + fileName + " already exists. To override it, enter 'y' ");
                String confirmation = scan.nextLine();
                if (confirmation.equalsIgnoreCase("y")) {
                    boolean wasDeleted = exportedFile.delete();
                    if (!wasDeleted) {
                        System.out.println("Something went wrong while trying to delete the original file");
                    }
                } else {
                    System.out.println("Operation cancelled, returning to menu.. ");
                    return;
                }
            }

            System.out.println("Đang xuất ra file.. ");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(exportPath + fileName));
            String line;
            int j = 0;
            while (j < Words.size()) {
                line = String.format("%-5d %-20s %-15s %-40s %-20s %s%n", j + 1, Words.get(j).getWord_target(), Words.get(j).getWord_type(), Words.get(j).getWord_explain(), Words.get(j).getPronunciation(), Words.get(j).getExample());
                bufferedWriter.write(line);
                j++;
            }
            bufferedWriter.close();
            System.out.println("Đã xuất ra file ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}