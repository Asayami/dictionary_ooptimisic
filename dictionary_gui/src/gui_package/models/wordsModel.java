package gui_package.models;

import java.sql.*;

public class wordsModel {
    static String jdbcUrl = "jdbc:sqlite:/C:src\\gui_package\\models\\database.db";
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void createWord(Word word) throws SQLException {
        String sql = "INSERT INTO words (Word, Type, Meaning, Pronunciation, Example, Synonym, Antonyms) " +
                     "VALUES ('" + word.getWord_target() +
                     "', '" + word.getWord_type() + "', '" +
                     word.getWord_explain() + "', '" +
                     word.getPronunciation() + "', '" +
                     word.getExample() + "', '<synonym>', '<antonyms>')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    static Word getWord(int id) throws SQLException {
        String sql = "SELECT * FROM words WHERE ID=" + id;
        Statement statement = connection.createStatement();
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


    static void closeConnection() throws SQLException {
        connection.close();
    }
}
