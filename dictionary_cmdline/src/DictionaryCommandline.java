import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    private boolean running = true;

    public void showAllWords() {
        // sắp xếp theo alphabet
        Words.sort(new Comparator<Word>() {
            public int compare(Word obj1, Word obj2) {
                String str1 = obj1.getWord_target();
                String str2 = obj2.getWord_target();
                return str1.compareToIgnoreCase(str2);
            }
        });
        // out
        System.out.printf("%-5s | %-5s | %-20s | %-15s | %-40s | %-20s | %s%n", "No", "ID", "English", "Type", "Vietnamese", "Pronunciation", "Example");
        for (int i = 0; i < Words.size(); i++) {
            Word word_ = Words.get(i);
            System.out.printf("%-5d | %-5d | %-20s | %-15s | %-40s | %-20s | %s%n", i + 1, word_.getId(), word_.getWord_target(), word_.getWord_type(), word_.getWord_explain(), word_.getPronunciation(), word_.getExample());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    /** these methods are made for testing purposes*/
    public boolean getStatus() {
        return running;
    }

    private void waitInput() {
        System.out.println("Press Enter to continue");
        scan.nextLine();
    }

    private byte getChoiceInput() {
        String input = scan.nextLine();
        if (input.matches("[0-9]")) { // check if the input is 0-9
            return Byte.parseByte(input);
        } else {
            return 10; // out of range
        }
    }

    public void dictionaryAdvanced() {
        running = true;
        do {
            System.out.print("""
                    Welcome to My Application!
                    [0] Exit
                    [1] Add
                    [2] Remove
                    [3] Update
                    [4] Display
                    [5] Lookup
                    [6] Search
                    [7] Game
                    [8] Import from file
                    [9] Export to file
                    Your action:
                    """);
            byte choice = getChoiceInput();
            switch (choice) {
                case 0: // exit
                    System.out.println("Thanks for using our application");
                    running = false;
                    break;
                case 1: // add
                    this.insertFromCommandline();
                    waitInput();
                    break;
                case 2: // remove
                    this.removeWord();
                    waitInput();
                    break;
                case 3: // update
                    this.updateWord();
                    waitInput();
                    break;
                case 4: // display
                    this.showAllWords();
                    waitInput();
                    break;
                case 5: //
                    this.dictionaryLookup();
                    waitInput();
                    break;
                case 6:
                    this.dictionarySearcher();
                    waitInput();
                    break;
                case 7:
                    System.out.println("Action_Game yet to be implemented");
                    waitInput();
                    break;
                case 8:
                    this.insertFromFile();
                    waitInput();
                    break;
                case 9:
                    this.dictionaryExportToFile();
                    waitInput();
                    break;
                default:
                    System.out.println("Action not supported");
                    waitInput();
                    break;
            }
        } while (running);
    }
}
