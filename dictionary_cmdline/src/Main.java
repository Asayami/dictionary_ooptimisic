public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        /* TEST AREA */
        DictionaryCommandline dic = new DictionaryCommandline();
        //dic.dictionaryBasic();
        dic.insertFromFile();
        //dic.addWord();
        dic.showAllWords();
        dic.dictionaryExportToFile();
        //dic.dictionaryLookup();
        dic.removeWord();
        dic.showAllWords();
    }
}