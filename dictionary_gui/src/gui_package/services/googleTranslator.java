package gui_package.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class googleTranslator {

    public String translate(String text, String targetLanguage) {
        try {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=" + targetLanguage + "&dt=t&q=" + text;
            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // Xử lý phản hồi JSON
            String translation = processTranslationResponse(response.toString());

            return translation;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String processTranslationResponse(String jsonResponse) {
        Gson gson = new Gson();

        // Phân tích JSON thành một JsonArray
        JsonArray jsonArray = gson.fromJson(jsonResponse, JsonArray.class);

        // Trích xuất kết quả dịch thuật từ JsonArray
        String translation = "";
        if (jsonArray != null && jsonArray.size() > 0) {
            JsonArray translationArray = jsonArray.get(0).getAsJsonArray();
            if (translationArray != null && translationArray.size() > 0) {
                JsonElement translationElement = translationArray.get(0).getAsJsonArray().get(0);
                translation = translationElement.getAsString();
            }
        }

        return translation;
    }
}
