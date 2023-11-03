package gui_package.models;

import java.io.File;
import java.sql.*;

public class MainModel {
    public static void main(String[] args) throws SQLException {
        //wordsModel.createWord(new Word("abc","typea","gt","/hello/","exx"));
        Word temp = wordsModel.getWord(46959);
        System.out.println(temp.getWord_target() + " " + temp.getPronunciation());
    }
}
