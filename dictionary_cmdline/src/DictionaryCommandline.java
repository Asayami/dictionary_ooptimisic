import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class DictionaryCommandline extends DictionaryManagement {
    public void showAllWords() {
        Words.sort((o1, o2)
                -> o1.getWord_target().compareTo(
                o2.getWord_target()));

        System.out.println(String.format("%-5s %-20s %-20s", "No", "English", "Vietnamese"));
        for (int i = 0; i < Words.size(); i++) {
            Word word_ = Words.get(i);
            System.out.println(String.format("%-5d %-20s %-20s", i + 1, word_.getWord_target(), word_.getWord_explain()));
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }
}
