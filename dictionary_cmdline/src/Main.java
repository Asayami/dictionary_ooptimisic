public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        /* TEST AREA */
        DictionaryCommandline dic = new DictionaryCommandline();
        //dic.dictionaryBasic();
        dic.insertFromFile();
        // dic.showAllWords();
        /*
        dic.dictionaryLookup();
        dic.dictionaryLookup();
        dic.dictionaryLookup();
        */
        dic.showAllWords();
        //dic.dictionaryExportToFile();
    }
}