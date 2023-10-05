public class Main {
    public static void main(String[] args) {
        // System.out.println("Hello world!");
        DictionaryCommandline myObj = new DictionaryCommandline();
        while (myObj.getStatus()) {
            myObj.dictionaryAdvanced();
        }
    }
}