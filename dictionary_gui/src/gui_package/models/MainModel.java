package gui_package.models;

import java.io.File;
import java.sql.*;

public class MainModel {
    static String jdbcUrl = "jdbc:sqlite:/C:src\\gui_package\\models\\database.db";
    static Connection connection;
    static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createWord(Word word) throws SQLException {
        String sql = "INSERT INTO words (Word, Type, Meaning, Pronunciation, Example, Synonym, Antonyms) " +
                     "VALUES ('" + word.getWord_target() +
                     "', '" + word.getWord_type() + "', '" +
                     word.getWord_explain() + "', '" +
                     word.getPronunciation() + "', '" +
                     word.getExample() + "', '', '')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static Word getWord(int id) throws SQLException {
        String sql = "SELECT * FROM words WHERE ID=" + id;
        ResultSet result = statement.executeQuery(sql);
        int word_id = result.getInt("Id");
        String word_target = result.getString("Word");
        String word_type = result.getString("Type");
        String word_explain = result.getString("Meaning");
        String word_pronunciation = result.getString("Pronunciation");
        String word_example = result.getString("Example");

        Word returnWord = new Word(word_target, word_type, word_explain, word_pronunciation, word_example);
        returnWord.setId(word_id);
        return returnWord;
    }

    public static void updateWord(int id, Word word) throws SQLException {
        String sql = "UPDATE words SET" +
                     " Word = '" + word.getWord_target() + "'," +
                     " Type = '" + word.getWord_type() + "'," +
                     " Meaning = '" + word.getWord_explain() + "'," +
                     " Pronunciation = '" + word.getPronunciation() + "'," +
                     " Example = '" + word.getExample() + "'," +
                     " Synonym = ''," +
                     " Antonyms = ''" +
                     "WHERE Id = " + id + ";";
        statement.executeUpdate(sql);
    }

    public static void removeWord(int id) throws SQLException {
        String sql = "DELETE FROM words WHERE Id=" + id + ";";
        statement.executeUpdate(sql);
    }

    public static ResultSet getWordByString(String wordString) throws SQLException {
        String sql = "SELECT *" +
                     "FROM words" +
                     "WHERE Word LIKE '" + wordString + "'" +
                     "UNION ALL" +
                     "SELECT *" +
                     "FROM words" +
                     "WHERE Word LIKE '" + wordString + "%'" +
                     "UNION ALL" +
                     "SELECT *" +
                     "FROM words" +
                     "WHERE Word LIKE '%" + wordString + "%'" +
                     "GROUP BY Word" +
                     "HAVING COUNT(*) <= 1;";
        return statement.executeQuery(sql);
    }

    public static void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }
}