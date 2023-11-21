package gui_package.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class googleTranslator {

    private static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String translate(String text, String targetLanguage) {
        try {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=" + targetLanguage + "&dt=t&q=" + encodeValue(text);
            URL apiUrl = URI.create(url).toURL();

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

            return processTranslationResponse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String processTranslationResponse(String jsonResponse) {
        Gson gson = new Gson();

        // Phân tích JSON thành một JsonArray
        JsonArray jsonArray = gson.fromJson(jsonResponse, JsonArray.class);

        // Trích xuất kết quả dịch thuật từ JsonArray
        String translation = "";
        if (jsonArray != null && !jsonArray.isEmpty()) {
            JsonArray translationArray = jsonArray.get(0).getAsJsonArray();
            if (translationArray != null && !translationArray.isEmpty()) {
                JsonElement translationElement = translationArray.get(0).getAsJsonArray().get(0);
                translation = translationElement.getAsString();
            }
        }

        return translation;
    }
}
