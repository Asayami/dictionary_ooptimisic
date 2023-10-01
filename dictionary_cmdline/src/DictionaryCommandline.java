import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class DictionaryCommandline extends DictionaryManagement {
    public void showAllWords() {
        // sắp xếp theo alphabet
        Collections.sort(Words, new Comparator<Word>() {
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
}
