public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        /* TEST AREA */
        DictionaryCommandline dic = new DictionaryCommandline();
        dic.insertFromFile();
        dic.updateWord();
        /* END TEST AREA */
        DictionaryManagement.scan.close();
    }
}